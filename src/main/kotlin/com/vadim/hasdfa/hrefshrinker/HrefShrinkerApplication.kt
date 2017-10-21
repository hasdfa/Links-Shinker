package com.vadim.hasdfa.hrefshrinker

import com.mongodb.client.MongoDatabase
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.jmx.export.MBeanExporter
import com.mongodb.client.MongoCollection
import com.mongodb.MongoClient
import com.vadim.hasdfa.hrefshrinker.model.Link
import org.bson.Document


@SpringBootApplication
@Component
class HrefShrinkerApplication: CommandLineRunner {

    override fun run(vararg args: String?) {
        println("Running: " + args)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(HrefShrinkerApplication::class.java, *args)
}