package eus.ehu.TxikIA.presentation;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.h2.tools.Server;

import java.io.IOException;
import java.sql.SQLException;

public class EntryPoint extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {

        Server server = Server.createWebServer("-webPort","9092","-ifNotExists");
        server.start();


        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("welcome-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("TxikI/A");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}