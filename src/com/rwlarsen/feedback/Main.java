package com.rwlarsen.feedback;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layout.fxml"));
        PairGenerator pairGenerator = new PairGeneratorImpl();
        TeamDataStore dataStore = new FileTeamDataStore();
        loader.setController(new Controller(pairGenerator, dataStore));
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
