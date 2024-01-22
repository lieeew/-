/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/22
 * @description
 */
module design.sencond {
    requires cglib;
    requires lombok;
    requires org.slf4j;
    requires spring.data.commons;
    requires spring.amqp;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires org.apache.commons.lang3;
    requires spring.beans;
    requires spring.web;
    requires spring.context;
    requires org.apache.tomcat.embed.core;
    requires com.alibaba.fastjson2;
    requires spring.data.jpa;
    requires spring.rabbit;
    requires jakarta.persistence;
    requires alipay.sdk.java;
    requires spring.messaging;
    requires spring.statemachine.core;
    requires spring.data.redis;
    requires com.rabbitmq.client;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires spring.statemachine.data.redis;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires spring.core;

    opens com.leikooo to spring.core,spring.beans,spring.context,spring.aop;
    opens com.leikooo.config to spring.core;
    opens com.leikooo.ordermanagement.listener to spring.core;
    opens com.leikooo.ordermanagement.statemachine to spring.core;
}