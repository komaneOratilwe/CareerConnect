package za.ac.cput;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CareerConnectApplication {
    public static void main(String[] args) {
        SpringApplication.run(CareerConnectApplication.class, args);
        System.out.println("CareerConnect Spring Boot Server is Running!");
    }
}