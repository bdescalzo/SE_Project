package eus.ehu.ui_mockup.presentation;

import atlantafx.base.theme.Styles;
import eus.ehu.ui_mockup.business_logic.BInterface;
import eus.ehu.ui_mockup.business_logic.BusinessLogic;
import eus.ehu.ui_mockup.domain.Project;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2MZ;


import eus.ehu.ui_mockup.domain.User;

import java.io.IOException;
import java.util.List;

public class ProjectController {

    @FXML
    private AnchorPane projectViewPane;

    @FXML
    private AnchorPane openedPane;

    @FXML
    private Pagination projectPager;



    @FXML
    private TitledPane projectTitledPane6;

    @FXML
    private TitledPane projectTitledPane7;

    @FXML
    private TitledPane projectTitledPane8;

    @FXML
    private MenuButton userButton;

    @FXML
    private Label welcomeMessage;

    private BInterface bizLogic = new BusinessLogic();

    private ProjectList projectList ;

    @FXML
    void initialize() {

        userButton.setGraphic(new FontIcon(Material2MZ.PERSON_OUTLINE));



        List<Project> projects = bizLogic.retrieveProjects(User.getId_static());
        projectList = new ProjectList(projects);

        System.out.println("Project numbers = "+projects.size());

        projectPager.setPageCount(3);
        projectPager.setMaxPageIndicatorCount(3);
        projectPager.setStyle("-fx-arrows-visible: false");
        if(projects.size() > 0) {
            ProjectList.ProjectEntry entry = projectList.new ProjectEntry(projects.get(0));

            projectPager.setPageFactory(index -> {
                VBox box = new VBox();
                box.setAlignment(Pos.CENTER);
                box.setSpacing(10);
                box.getChildren().add(entry.getProjectEntryPane());
                return box;

            });
        }
        welcomeMessage.setText("Welcome back, "+ User.getUsername_static()+"!");


    }


    void preloadProjectOpened(){
        try {
            FXMLLoader fxmlLoader_login = new FXMLLoader(getClass().getResource("chat-view.fxml"));
            openedPane = fxmlLoader_login.load();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
