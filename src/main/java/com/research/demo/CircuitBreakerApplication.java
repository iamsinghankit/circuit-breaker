package com.research.demo;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

@SpringBootApplication
public class CircuitBreakerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CircuitBreakerApplication.class, args);
    }


    @Bean
    CircuitBreakerFactory circuitBreakerFactory() {
        CircuitBreakerFactory factory = new Resilience4JCircuitBreakerFactory();
        factory
                .configureDefault(s -> new Resilience4JConfigBuilder((String) s)
                                               .timeLimiterConfig(TimeLimiterConfig.custom()
                                                                                   .timeoutDuration(Duration.ofSeconds(3))
                                                                                   .build())
                                               .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                                               .build());
        return factory;
    }
}
