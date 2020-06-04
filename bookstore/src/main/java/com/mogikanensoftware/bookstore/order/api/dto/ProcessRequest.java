package com.mogikanensoftware.bookstore.order.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessRequest {
    
    private String bookId;
    private String orderId;
    private String customerId;

}