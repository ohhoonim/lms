package dev.ohhoonim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import dev.ohhoonim.component.auditing.model.BusinessEntityScan;


@EnableAsync
@SpringBootApplication
@BusinessEntityScan(basePackages = {"dev.ohhoonim.para", "dev.ohhoonim.lms"})
public class LmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(LmsApplication.class, args);
	}
}
