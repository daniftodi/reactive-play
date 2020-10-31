package com.iftodi.reactive.client.configuration

import com.iftodi.reactive.client.RSocketStockClient
import com.iftodi.reactive.client.StockClient
import com.iftodi.reactive.client.WebStockClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class ClientConfiguration {
    @Bean
    @Profile("web")
    fun webStockClient(client: WebClient?): StockClient {
        return WebStockClient(client!!)
    }

    @Bean
    @Profile("rsocket")
    fun rSocketStockClient(rSocketRequester: RSocketRequester) : StockClient {
        return RSocketStockClient(rSocketRequester)
    }

    @Bean
    fun rSocketRequests(rSocketRequestBuilder : RSocketRequester.Builder) : RSocketRequester {
        return rSocketRequestBuilder.connectTcp("localhost", 7000).block()!!
    }

    @Bean
    @ConditionalOnMissingBean
    fun webClient(): WebClient {
        return WebClient.builder().build()
    }
}
