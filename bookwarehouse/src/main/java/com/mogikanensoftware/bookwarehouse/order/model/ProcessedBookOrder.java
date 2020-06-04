
package com.mogikanensoftware.bookwarehouse.order.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessedBookOrder {    
    private BookOrder bookOrder;
    private LocalDateTime processingDateTime;
    private LocalDateTime expectedShippingDateTime;
}