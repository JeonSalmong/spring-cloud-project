package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.messagequeue.KafkaProducer;
import com.example.orderservice.messagequeue.OrderProducer;
import com.example.orderservice.repository.OrderEntity;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
@Slf4j
public class OrderController {
    Environment env;
    OrderService orderService;
    KafkaProducer kafkaProducer;

    OrderProducer orderProducer;

    @Autowired
    public OrderController(Environment env, OrderService orderService,
                           KafkaProducer kafkaProducer, OrderProducer orderProducer) {
        this.env = env;
        this.orderService = orderService;
        this.kafkaProducer = kafkaProducer;
        this.orderProducer = orderProducer;
    }

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Order Service on PORT %s",
                env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,
                                                     @RequestBody RequestOrder orderDetails) {
        log.info("Before add orders data");
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map(orderDetails, OrderDto.class);
        orderDto.setUserId(userId);
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());

        /* jpa */
//        OrderDto createdOrder = orderService.createOrder(orderDto);
//        ResponseOrder responseOrder = mapper.map(createdOrder, ResponseOrder.class);

        /* send this order to the kafka */
        kafkaProducer.send("example-catalog-topic", orderDto);  // 다른 서비스에 보내기위한 메시지 큐에 send
        orderProducer.send("orders", orderDto); // DB에 직접 저장하지 않고, 저장 정보 메시지 큐에 send --> sink connect에서 받아서 db 저장 (pk 중복 사전 체크 필요 함)
        ResponseOrder responseOrder = mapper.map(orderDto, ResponseOrder.class);

        log.info("After added orders data");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId) throws Exception {
        log.info("Before retrieve orders data");
        Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);

        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseOrder.class));
        });

//        try {
//            Thread.sleep(1000);
//            throw new Exception("장애 발생");
//        } catch (InterruptedException ex) {
//            log.warn(ex.getMessage());
//        }

        log.info("Add retrieved orders data");

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
