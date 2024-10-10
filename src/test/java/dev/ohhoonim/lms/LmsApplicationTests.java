package dev.ohhoonim.lms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootTest
class LmsApplicationTests {

	@Test
	void contextLoads() {
		ApplicationModules.of(LmsApplication.class).verify();
	}

}
