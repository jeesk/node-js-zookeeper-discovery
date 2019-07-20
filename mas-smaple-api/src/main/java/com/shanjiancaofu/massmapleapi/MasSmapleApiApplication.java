package com.shanjiancaofu.massmapleapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MasSmapleApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MasSmapleApiApplication.class, args);
    }


    @GetMapping(value = "/hello", name = "HelloService")
    public String hello() {
        return "hello";
    }


}
