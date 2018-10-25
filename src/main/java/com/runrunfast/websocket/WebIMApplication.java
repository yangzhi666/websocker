package com.runrunfast.websocket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableWebSocket
@MapperScan("com.runrunfast.websocket.mapper")
public class WebIMApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WebIMApplication.class, args);
	}
}
