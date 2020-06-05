
package com.mogikanensoftware.bookwarehouse.order.service;

import com.mogikanensoftware.bookwarehouse.order.model.BookOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderReceiver {

    private OrderProcessingService orderProcessingService;

    @JmsListener(destination = "order-queue", containerFactory = "warehouseContainerFactory")
    public void receive(@Payload BookOrder bookOrder,
            @Header(name = "storeId") String storeId,
            @Header(name = "orderState") String orderState,
            MessageHeaders headers) {

        try {
            log.info("Received order {}, storeId->{}, orderState->{}, headers->{}", bookOrder, storeId, orderState,headers);

            if (bookOrder.getBookOrderId().equals("000")) {
                throw new RuntimeException("invalid order 000");
            }

            this.orderProcessingService.process(bookOrder, storeId, orderState);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public OrderReceiver(OrderProcessingService orderProcessingService) {
        this.orderProcessingService = orderProcessingService;
    }
}