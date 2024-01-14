package com.leikooo.pay.startegy;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.leikooo.constant.PayConstants;
import com.leikooo.pojo.Order;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/14
 * @description
 */
public class AlipayStrategy implements PayStrategyInterface {
    @Override
    public String pay(Order order) {
        //  创建 alipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(PayConstants.ALIPAY_GATEWAY, PayConstants.APP_ID, PayConstants.APP_PRIVATE_KEY, "JSON", "UTF-8", PayConstants.APP_PUBLIC_KEY, PayConstants.SIGN_TYPE);
        // 设置请求参数
        AlipayTradePagePayRequest payRequest = new AlipayTradePagePayRequest();
        payRequest.setReturnUrl(PayConstants.CALLBACK_URL);
        payRequest.setBizContent("{\"out_trade_no\":\"" + order.getOrderId() + "\","
                + "\"total_amount\":\"" + order.getPrice() + "\","
                + "\"subject\":\"" + "leikooo" + "\","
                + "\"body\":\"" + "商品描述" + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        // 请求
        try {
            return alipayClient.pageExecute(payRequest, "GET").getBody();
        } catch (Exception e) {
            throw new UnsupportedOperationException("Alipay failed! " + e);
        }
    }
}
