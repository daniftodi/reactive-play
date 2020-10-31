package com.iftodi.reactive.reactive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.MediaType
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.ZonedDateTime
import java.time.ZonedDateTime.now
import java.util.concurrent.ThreadLocalRandom

@SpringBootApplication
class ReactiveApplication

fun main(args: Array<String>) {
    runApplication<ReactiveApplication>(*args)
}

@RestController
class StockController(val stockService: StockService) {

    @GetMapping(value = ["/stock/{symbol}"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getStockPrices(@PathVariable symbol: String) = stockService.generatePrices(symbol)


}

@Controller
class RSocketController(val stockService: StockService) {

    @MessageMapping("stockPrices")
    fun prices(symbol: String) = stockService.generatePrices(symbol)
}

@Service
class StockService {
    fun generatePrices(symbol: String): Flux<StockValue> {
        return Flux.interval(Duration.ofSeconds(1))
                .map { StockValue(symbol, randomDouble(), now()) }
    }

    private fun randomDouble(): Double {
        return ThreadLocalRandom.current().nextDouble(100.0);
    }
}

data class StockValue(val symbol: String,
                      val value: Double,
                      val dateTime: ZonedDateTime)
