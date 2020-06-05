
package com.mogikanensoftware.bookstore.order.config;

import java.util.HashMap;
import java.util.Map;

import javax.jms.ConnectionFactory;

import com.mogikanensoftware.bookstore.order.model.BookOrder;

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

@Configuration
public class JmsConfig{


    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        final MappingJackson2MessageConverter conv = new MappingJackson2MessageConverter();
        conv.setTargetType(MessageType.TEXT);
        conv.setTypeIdPropertyName("_type");
        
        final Map <String, Class <?>> typeIdMappings = new HashMap <>();
        typeIdMappings.put("bookstore.BookOrder",BookOrder.class);
     
        
        conv.setTypeIdMappings(typeIdMappings);
        return conv;
    }

    @Bean(name = "jmsTransactionManager")
    public PlatformTransactionManager jmsTransactionManager(final ConnectionFactory connectionFactory) {
        return new JmsTransactionManager(connectionFactory);
    }

    @Bean
    public JmsListenerContainerFactory warehouseContainerFactory(ConnectionFactory factory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
        configurer.configure(containerFactory, factory);
        containerFactory.setConcurrency("1-1");
        containerFactory.setTransactionManager(this.jmsTransactionManager(factory));        
        return containerFactory;
    }
}