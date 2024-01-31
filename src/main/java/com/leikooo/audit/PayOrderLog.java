package com.leikooo.audit;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/31
 * @description
 */
@Component
public class PayOrderLog extends AbstractAuditLogProcessor {
    @Override
    protected OrderAuditLog buildDetails(OrderAuditLog orderAuditLog) {
        HashMap<String, String> extraLog = new HashMap<>();
        extraLog.put("payType", "支付宝");
        extraLog.put("price", "100");
        orderAuditLog.setDetails(extraLog);
        return orderAuditLog;
    }
}
