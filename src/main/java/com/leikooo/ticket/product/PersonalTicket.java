package com.leikooo.ticket.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/21
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalTicket implements Cloneable {
    /**
     * 发票固定不变的信息
     */
    private String finalInfo;

    /**
     * 发票抬头
     */
    private String title;

    /**
     * 商品信息
     */
    private String product;

    /**
     * 税率、发票代码、校验码、收款方
     */
    private String content;

    @Override
    public PersonalTicket clone() {
        try {
            PersonalTicket clone = (PersonalTicket) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
