package com.iftodi.reactive.client

import java.time.ZonedDateTime

data class StockValue(val symbol: String,
                      val value: Double,
                      val dateTime: ZonedDateTime)
