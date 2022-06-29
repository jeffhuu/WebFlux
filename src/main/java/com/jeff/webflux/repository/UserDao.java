package com.jeff.webflux.repository;

import com.jeff.webflux.constant.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @program: WebFlux
 * @author: Jeff Hu 2022/06/29 18:02
 */
@EnableMongoRepositories
public interface UserDao extends ReactiveMongoRepository<User, String> {
}
