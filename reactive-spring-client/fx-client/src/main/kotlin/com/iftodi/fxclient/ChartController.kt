package com.iftodi.fxclient

import com.iftodi.reactive.client.StockClient
import javafx.application.Platform
import javafx.collections.FXCollections.observableArrayList
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.chart.LineChart
import javafx.scene.chart.XYChart.Data
import javafx.scene.chart.XYChart.Series
import org.springframework.stereotype.Component

@Component
class ChartController(val webClient: StockClient) {

    @FXML
    lateinit var chart: LineChart<String, Double>


    @FXML
    fun initialize() {
        val symbols = arrayOf("USD", "EUR", "RON", "RON")
        val data = observableArrayList<Series<String, Double>>()
        chart.data = data;

        symbols.forEach {
            subscribeToStockSymbol(it, data)
        }
    }

    private fun addObservableSeriesData(data: ObservableList<Series<String, Double>>, symbol: String):
            ObservableList<Data<String, Double>> {
        val seriesData: ObservableList<Data<String, Double>> = observableArrayList();
        data.add(Series<String, Double>(symbol, seriesData))
        return seriesData
    }

    private fun subscribeToStockSymbol(symbol: String, data: ObservableList<Series<String, Double>>) {
        val seriesData: ObservableList<Data<String, Double>> = addObservableSeriesData(data, symbol)
        webClient.readStockSymbol(symbol).subscribe {
            Platform.runLater {
                seriesData.add(Data(it.dateTime.second.toString(), it.value))
            }
        }
    }
}