package com.iftodi.reactive.client

import reactor.core.publisher.Flux

interface StockClient {
    fun readStockSymbol(symbol: String): Flux<StockValue>
}