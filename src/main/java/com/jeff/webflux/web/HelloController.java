package com.jeff.webflux.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @program: WebFlux
 * @author: Jeff Hu 2022/06/29 15:58
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        long start = System.currentTimeMillis();
        // 普通接口线程在这里会阻塞, 模拟平时开发中的数据库IO、第三方接口调用等耗时操作
        String helloStr = getHelloStr();
        System.out.println("普通接口耗时：" + (System.currentTimeMillis() - start));
        return helloStr;
    }

    @GetMapping("/hello2")
    public Mono<String> hello2() {
        long start = System.currentTimeMillis();
        // 线程在这里不会阻塞, 会往下执行
        Mono<String> hello2 = Mono.fromSupplier(() -> getHelloStr());
        System.out.println("WebFlux 接口耗时：" + (System.currentTimeMillis() - start));
        return hello2;
    }

    private String getHelloStr() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello";
    }
}
