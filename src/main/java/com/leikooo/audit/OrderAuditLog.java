package com.leikooo.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/31
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderAuditLog {
    /**
     * 当前用户信息
     */
    private String account;

    /**
     * 用户操作
     */
    private String action;

    private String orderId;

    /**
     * 其他额外信息
     */
    private Object details;
}
