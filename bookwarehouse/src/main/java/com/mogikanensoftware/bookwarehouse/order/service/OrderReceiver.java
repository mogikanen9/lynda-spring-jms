
package com.mogikanensoftware.bookwarehouse.order.service;

import com.mogikanensoftware.bookwarehouse.order.model.BookOrder;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderReceiver {

    @JmsListener(destination = "order-queue", containerFactory = "warehouseContainerFactory")
    public void receive(BookOrder bookOrder) {

        try {
            log.info("Received order {}", bookOrder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}