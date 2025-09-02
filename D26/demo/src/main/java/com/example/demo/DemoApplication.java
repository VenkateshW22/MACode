package com.example.demo;

import com.example.demo.service.AvroProducerService;
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

    // Kafka Producer
    @Bean
    public CommandLineRunner commandLineRunnerKafka(KafkaProducerService kafkaProducerService) {
        return args -> {
            for (int i = 0; i < 20 ; i++) {
                kafkaProducerService.sendMessage("Hello Kafka - "+i);
                TimeUnit.SECONDS.sleep(2);
            }
        };
    }

    // Avro Producer
    @Bean
    public CommandLineRunner commandLineRunnerAvro(AvroProducerService avroProducerService) {
        return args -> {
            for (int i = 0; i < 20; i++) {
                avroProducerService.sendUserAction();
                TimeUnit.SECONDS.sleep(2);
            }
        };
    }

}
