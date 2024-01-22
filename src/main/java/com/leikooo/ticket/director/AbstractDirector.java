package com.leikooo.ticket.director;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/21
 * @description
 */
public abstract class AbstractDirector {
    public abstract Object buildTicket(String type, String productId, String title, String content, String bankInfo, String tacId);
}
