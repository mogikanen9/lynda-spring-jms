
package com.mogikanensoftware.bookstore.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Customer {
    private final String customerId;
    private final String fullName;
}
