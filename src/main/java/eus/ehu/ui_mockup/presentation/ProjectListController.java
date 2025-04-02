package eus.ehu.ui_mockup.presentation;

import eus.ehu.ui_mockup.business_logic.BInterface;
import eus.ehu.ui_mockup.business_logic.BusinessLogic;
import eus.ehu.ui_mockup.domain.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2MZ;


import eus.ehu.ui_mockup.domain.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class ProjectListController {

    @FXML
    private AnchorPane projectViewPane;

    @FXML
    private AnchorPane openedPane;

    @FXML
    private Pagination projectPager;


    @FXML
    private MenuButton userButton;

    @FXML
    private Label welcomeMessage;

    @FXML
    private Button createProjectButton;

    @FXML
    private StackPane modalArea;

    @FXML
    private WebView chatWindow;

    private BInterface bizLogic = new BusinessLogic();

    private ProjectList projectList ;

    ModalFactory newModal = new ModalFactory("register-view.fxml");

    @FXML
    void createProject(ActionEvent event) {
        Project project = new Project();
        project.setName("Debug project");
        project.setDescription("Debug project");
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        bizLogic.createProject(User.getId_static(),project);
    }

    @FXML
    void initialize() {

        userButton.setGraphic(new FontIcon(Material2MZ.PERSON_OUTLINE));
        createProjectButton.setGraphic(new FontIcon(Material2MZ.WARNING));

        modalArea.getChildren().add(newModal.getModalPane());

        List<Project> projects = bizLogic.retrieveProjects(User.getId_static());
        projectList = new ProjectList(projects);


        if(!projects.isEmpty()) {
            projectPager.setStyle("-fx-arrows-visible: false");
            int project_num_per_page = (projects.size()/5)+1;
            ProjectList.ProjectEntry entry = projectList.new ProjectEntry(projects.getFirst(),projectViewPane,modalArea);
            projectPager.setMaxPageIndicatorCount(project_num_per_page);
            projectPager.setPageCount(project_num_per_page);
            projectPager.setPageFactory(index -> {
                VBox box = new VBox();
                box.setAlignment(Pos.CENTER);
                box.setSpacing(10);
                box.getChildren().add(entry.getProjectEntryPane());

                return box;

            });
            projectPager.setVisible(true);
        }
        welcomeMessage.setText("Welcome back, "+ User.getUsername_static()+"!");
    }
}
