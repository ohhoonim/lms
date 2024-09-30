package dev.ohhoonim.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class LmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(LmsApplication.class, args);
	}
}
