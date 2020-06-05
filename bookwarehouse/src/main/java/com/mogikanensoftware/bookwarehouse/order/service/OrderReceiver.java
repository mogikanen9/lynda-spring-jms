
package com.mogikanensoftware.bookwarehouse.order.service;

import com.mogikanensoftware.bookwarehouse.order.model.BookOrder;
import com.mogikanensoftware.bookwarehouse.order.model.ProcessedBookOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderReceiver {

    private final OrderProcessingService orderProcessingService;

    @JmsListener(destination = "order-queue", containerFactory = "warehouseContainerFactory")
    @SendTo(value = "processed-book-orders-queue")
    public Message<ProcessedBookOrder> receive(@Payload
    final BookOrder bookOrder,
            @Header(name = "storeId")
            final String storeId,
            @Header(name = "orderState")
            final String orderState,
            final MessageHeaders headers) {

        try {
            log.info("Received order {}, storeId->{}, orderState->{}, headers->{}", bookOrder, storeId, orderState, headers);

            if (bookOrder.getBookOrderId().equals("000")) {
                throw new RuntimeException("invalid order 000");
            }

            ProcessedBookOrder pbo = this.orderProcessingService.process(bookOrder, storeId, orderState);
            return MessageBuilder
            .withPayload(pbo)
            .setHeader("orderState", orderState)
            .setHeader("storeId", storeId)
            .build();

        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public OrderReceiver(final OrderProcessingService orderProcessingService) {
        this.orderProcessingService = orderProcessingService;
    }
}