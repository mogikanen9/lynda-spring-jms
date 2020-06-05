
package com.mogikanensoftware.bookstore.order.service;

import javax.jms.JMSException;
import javax.jms.Message;

import com.mogikanensoftware.bookstore.order.model.BookOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderQueueService {

    private JmsTemplate jmsTemplate;

    @Transactional(transactionManager = "jmsTransactionManager")
    public void send(BookOrder bookOrder) {

        MessagePostProcessor mpp = new MessagePostProcessor() {

            @Override
            public Message postProcessMessage(Message message) throws JMSException {
                message.setStringProperty("storeId","ST00WEB_CA");
                message.setStringProperty("orderState","NEW");
                return message;
            }

        };

        this.jmsTemplate.convertAndSend("order-queue", bookOrder, mpp);
        log.info("order submitted to queue");
    }

    @Autowired
    public OrderQueueService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}