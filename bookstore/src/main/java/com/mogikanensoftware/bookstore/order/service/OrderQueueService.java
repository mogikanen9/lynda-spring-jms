package com.mogikanensoftware.bookstore.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderQueueService {
    
    private JmsTemplate jmsTemplate;    

    public void send(){
        this.jmsTemplate.convertAndSend("order-queue", "Hello");
        log.info("order submitted to queue");
    }

    @Autowired
    public OrderQueueService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}