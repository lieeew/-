package com.leikooo.transaction.mediator;

import com.leikooo.transaction.colleague.AbstractCustomer;
import com.leikooo.transaction.colleague.Buyer;
import com.leikooo.transaction.colleague.Payer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/30
 * @description 使用 Map 修改成了无状态的类
 */
@Component
public class Mediator extends AbstractMediator {
    public static Map<String, Map<String, AbstractCustomer>> customerInstances = new ConcurrentHashMap<>();

    @Override
    public void messageTransfer(String orderId, String targetCustomer, AbstractCustomer customer, String payResult) {
        if (customer instanceof Buyer) {
            System.out.println("朋友代付：" + customerInstances.get(orderId).get("buyer") + " 转发 orderId: " + orderId + " 到用户" + targetCustomer + "进行支付");
        } else if (customer instanceof Payer) {
            System.out.println("支付完成通知: " + customerInstances.get(orderId).get("payer") + " 订单号为：" + orderId + " 的支付。通知 " + targetCustomer + " 支付结果 " + payResult);
        }
    }
}
