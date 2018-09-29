package com.eservice.iot;

import com.eservice.iot.service.TokenService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HT
 */
@EnableScheduling
@SpringBootApplication
@ComponentScan
public class IotApplication {

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate  restTemplate = new RestTemplate();
		StringHttpMessageConverter stringHttpMessageConverter=new StringHttpMessageConverter(Charset.forName("UTF-8"));
		List<HttpMessageConverter<?>> list=new ArrayList<>();
		list.add(stringHttpMessageConverter);
		restTemplate.setMessageConverters(list);
		return restTemplate;
	}

	@Bean
	public TokenService tokenService() {
		return new TokenService();
	}

	public static void main(String[] args) {
		SpringApplication.run(IotApplication.class, args);
	}
}
