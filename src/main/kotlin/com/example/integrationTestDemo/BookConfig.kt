package com.example.integrationTestDemo

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "book-service")
@Configuration
class BookConfig {
    lateinit var baseUrl: String
}
