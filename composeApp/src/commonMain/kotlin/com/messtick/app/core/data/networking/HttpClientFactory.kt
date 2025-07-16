package com.messtick.app.core.data.networking

import com.messtick.app.core.data.persistent.UserDataStore
import com.messtick.app.login.data.refreshToken
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

class HttpClientFactory(
    private val userDataStore: UserDataStore,
) {
    fun create(): HttpClient {
        return HttpClient {
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.w(message)
                    }
                }
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        encodeDefaults = false
                    },
                    contentType = ContentType.Any
                )
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val accessToken = userDataStore.accessToken.first()
                        if (accessToken.isNullOrBlank()) {
                            return@loadTokens null
                        }
                        BearerTokens(
                            accessToken = userDataStore.accessToken.first().orEmpty(),
                            refreshToken = userDataStore.refreshToken.first().orEmpty()
                        )
                    }
                    refreshTokens {
                        val refreshToken = userDataStore.refreshToken.first().orEmpty()
                        if (refreshToken.isBlank())
                            return@refreshTokens null
                        val tokens = refreshToken(client, refreshToken).getOrNull()
                        if (tokens?.isSuccess == true) {
                            BearerTokens(
                                accessToken = tokens.data?.token.orEmpty(),
                                refreshToken = tokens.data?.refreshToken.orEmpty()
                            )
                        } else {
                            userDataStore.clearAll()
                            userDataStore.sendSessionOutEvent()
                            null
                        }
                    }
                }
            }
            defaultRequest {
                url(NetworkConfig.BASE_URL)
                contentType(ContentType.Application.Json)
            }
        }
    }
}

object NetworkConfig{
    const val BASE_URL = "https://api.dev.messtick.com"
    const val IMAGE_URL = "$BASE_URL/api/Chat/Picture"
}