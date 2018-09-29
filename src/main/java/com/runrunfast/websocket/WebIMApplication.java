package com.runrunfast.websocket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.runrunfast.websocket.mapper")
public class WebIMApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebIMApplication.class, args);
	}
}
