package com.example.integrationTestDemo

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.event.ContextClosedEvent

class WireMockContextInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(applicationContext: ConfigurableApplicationContext) {

        val wireMockServer = WireMockServer(WireMockConfiguration().dynamicPort())
        wireMockServer.start()

        applicationContext.beanFactory.registerSingleton("wireMock", wireMockServer)

        applicationContext.addApplicationListener {
            if (it is ContextClosedEvent) {
                wireMockServer.stop()
            }
        }

        TestPropertyValues
                .of("book-service.baseUrl=http://localhost:${wireMockServer.port()}/")
                .applyTo(applicationContext)
    }
}
