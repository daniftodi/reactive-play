package com.iftodi.reactive.client

import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

class WebStockClient(val webClient: WebClient) : StockClient {

    override fun readStockSymbol(symbol : String) : Flux<StockValue> {
        return webClient
                .get()
                .uri("http://localhost:8080/stock/{symbol}", symbol)
                .retrieve()
                .bodyToFlux(StockValue::class.java)
    }
}