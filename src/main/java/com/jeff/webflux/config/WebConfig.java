package com.jeff.webflux.config;

import com.jeff.webflux.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

/**
 * @program: WebFlux
 * @author: Jeff Hu 2022/07/01 18:03
 */
@Configuration
public class WebConfig {

    private final UserHandler userHandler;

    public WebConfig(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    @Bean
    RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions
                .nest(RequestPredicates.path("/user"),
                        RouterFunctions.route(RequestPredicates.GET("/all"), userHandler::getAllUsers)
                                .andRoute(RequestPredicates.POST("/"), userHandler::createUser)
                );
    }
}
