package com.example.integrationTestDemo

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.event.ContextClosedEvent
import java.nio.file.Files
import java.nio.file.Paths

class WireMockContextInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(applicationContext: ConfigurableApplicationContext) {

        val wmServer = WireMockServer(WireMockConfiguration().dynamicPort())
        wmServer.start()

        val mappingJson = String(Files.readAllBytes(Paths.get("src/main/resources/mock.json")));
        val jsonArray = JSONArray(mappingJson)
        var i = 0
        while (i < jsonArray.length()) {
            val stubMapping = StubMapping.buildFrom(jsonArray[i].toString())
            wmServer.addStubMapping(stubMapping)
            i++
        }

        applicationContext.beanFactory.registerSingleton("wireMock", wmServer)

        applicationContext.addApplicationListener {
            if (it is ContextClosedEvent) {
                wmServer.stop()
            }
        }

        TestPropertyValues
                .of("book-service.baseUrl=http://localhost:${wmServer.port()}/")
                .applyTo(applicationContext)
    }
}
