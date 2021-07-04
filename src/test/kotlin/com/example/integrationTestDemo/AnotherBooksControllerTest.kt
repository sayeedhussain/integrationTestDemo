package com.example.integrationTestDemo

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort

import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [WireMockContextInitializer::class])
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class AnotherBooksControllerTest(@LocalServerPort val port: Int) {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var wireMock: WireMockServer

    @Test
    internal fun `Should return 200`() {

        webTestClient.get().uri("/books").exchange().expectStatus().isOk

        wireMock.verify(WireMock.getRequestedFor(WireMock.urlPathEqualTo("/v3/909baf7d-0843-4ffb-83b8-f83a24e72310")));
    }
}