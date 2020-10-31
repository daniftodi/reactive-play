package com.iftodi.reactive.client

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.rsocket.RSocketRequester
import reactor.core.publisher.Flux
import java.io.IOException

class RSocketStockClient(val rSocketRequest: RSocketRequester) : StockClient {

    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun readStockSymbol(symbol: String): Flux<StockValue> {
        return rSocketRequest.route("stockPrices")
                .data(symbol)
                .retrieveFlux(StockValue::class.java)
                .doOnError(IOException::class.java) {
                    log.error("Exception on RSocketRequest: {}", it.message, it)
                }
    }


}