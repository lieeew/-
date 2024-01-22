package com.leikooo.ticket.proxy;

import com.leikooo.ticket.director.AbstractDirector;
import com.leikooo.ticket.director.Director;
import jakarta.annotation.Resource;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/22
 * @description 静态代理
 */
public class DirectorProxy extends AbstractDirector {
    @Resource
    private Director director;

    @Override
    public Object buildTicket(String type, String productId, String title, String content, String bankInfo, String tacId) {
        // 前置处理
        String product = this.getProduct(productId);
        // 前置处理验证银行卡信息
        if (!this.validateBankInfo()) {
            System.out.println("银行卡信息不合法");
        }
        // 调用真实对象的方法
        return director.buildTicket(type, productId, title, content, bankInfo, tacId);
    }

    private String getProduct(String productId) {
        return "通过 productId 获取产品信息";
    }

    private boolean validateBankInfo() {
        return true;
    }
}
