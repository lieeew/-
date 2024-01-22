package com.leikooo.ticket.builder;

import com.leikooo.ticket.product.PersonalTicket;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/21
 * @description
 */
public class PersonalTicketBuilder extends TicketBuilder<PersonalTicket> {
    private final PersonalTicket personalTicket = new PersonalTicket();

    @Override
    public void setCommonInfo(String title, String product, String content) {
        personalTicket.setContent(content);
        personalTicket.setProduct(product);
        personalTicket.setTitle(title);
    }

    @Override
    public PersonalTicket buildTicket() {
        return personalTicket;
    }
}
