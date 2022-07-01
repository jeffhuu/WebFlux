package com.jeff.webflux.web;

import com.jeff.webflux.constant.User;
import com.jeff.webflux.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @program: WebFlux
 * @author: Jeff Hu 2022/06/29 18:05
 *
 * 传统 MVC 注解开发模式
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserDao userDao;

    @PostMapping("/")
    public Mono<User> addUser(@RequestBody User user) {
        return userDao.save(user);
    }

    @GetMapping("/")
    public Flux<User> getAll() {
        return userDao.findAll();
    }

    @GetMapping(value = "/stream/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamGetAll() {
        return userDao.findAll();
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable String id) {
        // deleteById 没有返回值，不能判断数据是否存在
        return userDao.findById(id)
                // flapMap 和 map 的返回值都是 Momo，区别在于 flatMap 的函数式接口参数必须返回 Momo， 而 map 的参数只要求返回普通对象
                // 当需要操作数据，并且参数 Function 返回一个 Momo 这个时候使用 flatMap，flatMap 会形成一个新的流
                // 如果不操作数据，只是转换数据，使用 map 会原封不动把对象装进 Mono
                .flatMap(user -> userDao.delete(user).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/")
    public Mono<ResponseEntity<User>> updateUser(@RequestBody User user) {
        return userDao.findById(user.getId())
                .flatMap(u -> userDao.save(user))
                .map(u->new ResponseEntity<User>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity (HttpStatus.NOT_FOUND));
    }

    /**
     * 根据 ID 查找用户信息
     * 存在返回用户信息，不存在返回 404
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> findUserById(@PathVariable("id") String id) {
        return userDao.findById(id)
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/age/{start}/{end}")
    public Flux<User> findByAge(@PathVariable("start") int start, @PathVariable("end") int end) {
        return userDao.findByAgeBetween(start, end);
    }

    @GetMapping(value = "/stream/age/{start}/{end}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamFindByAge(@PathVariable("start") int start, @PathVariable("end") int end) {
        return userDao.findByAgeBetween(start, end);
    }

    @GetMapping("/test")
    public Mono<Integer> test() {
        return Mono.just(1)
                .flatMap(i -> Mono.just(3))
                .map(i -> 22)
                .defaultIfEmpty(-1);
    }
}
