
package com.mogikanensoftware.bookstore.order.service;

import com.mogikanensoftware.bookstore.order.model.BookOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderQueueService {
    
    private JmsTemplate jmsTemplate;    

    @Transactional(transactionManager = "jmsTransactionManager")
    public void send(BookOrder bookOrder){
        this.jmsTemplate.convertAndSend("order-queue", bookOrder);
        log.info("order submitted to queue");
    }

    @Autowired
    public OrderQueueService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}