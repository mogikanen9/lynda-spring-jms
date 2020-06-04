package com.mogikanensoftware.bookstore;

import com.mogikanensoftware.bookstore.order.service.OrderQueueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner{

	@Autowired
	private OrderQueueService orderQueueService;

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		orderQueueService.send();
	}

}
