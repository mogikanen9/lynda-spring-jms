
package com.mogikanensoftware.bookwarehouse.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.jms.ConnectionFactory;

import com.mogikanensoftware.bookwarehouse.order.model.BookOrder;
import com.mogikanensoftware.bookwarehouse.order.model.ProcessedBookOrder;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableTransactionManagement
@Slf4j
public class JmsConfig {

    @Bean
    public JmsListenerContainerFactory warehouseContainerFactory(ConnectionFactory factory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
        configurer.configure(containerFactory, factory);
        containerFactory.setConcurrency("1-1");
        containerFactory.setTransactionManager(this.jmsTransactionManager(factory));
        containerFactory.setErrorHandler(t->{
            log.error("Exception occured: {}", t.getMessage());
        });
        return containerFactory;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter conv = new MappingJackson2MessageConverter();
        conv.setTargetType(MessageType.TEXT);
        conv.setTypeIdPropertyName("_type");

        Map<String,Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("bookstore.BookOrder",BookOrder.class);
        typeIdMappings.put("bookwarehouse.ProcessedBookOrder", ProcessedBookOrder.class);
        
        conv.setTypeIdMappings(typeIdMappings);
        return conv;
    }

    @Bean(name = "jmsTransactionManager")
    public PlatformTransactionManager jmsTransactionManager(final ConnectionFactory connectionFactory) {
        return new JmsTransactionManager(connectionFactory);
    }
}