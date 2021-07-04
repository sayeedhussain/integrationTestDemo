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
class BooksControllerTest(@LocalServerPort val port: Int) {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var wireMock: WireMockServer

    @Test
    fun `Should return 200`() {

        val json = "[ { \"author\": \"Walt Whitman\", \"title\": \"Leaves of Grass\" } ]"

        wireMock.stubFor(
                WireMock.get(WireMock.urlPathEqualTo("/v3/909baf7d-0843-4ffb-83b8-f83a24e72310"))
                        .willReturn(WireMock.aResponse().withStatus(200)
                                .withBody(json))
        )

        webTestClient.get().uri("/books").exchange().expectStatus().isOk.expectBody().json(json)

        wireMock.verify(WireMock.getRequestedFor(WireMock.urlPathEqualTo("/v3/909baf7d-0843-4ffb-83b8-f83a24e72310")));
    }

    @Test
    fun `Should return 500 when books api returns 401`() {

        val json = "[ { \"author\": \"Walt Whitman\", \"title\": \"Leaves of Grass\" } ]"

        wireMock.stubFor(
                WireMock.get(WireMock.urlPathEqualTo("/v3/909baf7d-0843-4ffb-83b8-f83a24e72310"))
                        .willReturn(WireMock.aResponse().withStatus(401)
                                .withBody(json))
        )

        webTestClient.get().uri("/books").exchange().expectStatus().is5xxServerError

        wireMock.verify(WireMock.getRequestedFor(WireMock.urlPathEqualTo("/v3/909baf7d-0843-4ffb-83b8-f83a24e72310")));
    }
}