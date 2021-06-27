package com.example.integrationTestDemo

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "book-service")
@ConstructorBinding
data class BookConfig(val baseUrl: String)
