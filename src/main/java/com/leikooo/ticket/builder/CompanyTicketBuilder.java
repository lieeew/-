package com.leikooo.ticket.builder;

import com.leikooo.ticket.product.CompanyTicket;
import jakarta.persistence.Table;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/21
 * @description
 */
public class CompanyTicketBuilder extends TicketBuilder<CompanyTicket> {
    private final CompanyTicket companyTicket = new CompanyTicket();

    @Override
    public void setCommonInfo(String title, String product, String content) {
        companyTicket.setTitle(title);
        companyTicket.setProduct(product);
        companyTicket.setContent(content);
    }

    @Override
    public void setTaxId(String taxId) {
        companyTicket.setTaxId(taxId);
    }

    @Override
    public void setBankInfo(String bankInfo) {
        companyTicket.setBankInfo(bankInfo);
    }

    @Override
    public CompanyTicket buildTicket() {
        return companyTicket;
    }
}
