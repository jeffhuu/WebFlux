package com.jeff.webflux.constant;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @program: WebFlux
 * @author: Jeff Hu 2022/06/29 17:57
 */
@Document
@Data
public class User {
    @Id
    private String id;

    @NotBlank
    private String username;

    @Range(min = 10, max = 100)
    private String address;

    private Integer age;

}
