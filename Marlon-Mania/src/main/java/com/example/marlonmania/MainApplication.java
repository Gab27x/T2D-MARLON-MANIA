package com.example.marlonmania;

import com.example.marlonmania.Controllers.GameController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) {
        openWindow("menu.fxml");
    }

    public static void openWindow(String fxml){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("MARLON-MANIA");
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException ex){
            ex.printStackTrace();
        }


    }

    public static void openGameWindow(String fxml, String nickName){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("MARLON-MANIA");
            stage.setScene(scene);

            GameController controller = fxmlLoader.getController();

            controller.setNickNameLabel(nickName);



            stage.show();

        }
        catch (IOException ex){
            ex.printStackTrace();
        }


    }
    public static void closeWindow(Stage stage){
        stage.close();

    }



    public static void main(String[] args) {
        launch();
    }

}