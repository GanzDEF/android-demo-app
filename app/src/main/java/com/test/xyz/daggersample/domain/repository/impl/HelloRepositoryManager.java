package com.test.xyz.daggersample.domain.repository.impl;

import com.test.xyz.daggersample.domain.repository.api.HelloRepository;

public class HelloRepositoryManager implements HelloRepository {

    @Override
    public String greet(String userName) {
        return "Hello " + userName + "!";
    }
}
