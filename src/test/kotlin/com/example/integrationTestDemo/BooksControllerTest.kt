package com.example.integrationTestDemo

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpMethod
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(WireMockConfig::class)
@ActiveProfiles("test")
class BooksControllerTest(@LocalServerPort val port: Int) {

    val restTemplate = RestTemplate()

    @Test
    fun `Should return 200`() {

        stubFor(
                get(urlPathEqualTo("/v3/909baf7d-0843-4ffb-83b8-f83a24e72310"))
                        .willReturn(WireMock.aResponse().withStatus(200)
                                .withBody("[ { \"author\": \"Walt Whitman\", \"title\": \"Leaves of Grass\" } ]"))
        )

        restTemplate.exchange(
                "http://localhost:$port/books",
                HttpMethod.GET,
                null,
                String::class.java)

        verify(getRequestedFor(urlPathEqualTo("/v3/909baf7d-0843-4ffb-83b8-f83a24e72310")));
    }

    @Test
    fun `Should return 202`() {

        stubFor(
                get(urlPathEqualTo("/v3/909baf7d-0843-4ffb-83b8-f83a24e72310"))
                        .willReturn(WireMock.aResponse().withStatus(202)
                                .withBody("[ { \"author\": \"Marguerite Yourcenar\", \"title\": \"Memoirs of Hadrian\" } ]"))
        )

        restTemplate.exchange(
                "http://localhost:$port/books",
                HttpMethod.GET,
                null,
                String::class.java)

        verify(getRequestedFor(urlPathEqualTo("/v3/909baf7d-0843-4ffb-83b8-f83a24e72310")));
    }
}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(WireMockConfig::class)
@ActiveProfiles("test")
class AnotherBooksControllerTest(@LocalServerPort val port: Int) {

    val restTemplate = RestTemplate()

    @Test
    internal fun `Should return ok`() {

        stubFor(
                get(urlPathEqualTo("/v3/909baf7d-0843-4ffb-83b8-f83a24e72310"))
                        .willReturn(WireMock.aResponse().withStatus(202)
                                .withBody("[ { \"author\": \"Marguerite Yourcenar\", \"title\": \"Memoirs of Hadrian\" } ]"))
        )

        restTemplate.exchange(
                "http://localhost:$port/books",
                HttpMethod.GET,
                null,
                String::class.java)

        verify(getRequestedFor(urlPathEqualTo("/v3/909baf7d-0843-4ffb-83b8-f83a24e72310")));
    }
}