package com.test.xyz.demo.domain.repository.impl;

import com.test.xyz.demo.domain.repository.api.HelloRepository;

public class HelloRepositoryReleaseManager implements HelloRepository {

    @Override
    public String greet(String userName) {
        return "Hello " + userName + "!";
    }
}
