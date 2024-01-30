package com.leikooo.transaction.mediator;

import com.leikooo.transaction.colleague.AbstractCustomer;
import com.leikooo.transaction.colleague.Buyer;
import com.leikooo.transaction.colleague.Payer;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/30
 * @description
 */
public class Mediator extends AbstractMediator {
    private AbstractCustomer buyer;

    private AbstractCustomer payer;

    public void setBuyer(AbstractCustomer buyer) {
        this.buyer = buyer;
    }

    public void setPayer(AbstractCustomer payer) {
        this.payer = payer;
    }

    @Override
    public void messageTransfer(String orderId, String targetCustomer, AbstractCustomer customer, String payResult) {
        if (customer instanceof Buyer) {
            System.out.println("朋友代付：" + buyer.getCustomerName() + "转发 orderId: " + orderId + "到用户" + targetCustomer + "进行支付");
        } else if (customer instanceof Payer) {
            System.out.println("支付完成通知: " + payer.getCustomerName() + " 订单号为：" + orderId + "的支付。通知 " + targetCustomer + " 支付结果 " + payResult);
        }
    }
}
