package com.sohail.jetktor.repo

import com.sohail.jetktor.model.FreeGames
import com.sohail.jetktor.network.KtorClient
import io.ktor.client.request.*

object FreeGameRepo {
    private val BASE_URL = "https://www.freetogame.com/api/games"

    suspend fun getFreeGames(): FreeGames {
        return KtorClient.httpClient.use {
            it.get(BASE_URL)
        }
    }
}