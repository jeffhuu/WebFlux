package com.jeff.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) //排除掉数据库自动加载
public class WebFluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebFluxApplication.class, args);
    }

}
