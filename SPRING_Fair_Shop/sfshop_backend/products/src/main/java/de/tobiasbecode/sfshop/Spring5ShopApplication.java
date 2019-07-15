package de.tobiasbecode.sfshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@SpringBootApplication
@EnableDiscoveryClient
public class Spring5ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring5ShopApplication.class, args);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();
		properties.setNullValue("@null");
		return properties;
	}

}
