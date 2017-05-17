package ubs.application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableCaching
@ComponentScan("ubs")
@Configuration
@EnableAspectJAutoProxy
public class MovieApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MovieApplication.class, args);
	}

	@Bean
	public ExecutorService executorService() {
		return Executors.newFixedThreadPool(5);
	}


}
