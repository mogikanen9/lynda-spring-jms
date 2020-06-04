
package com.mogikanensoftware.bookstore.order.api;

import com.mogikanensoftware.bookstore.order.api.dto.ProcessRequest;
import com.mogikanensoftware.bookstore.order.model.Book;
import com.mogikanensoftware.bookstore.order.model.BookOrder;
import com.mogikanensoftware.bookstore.order.model.Customer;
import com.mogikanensoftware.bookstore.order.service.OrderQueueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/order")
@Slf4j
public class OrderQueueController {

    private OrderQueueService orderQueueService;

    @PostMapping("/process/")
    public ResponseEntity <ProcessRequest> submit(@RequestBody ProcessRequest processRequest) {
        try {
            orderQueueService.send(this.build(processRequest));
            return new ResponseEntity <>(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity <>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    protected BookOrder build(ProcessRequest processRequest) {

        return new BookOrder(processRequest.getOrderId(), new Book(processRequest.getBookId(), ""),
                new Customer(processRequest.getCustomerId(), ""));
    }

    @Autowired
    public OrderQueueController(OrderQueueService orderQueueService) {
        this.orderQueueService = orderQueueService;
    }
}