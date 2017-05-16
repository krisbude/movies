package ubs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan("ubs") 
public class MovieApplication{

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MovieApplication.class, args);
    }	

}
