
package com.mogikanensoftware.bookwarehouse.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookOrder {

    private String bookOrderId;
    private Book book;
    private Customer customer;

}
