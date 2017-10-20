package com.vadim.hasdfa.hrefshrinker

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class HrefShrinkerApplication

fun main(args: Array<String>) {
    SpringApplication.run(HrefShrinkerApplication::class.java, *args)
}