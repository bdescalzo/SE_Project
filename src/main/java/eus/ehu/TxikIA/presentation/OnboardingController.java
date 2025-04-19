package eus.ehu.TxikIA.presentation;

import atlantafx.base.util.Animations;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;


import java.util.List;
import java.util.Objects;

public class OnboardingController {

    @FXML
    private VBox contentVbox;

    @FXML
    private Pagination tipPager;

    private final List<String> captions = List.of("You can view and edit your existing projects",
                                                  "Most work is done inside a Project");
    private final List<String> pics = List.of("pictures/edit_project.gif",
                                                "pictures/open_project.gif");
    @FXML
    private Button nextButton;

    @FXML
    private Button prevButton;

    @FXML
    void initialize() {

        var animation = Animations.fadeInDown(contentVbox, Duration.seconds(2));
        animation.playFromStart();

        tipPager.setPageCount(captions.size());
        tipPager.setMaxPageIndicatorCount(captions.size());

        tipPager.setPageFactory(index -> {
            VBox cur_page = new VBox();

            cur_page.setAlignment(Pos.CENTER);

            Label cur_page_caption = new Label(captions.get(index));

            cur_page_caption.setFont(new Font(20));
            cur_page_caption.setTextFill(Color.web("#6b6868"));
            cur_page_caption.setWrapText(true);
            cur_page_caption.setTextAlignment(TextAlignment.CENTER);

            ImageView cur_page_image = new ImageView(Objects.requireNonNull(getClass().getResource(pics.get(index))).toExternalForm());

            cur_page_image.setPreserveRatio(true);
            cur_page_image.setSmooth(true);
            cur_page_image.setCache(true);

            cur_page_image.fitWidthProperty().bind(cur_page.widthProperty());
            cur_page_image.fitHeightProperty().bind(cur_page.heightProperty().multiply(0.9));

            cur_page.getChildren().addAll(cur_page_caption, cur_page_image);

            return cur_page;
        });
    }

}

