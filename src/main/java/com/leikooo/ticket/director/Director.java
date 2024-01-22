package com.leikooo.ticket.director;

import com.leikooo.ticket.builder.CompanyTicketBuilder;
import com.leikooo.ticket.builder.PersonalTicketBuilder;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/22
 * @description
 */
public class Director extends AbstractDirector {
    @Override
    public Object buildTicket(String type, String productId, String title, String content, String bankInfo, String tacId) {
        switch (type) {
            case "person" -> {
                PersonalTicketBuilder personalTicketBuilder = new PersonalTicketBuilder();
                personalTicketBuilder.setCommonInfo(title, productId, content);
                return personalTicketBuilder.buildTicket();
            }
            case "company" -> {
                CompanyTicketBuilder companyTicketBuilder = new CompanyTicketBuilder();
                companyTicketBuilder.setBankInfo(bankInfo);
                companyTicketBuilder.setCommonInfo(title, productId, content);
                companyTicketBuilder.setTaxId(tacId);
                return companyTicketBuilder.buildTicket();
            }
            default -> throw new UnsupportedOperationException("不支持的订单格式");
        }
    }
}
