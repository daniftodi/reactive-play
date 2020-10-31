package com.iftodi.reactive.client

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient

internal class WebStockClientTest {

    val webClient = WebClient.builder().build();

    @Test
    fun testReadStockSymbols() {
        //given
        val webStockClient = WebStockClient(webClient)

        //when
        val stockValueSymbolReader = webStockClient.readStockSymbol("SYMBOL");

        //then
        assertThat(stockValueSymbolReader.blockFirst()?.symbol).isEqualTo("SYMBOL");
        assertThat(stockValueSymbolReader.take(5).count().block()).isEqualTo(5);
    }

}