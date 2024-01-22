package com.leikooo.ticket.builder;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/21
 * @description
 */
public abstract class TicketBuilder<T> {

    /**
     * 设置通用发票信息
     *
     * @param title
     * @param product
     * @param content
     */
    public abstract void setCommonInfo(String title, String product, String content);

    public void setTaxId(String taxId) {
        throw new UnsupportedOperationException();
    }

    public void setBankInfo(String bankInfo) {
        throw new UnsupportedOperationException();
    }

    /**
     * 抽象方法构建方法
     *
     * @return
     */
    public abstract T buildTicket();
}
