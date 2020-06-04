
package com.mogikanensoftware.bookwarehouse.order.service;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderReceiver {

    @JmsListener(destination = "order-queue", containerFactory = "warehouseContainerFactory")
    public void receive(Message message) {

        try {
            log.info("Received order message {}", message);           
            message.acknowledge();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}