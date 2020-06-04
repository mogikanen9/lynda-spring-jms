
package com.mogikanensoftware.bookwarehouse.order.service;

import java.time.LocalDateTime;

import com.mogikanensoftware.bookwarehouse.order.model.BookOrder;
import com.mogikanensoftware.bookwarehouse.order.model.ProcessedBookOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProcessingService {
    
    private JmsTemplate jmsTemplate;

    public void process(BookOrder bookOrder){
        ProcessedBookOrder pbo = new ProcessedBookOrder(bookOrder, LocalDateTime.now(),
         LocalDateTime.now().plusHours(48));
         jmsTemplate.convertAndSend("processed-book-orders-queue", pbo);
    }

    @Autowired
    public OrderProcessingService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    
}