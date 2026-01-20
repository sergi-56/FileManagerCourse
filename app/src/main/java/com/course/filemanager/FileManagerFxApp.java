package com.course.filemanager;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Минимальное JavaFX-приложение для файлового менеджера.
 * Используется как графическая версия приложения.
 */
public class FileManagerFxApp extends Application {

    @Override
    public void start(Stage stage) {
        Label label = new Label("File Manager (JavaFX)");
        Scene scene = new Scene(label, 400, 200);

        stage.setTitle("File Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
