package com.example.userservice.config;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;
import java.util.Enumeration;

@Slf4j
public class LoginCheckFilter implements Filter {

    public static final String AUTHORIZATION = "Authorization";
    public static final String TYPE = "Bearer";
    private static final String[] whitelist = {"/login", "/error", "/signup", "/health_check", "/actuator/**"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("LoginCheckFilter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try {
            log.info("인증 체크 필터 시작 {}", requestURI);
            if (isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}", requestURI);

                String token = null;
                Enumeration<String> headers = httpRequest.getHeaders(AUTHORIZATION);
                while (headers.hasMoreElements()) {
                    String value = headers.nextElement();
                    if (value.toLowerCase().startsWith(TYPE.toLowerCase())) {
                        token = value.substring(TYPE.length()).trim();
                    }
                }

                if (token == null || token.isEmpty()) {
                    log.info("미인증 사용자 요청 {}", requestURI);
                    //로그인으로 redirect
                    //httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    httpResponse.sendError(400, "미인증 사용자 요청");
                    return; //여기가 중요, 미인증 사용자는 다음으로 진행하지 않고 끝!
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e; //예외 로깅 가능 하지만, 톰캣까지 예외를 보내주어야 함
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }
    }

    /**
     * 화이트 리스트의 경우 인증 체크X
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

    @Override
    public void destroy() {
        log.info("LoginCheckFilter destroy");
    }
}
