package com.example.integrationTestDemo

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(BookConfig::class)
class IntegrationTestDemoApplication

fun main(args: Array<String>) {
	runApplication<IntegrationTestDemoApplication>(*args)
}
