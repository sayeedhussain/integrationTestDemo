package com.example.integrationTestDemo

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.http.Request
import com.github.tomakehurst.wiremock.http.RequestListener
import com.github.tomakehurst.wiremock.http.Response
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.event.ContextClosedEvent

class WireMockContextInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(applicationContext: ConfigurableApplicationContext) {

        val wireMockServer = WireMockServer(WireMockConfiguration().dynamicPort())
        wireMockServer.start()

        wireMockServer.addMockServiceRequestListener(WireMockRequestListener())

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

    class WireMockRequestListener: RequestListener {
        override fun requestReceived(request: Request?, response: Response?) {
            println("WireMock request at URL: ${request?.absoluteUrl}")
            println("WireMock request headers: ${request?.headers}")
            println("WireMock response statusCode: ${response?.status}")
            println("WireMock response headers: ${response?.headers}")
            val responseBody = if (response?.body != null) response.bodyAsString else "null"
            println("WireMock response body: $responseBody")
        }
    }
}
