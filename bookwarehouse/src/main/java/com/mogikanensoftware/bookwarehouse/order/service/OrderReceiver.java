
package com.mogikanensoftware.bookwarehouse.order.service;

import com.mogikanensoftware.bookwarehouse.order.model.BookOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderReceiver {

    private OrderProcessingService orderProcessingService;

    @JmsListener(destination = "order-queue", containerFactory = "warehouseContainerFactory")
    public void receive(BookOrder bookOrder) {

        try {
            log.info("Received order {}", bookOrder);

            if(bookOrder.getBookOrderId().equals("000")){
                throw new RuntimeException("invalid order 000");
            }

            this.orderProcessingService.process(bookOrder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public OrderReceiver(OrderProcessingService orderProcessingService) {
        this.orderProcessingService = orderProcessingService;
    }
}