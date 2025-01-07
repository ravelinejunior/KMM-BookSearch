package com.plcoding.bookpedia.core.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun createHttpClient(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(HttpTimeout) {
                socketTimeoutMillis = 15_000L
                requestTimeoutMillis = 15_000L
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println(
                            message
                        )
                    }
                }
                level = LogLevel.ALL
            }
            defaultRequest {
                header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/237.84.2.178 Safari/537.36")
                header("Accept", "application/json")
                header("Accept-Language", "en-US,en;q=0.8")
                header("Accept-Encoding", "gzip, deflate, br")
                header("Connection", "keep-alive")
                header("Cache-Control", "no-cache")
                header("Pragma", "no-cache")
                contentType(ContentType.Application.Json)
            }
        }
    }
}