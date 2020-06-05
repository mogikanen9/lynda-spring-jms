
package com.mogikanensoftware.bookstore.order.service;

import javax.jms.Message;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BookOrderProcessingMessageListener {

    @JmsListener(destination = "processed-book-orders-queue", concurrency = "1")
    public void onMessage(final Message message) {
        log.info("book order processing message received -> {}", message);
    }

}