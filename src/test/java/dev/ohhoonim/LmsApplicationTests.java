package dev.ohhoonim;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
class LmsApplicationTests {

	@Test
	void contextLoads() {
		ApplicationModules.of(LmsApplication.class).verify();
	}

	ApplicationModules modules = ApplicationModules.of(LmsApplication.class);

	@Test
	public void writeDocumentationSnippets() {
		new Documenter(modules)
				.writeModulesAsPlantUml()
				.writeIndividualModulesAsPlantUml();
	}
}
