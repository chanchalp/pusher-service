package com.infinity.pusherservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.infinity.pusher.config.JsonProperties;

@SpringBootApplication
@ComponentScan("com.infinity")
public class PusherServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PusherServiceApplication.class, args);
	}

}
