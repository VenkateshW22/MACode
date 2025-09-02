package com.example.demo;

import com.example.demo.service.KafkaProducerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

    @Bean
    public CommandLineRunner commandLineRunner(KafkaProducerService kafkaProducerService) {
        return args -> {
            for (int i = 0; i < 50 ; i++) {
                kafkaProducerService.sendMessage("Hello Kafka - "+i);
                TimeUnit.SECONDS.sleep(3);
            }
        };
    }

}
