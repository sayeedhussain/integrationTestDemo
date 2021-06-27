package com.example.integrationTestDemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IntegrationTestDemoApplication

fun main(args: Array<String>) {
	runApplication<IntegrationTestDemoApplication>(*args)
}
