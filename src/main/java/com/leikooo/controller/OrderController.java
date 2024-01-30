package com.leikooo.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.leikooo.constant.PayConstants;
import com.leikooo.pojo.Order;
import com.leikooo.service.OrderService;
import com.leikooo.service.decorator.OrderServiceDecorator;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/12
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Value("${service.level}")
    private Integer serviceLevel;

    @Resource
    private OrderService orderService;

    @Resource
    private OrderServiceDecorator orderServiceDecorator;

    @PostMapping("/creat")
    public Order createOrder(@RequestParam String productId) {
        return orderService.createOrder(productId);
    }

    @PostMapping("/pay")
    public Order payOrder(@RequestParam String orderId) {
        return orderService.payOrder(orderId);
    }

    @PostMapping("/payFace")
    public String payFace(@RequestParam String orderId, @RequestParam Integer payType, @RequestParam Float price) {
        return orderService.getPayUrl(orderId, payType, price);
    }

    @PostMapping("/send")
    public Order send(@RequestParam String orderId) {
        return orderService.sendOrder(orderId);
    }

    @PostMapping("/receive")
    public Order receive(@RequestParam String orderId) {
        return orderService.receiveOrder(orderId);
    }

    @RequestMapping("/alipayCallback")
    public String alipayCallback(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException, UnsupportedEncodingException {
        // 获取回调信息
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                // 受教了，之前都是写一个 if 判断语句
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //避免出现乱码
            valueStr = new String(valueStr.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            params.put(name, valueStr);
        }
        //验证签名，确保回调接口真的是支付宝平台触发的
        boolean signVerified = AlipaySignature.rsaCheckV1(params, PayConstants.APP_PUBLIC_KEY, "UTF-8", PayConstants.SIGN_TYPE); // 调用SDK验证签名
        //确定是否是支付宝平台 发起的回调
        if (!signVerified) {
            throw new UnsupportedOperationException("callback verify failed!");
        }
        // 商户订单号
        String outTradeNo = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        System.out.println("========== " + outTradeNo);
        // 支付宝流水号
        String tradeNo = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        // 支付金额
        float totalAmount = Float.parseFloat(new String(request.getParameter("total_amount").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        // 支付
        orderServiceDecorator.setOrderServiceInterface(orderService);
        Order decoratorPay = orderServiceDecorator.decoratorPay(outTradeNo, serviceLevel, totalAmount);
        log.info("支付宝流水号: {} , 支付金额: {}, decoratorPay {}", tradeNo, totalAmount, decoratorPay);
        //进行相关的业务操作
        return "支付成功页面跳转, 当前订单为：" + outTradeNo;
    }
    @PostMapping("/friendPay")
    public void friendPay(String orderId, String sourceCustomer, String targetCustomer, String payResult, String role) {
        orderService.friendlyPay(sourceCustomer, orderId, targetCustomer, payResult, role);
    }
}
