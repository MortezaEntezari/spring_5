package com.example1.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Demo1Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }


    @GetMapping("/isPrime")
    public String isPrime(@RequestParam(value = "number") int number) {

        if (number==0 || number==1){

            return number+" is not prime";

        }

        for (int i = 2; i < Math.floor(Math.sqrt(number)); i++) {

            if (number%i==0){

                return number+" is not prime";

            }

        }

        return number+" is prime";


    }

}
