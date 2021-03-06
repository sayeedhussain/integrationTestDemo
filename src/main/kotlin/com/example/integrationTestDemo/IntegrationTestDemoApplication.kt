package com.example.integrationTestDemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(BookConfig::class)
class IntegrationTestDemoApplication

fun main(args: Array<String>) {
	runApplication<IntegrationTestDemoApplication>(*args)
}
