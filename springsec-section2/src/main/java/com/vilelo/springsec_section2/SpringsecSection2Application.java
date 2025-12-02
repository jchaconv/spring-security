package com.vilelo.springsec_section2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
/*@EnableJpaRepositories("com.vilelo.springsec_section2.repository")
@EntityScan("com.vilelo.springsec_section2.model")*/
public class SpringsecSection2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringsecSection2Application.class, args);
	}

}
