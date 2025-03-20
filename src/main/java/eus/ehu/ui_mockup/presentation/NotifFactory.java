package eus.ehu.ui_mockup.presentation;

import atlantafx.base.controls.Notification;
import atlantafx.base.theme.Styles;
import atlantafx.base.util.Animations;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2MZ;

public class NotifFactory {

    public void showMessage(StackPane content){
        final var msg = new Notification(
                "Incorrect username or password entered",
                new FontIcon(Material2MZ.WARNING)
        );

        msg.getStyleClass().addAll(
                Styles.DANGER, Styles.ELEVATED_1
        );
        msg.setPrefHeight(Region.USE_PREF_SIZE);
        msg.setMaxHeight(Region.USE_PREF_SIZE);
        StackPane.setAlignment(msg, Pos.TOP_RIGHT);
        StackPane.setMargin(msg, new Insets(10, 10, 0, 0));



        msg.setOnClose(e -> {
            var out = Animations.slideOutUp(msg, Duration.millis(250));
            out.setOnFinished(f -> content.getChildren().remove(msg));
            out.playFromStart();
            content.setVisible(false);
        });

        var in = Animations.slideInDown(msg, Duration.millis(750));
        content.getChildren().add(msg);
        content.setVisible(true);
        in.playFromStart();
    }
}
