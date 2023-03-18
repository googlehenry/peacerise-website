package com.peacerise.identity.myaccount.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient


@Configuration
class WebClientConfig {

    //webclient每一个request自动检查并获得apptoken并设置authorization: bearer xxx头信息 (oauth2Client)
    @Bean
    fun webClient(authorizedClientManager: OAuth2AuthorizedClientManager?): WebClient {
        val oauth2Client = ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager)
        oauth2Client.setDefaultClientRegistrationId("login-client-client-credentials")
        return WebClient.builder()
            .filter(oauth2Client)
            .filter { request, next ->
                request.headers().forEach { t, u ->
                    println("$t=$u")
                }
                next.exchange(request)
            }
            .build()
    }

    //webclient每一个request自动检查并获得apptoken并设置authorization: bearer xxx头信息 (oauth2Client)
    @Bean("webclient_clean")
    fun webClientClean(authorizedClientManager: OAuth2AuthorizedClientManager?): WebClient {
        return WebClient.builder()
            .filter { request, next ->
                request.headers().forEach { t, u ->
                    println("$t=$u")
                }
                next.exchange(request)
            }
            .build()
    }

    @Bean
    fun authorizedClientManager(
        clientRegistrationRepository: ClientRegistrationRepository,
        authorizedClientRepository: OAuth2AuthorizedClientRepository
    ): OAuth2AuthorizedClientManager {
        val authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
            .authorizationCode()
            .refreshToken()
            .clientCredentials()
            .build()
        val authorizedClientManager = DefaultOAuth2AuthorizedClientManager(
            clientRegistrationRepository, authorizedClientRepository
        )
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider)
        return authorizedClientManager
    }
}