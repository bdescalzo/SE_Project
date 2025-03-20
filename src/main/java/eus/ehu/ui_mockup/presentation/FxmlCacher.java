package eus.ehu.ui_mockup.presentation;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FxmlCacher {

    private String fxmlFileName;
    private FXMLLoader loader;

    private static Map<String, AnchorPane> cache = new HashMap<>();

    public FxmlCacher(String fxmlFileName) {
        this.fxmlFileName = fxmlFileName;
        loader = new FXMLLoader(getClass().getResource(fxmlFileName));
    }

    public void loadContent(AnchorPane contentPane) {
        String filename = getFxmlFileName();
        try {
            // Check if content is already cached
            AnchorPane content = cache.get(filename);
            if (content == null) {
                // If not cached, load it and store in cache
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
