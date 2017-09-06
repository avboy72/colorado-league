package com.thescottasylum.nica;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackageClasses= {Application.class})
public class Application {

	private static Logger __log = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) {

    	    SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
            
    }
    

    @Bean
    public RestTemplate rest(RestTemplateBuilder builder) {
      return builder.build();
    }
    
}
