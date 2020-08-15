package com.research.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * @author Ankit Singh
 */
@SuppressWarnings("ALL")
@RestController
public class CircuitBreakerController {

    @Autowired
    private CircuitBreakerFactory factory;

    @GetMapping("/cb/users")
    public List<User> userDelegator() {
        RestTemplate rest = new RestTemplate();
        CircuitBreaker cb = factory.create("cb");
        return cb.run(()->  rest.getForEntity(URI.create("http://localhost:8080/users"), List.class)
                   .getBody(),throwable ->fallback() );
    }
    private List<User> fallback(){
        return List.of(new User("Divya","abc@gmail.com","88888888"));
    }

    @GetMapping("/users")
    public List<User> users() {

//        throw new RuntimeException("Someting went wrong");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return List.of(new User("Ankit", "xyz@email.com", "999999999"));
    }


    private static class User {
        private final String username;
        private final String email;
        private final String mobileNo;

        public User(String username, String email, String mobileNo) {
            this.username = username;
            this.email = email;
            this.mobileNo = mobileNo;
        }


        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public String getMobileNo() {
            return mobileNo;
        }
    }


}
