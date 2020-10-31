package com.iftodi.reactive.client

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.StepVerifier

@SpringBootTest
class RSocketClientTest {

    @Autowired
    lateinit var rSocketRequesterBuilder: RSocketRequester.Builder ;

    @Test
    fun testReadStockSymbols() {
        //given
        val rSocketStockClient = RSocketStockClient(createRSocketRequester()!!)

        //when
        val stockValueSymbolReader = rSocketStockClient.readStockSymbol("SYMBOL");

        //then
        assertThat(stockValueSymbolReader.blockFirst()?.symbol).isEqualTo("SYMBOL");
        assertThat(stockValueSymbolReader.take(5).count().block()).isEqualTo(5);

        StepVerifier.create(stockValueSymbolReader.take(2))
                .expectNextMatches {it.symbol.equals("SYMBOL")}
                .expectNextMatches {it.symbol.equals("SYMBOL")}
                .verifyComplete()
    }

    private fun createRSocketRequester(): RSocketRequester? {
        return rSocketRequesterBuilder.connectTcp("localhost", 7000).block()
    }

}