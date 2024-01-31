package com.leikooo.audit;

import org.springframework.stereotype.Component;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/31
 * @description
 */
@Component
public class CreatOrderLog extends AbstractAuditLogProcessor {
    @Override
    protected OrderAuditLog buildDetails(OrderAuditLog orderAuditLog) {
        return orderAuditLog;
    }
}
