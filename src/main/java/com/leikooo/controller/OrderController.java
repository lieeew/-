package com.leikooo.controller;

import com.leikooo.pojo.Order;
import com.leikooo.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/12
 * @description
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    @PostMapping("/creat")
    public Order createOrder(@RequestParam String productId) {
        return orderService.createOrder(productId);
    }

    @PostMapping("/pay")
    public Order payOrder(@RequestParam String orderId) {
        return orderService.payOrder(orderId);
    }

    @PostMapping("/send")
    public Order send(@RequestParam String orderId) {
        return orderService.sendOrder(orderId);
    }

    @PostMapping("/receive")
    public Order receive(@RequestParam String orderId) {
        return orderService.receiveOrder(orderId);
    }
}
