package com.test.xyz.demo.domain.repository.impl;

import com.test.xyz.demo.domain.repository.api.HelloRepository;

public class HelloRepositoryManager implements HelloRepository {

    @Override
    public String greet(String userName) {
        return "Hello " + userName + "!";
    }
}
