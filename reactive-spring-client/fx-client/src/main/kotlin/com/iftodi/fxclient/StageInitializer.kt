package com.iftodi.fxclient

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationListener
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class StageInitializer(val context : ApplicationContext) : ApplicationListener<StageReadyEvent> {

    @Value("classpath:/chart.fxml")
    lateinit var chartFxml : Resource

    @Value("\${spring.application.ui.title}")
    lateinit var applicationTitle : String

    override fun onApplicationEvent(event: StageReadyEvent) {
        val stage = event.getStage();
        val fxmlLoader = FXMLLoader(chartFxml.url)
        fxmlLoader.setControllerFactory { aClass -> context.getBean(aClass) }

        val parent = fxmlLoader.load<Parent>()
        stage.scene = Scene(parent, 1800.0, 1200.0);
        stage.title = applicationTitle
        stage.show()
    }
}