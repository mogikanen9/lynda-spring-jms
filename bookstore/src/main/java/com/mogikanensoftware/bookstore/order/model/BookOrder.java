package com.mogikanensoftware.bookstore.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class BookOrder {

    private final String bookOrderId;
    private final Book book;
    private final Customer customer;

}
