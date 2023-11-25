package com.example.marlonmania;

import com.example.marlonmania.Controllers.GameListController;
import com.example.marlonmania.Controllers.GameMatrixController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) {
/*        openWindow("new-Game-Setup.fxml");*/
        openGameWindow("game-matrix.fxml","Gabriel",0);
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

    public static void openGameWindow(String fxml, String nickName,int level){
        System.out.println("open game" + level);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("MARLON-MANIA");
            stage.setScene(scene);



            if(fxmlLoader.getController() instanceof GameListController){
                GameListController controller = fxmlLoader.getController();
                controller.setLevel(level);
                controller.setNickNameLabel(nickName);



            } else if (fxmlLoader.getController() instanceof GameMatrixController) {
                GameMatrixController controller = fxmlLoader.getController();
                controller.setLevel(level);
                controller.setNickNameLabel(nickName);



            }


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