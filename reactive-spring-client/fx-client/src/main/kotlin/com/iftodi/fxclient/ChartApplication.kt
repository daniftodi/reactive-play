package com.iftodi.fxclient

import javafx.application.Application
import javafx.application.Platform
import javafx.stage.Stage
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ApplicationEvent
import org.springframework.context.ConfigurableApplicationContext

@SpringBootApplication
class ChartApplication() : Application() {
    lateinit var applicationContext : ConfigurableApplicationContext

    override fun init() {
        applicationContext = SpringApplicationBuilder(ChartApplication::class.java).run()
    }

    override fun stop() {
        applicationContext.stop()
        Platform.exit()
    }

    override fun start(primaryStage: Stage?) {
        applicationContext.publishEvent(StageReadyEvent(primaryStage))
    }

}

class StageReadyEvent(stage: Stage?) : ApplicationEvent(stage!!) {
    fun getStage() : Stage {
        return getSource() as Stage
    }
}
