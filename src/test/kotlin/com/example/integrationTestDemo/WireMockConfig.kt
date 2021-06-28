package com.example.integrationTestDemo

import com.github.tomakehurst.wiremock.client.WireMock
import org.springframework.boot.test.context.TestConfiguration

//private val wireMockServer =
//        WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort())

@TestConfiguration
class WireMockConfig {

    init {
        WireMock.configureFor("localHost", 9080)
    }

//    @Bean
//    @Primary
//    fun bookConfig(): BookConfig {
//        val bookConfig = BookConfig()
//        bookConfig.baseUrl = "http://localhost:${wireMockServer.port()}"
//        return bookConfig
//    }
}