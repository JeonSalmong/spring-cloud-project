package com.example.userservice.service;

import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.config.exception.CustomException;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.Authority;
import com.example.userservice.entity.UserAuthority;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.repository.AuthorityRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.config.JwtTokenProvider;
import com.example.userservice.vo.RequestLogin;
import com.example.userservice.vo.ResponseAuth;
import com.example.userservice.vo.ResponseOrder;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final OrderServiceClient orderServiceClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

    @Override
    public UserDto signup(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));

        Authority authority = authorityRepository.findByAuthorityName(userDto.getAuth());
        UserAuthority userAuthority = UserAuthority.createUserAuthority(authority);
        userEntity.addUserAuthority(userAuthority);

        userRepository.save(userEntity);

        UserDto returnUserDto = mapper.map(userEntity, UserDto.class);

        returnUserDto.setAuth(userDto.getAuth());

        return returnUserDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new CustomException("User not found");

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        List<ResponseAuth> auths = new ArrayList<>();
        for (UserAuthority row : userEntity.getUserAuthorities()) {
            ResponseAuth responseAuth = new ResponseAuth();
            responseAuth.setAuth(row.getAuthority().getAuthorityName());
            auths.add(responseAuth);
        }
        userDto.setAuths(auths);

        List<ResponseOrder> orders = new ArrayList<>();
        /* Using as rest template */
//        String orderUrl = String.format(env.getProperty("order_service.url"), userId);
//        ResponseEntity<List<ResponseOrder>> orderListResponse =
//                restTemplate.exchange(orderUrl, HttpMethod.GET, null,
//                                            new ParameterizedTypeReference<List<ResponseOrder>>() {
//                });
//        List<ResponseOrder> ordersList = orderListResponse.getBody();

        /* Using a feign client */
        /* Feign exception handling */
//        List<ResponseOrder> ordersList = null;
//        try {
//            ordersList = orderServiceClient.getOrders(userId);
//        } catch (FeignException ex) {
//            log.error(ex.getMessage());
//        }

        /* ErrorDecoder */
//        List<ResponseOrder> ordersList = orderServiceClient.getOrders(userId);
        log.info("Before call orders microservice");
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        List<ResponseOrder> ordersList = circuitBreaker.run(() -> orderServiceClient.getOrders(userId),
                throwable -> new ArrayList<>());
        log.info("After called orders microservice");

        userDto.setOrders(ordersList);

        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null)
            throw new IllegalArgumentException(email);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(userEntity, UserDto.class);
        return userDto;
    }

    @Override
    public String createToken(RequestLogin requestLogin) {

        UserEntity user = userRepository.findByEmail(requestLogin.getEmail());
        if (user == null) {
            throw new CustomException("User not found");
        }
        //비밀번호 확인 등의 유효성 검사 진행
        if (requestLogin.getPassword() == null || requestLogin.getPassword().isEmpty()) {
            throw new CustomException("password 누락");
        }

        if (!passwordEncoder.matches(requestLogin.getPassword(), user.getEncryptedPwd())) {
            throw new CustomException("password 불일치!!");
        }

        //권한정보 확인
        boolean ynAuth = false;
        for (UserAuthority row : user.getUserAuthorities()) {
            if ("admin".equals(row.getAuthority().getAuthorityName())) {
                ynAuth = true;
                break;
            }

            if ((requestLogin.getAuth()).equals(row.getAuthority().getAuthorityName())) {
                ynAuth = true;
                break;
            }
        }
        if (!ynAuth) {
            throw new CustomException("권한 불일치!!");
        }

        return jwtTokenProvider.createToken(user.getEmail(), requestLogin);
    }

    @Override
    public String getToken(String token) {
        return jwtTokenProvider.getSubject(token);
    }

}
