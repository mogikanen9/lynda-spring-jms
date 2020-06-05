
package com.mogikanensoftware.bookwarehouse.order.service;

import java.time.LocalDateTime;

import com.mogikanensoftware.bookwarehouse.order.model.BookOrder;
import com.mogikanensoftware.bookwarehouse.order.model.ProcessedBookOrder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderProcessingService {

    @Transactional(transactionManager = "jmsTransactionManager")
    public ProcessedBookOrder process(BookOrder bookOrder, String storeId, String orderState) {
        ProcessedBookOrder pbo = new ProcessedBookOrder(bookOrder, LocalDateTime.now(),
                LocalDateTime.now().plusHours(48));

        if (orderState.equals("NEW")) {
            this.add(bookOrder, storeId);
        } else if (orderState.equals("UPDATE")) {
            this.update(bookOrder, storeId);
        } else {
            throw new RuntimeException("Order State not supported->" + orderState);
        }

        return pbo;
    }

    protected void add(BookOrder bookOrder, String storeId) {
        log.info("Saving new order -> {}", bookOrder);
    }

    protected void update(BookOrder bookOrder, String storeId) {
        log.info("Updating existing order -> {}", bookOrder);
    }
}