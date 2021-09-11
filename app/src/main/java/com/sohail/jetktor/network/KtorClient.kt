package com.sohail.jetktor.network

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*

object KtorClient {

    val httpClient = HttpClient {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.i("ktor-log", message)
                }
            }
            level = LogLevel.ALL
        }

        install(HttpTimeout) {
            socketTimeoutMillis = 15_000
            requestTimeoutMillis = 15_000
            connectTimeoutMillis = 15_000
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}