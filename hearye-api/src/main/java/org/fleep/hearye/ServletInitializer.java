package org.fleep.hearye;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		System.out.println("!!!!!!! STARTING UP!!!!!!!!");
		return application.sources(HearYeApiApplication.class);
	}

}
