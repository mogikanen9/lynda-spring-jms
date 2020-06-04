
package com.mogikanensoftware.bookstore.order.config;

import java.util.Collections;

import com.mogikanensoftware.bookstore.order.model.BookOrder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter conv = new MappingJackson2MessageConverter();
        conv.setTargetType(MessageType.TEXT);
        conv.setTypeIdPropertyName("_type");
        conv.setTypeIdMappings(Collections.singletonMap("bookstore.BookOrder",BookOrder.class));
        return conv;
    }
}