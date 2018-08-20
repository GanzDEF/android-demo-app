package com.test.xyz.demo.domain.repository.impl

import com.test.xyz.demo.domain.repository.api.GreetRepository

class GreetRepositoryManager : GreetRepository {

    override fun greet(userName: String): String {
        return "Hello $userName!"
    }
}
