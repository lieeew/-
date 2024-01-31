package com.leikooo.audit;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/31
 * @description
 */
@Component
public class SendOrderLog extends AbstractAuditLogProcessor {
    @Override
    protected OrderAuditLog buildDetails(OrderAuditLog orderAuditLog) {
        HashMap<String, String> extraLog = new HashMap<>();
        extraLog.put("快递公司", "顺丰快递");
        extraLog.put("快递单号", "100100");
        orderAuditLog.setDetails(extraLog);
        return orderAuditLog;
    }
}
