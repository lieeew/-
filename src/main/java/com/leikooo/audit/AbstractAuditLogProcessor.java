package com.leikooo.audit;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/31
 * @description
 */
public abstract class AbstractAuditLogProcessor {
    /**
     * final 防止子类覆写
     * @param account
     * @param action
     * @param orderId
     * @return
     */
    private final OrderAuditLog basicAuditLog(String account, String action, String orderId) {
        return OrderAuditLog.builder().account(account).action(action).orderId(orderId).build();
    }

    /**
     * 自定义模板，子类实现具体细节
     *
     * @param orderAuditLog
     * @return
     */
    protected abstract OrderAuditLog buildDetails(OrderAuditLog orderAuditLog);

    /**
     * 使用 final 关键字不允许子类进行覆写
     *
     * @param account
     * @param action
     * @param orderId
     * @return
     */
    public final OrderAuditLog creatAuditLog(String account, String action, String orderId) {
        OrderAuditLog orderAuditLog = basicAuditLog(account, action, orderId);
        return buildDetails(orderAuditLog);
    }
}
