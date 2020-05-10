package PresentationLayer;

import BusinessLayer.RoleCrudOperations.Role;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 650, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        //connectToDB();
        //getTeamNames();
        launch(args);


        int n=0;
    }



}
