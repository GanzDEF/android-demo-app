package com.test.xyz.demo.domain.repository.impl;

import com.test.xyz.demo.domain.repository.api.GreetRepository;

public class GreetRepositoryManager implements GreetRepository {

    @Override
    public String greet(String userName) {
        return "Hello " + userName + "!";
    }
}
