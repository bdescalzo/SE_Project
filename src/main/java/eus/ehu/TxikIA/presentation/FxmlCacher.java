package eus.ehu.TxikIA.presentation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FxmlCacher {

    private String fxmlFileName;
    private FXMLLoader loader;

    private static Map<String, Pane> cache = new HashMap<>();

    public FxmlCacher(String fxmlFileName) {
        this.fxmlFileName = fxmlFileName;
        loader = new FXMLLoader(getClass().getResource(fxmlFileName));
    }

    public void loadContent(Pane contentPane)  {

        String filename = getFxmlFileName();

        Stage stage = (Stage) contentPane.getScene().getWindow();

        Pane content = null;
        Scene scene = null;

        try {
            content = cache.get(filename);
            if (content == null) {
                content = loader.load();
                cache.put(filename, content);
                scene = new Scene(content);
            } else {
                scene = content.getScene();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage.setScene(scene);
        stage.show();

    }

    public void loadModal(Pane contentPane) {
        String filename = getFxmlFileName();

        try {
            Pane content = cache.get(filename);
            if (content == null) {
                content = loader.load();
                cache.put(filename, content);
            }
            contentPane.getChildren().setAll(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFxmlFileName() {
        return fxmlFileName;
    }

}
