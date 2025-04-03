package eus.ehu.ui_mockup.presentation;

import eus.ehu.ui_mockup.business_logic.BInterface;
import eus.ehu.ui_mockup.business_logic.BusinessLogic;
import eus.ehu.ui_mockup.domain.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
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
    private Button createSampleProjectButton;

    @FXML
    private StackPane modalArea;

    @FXML
    private Button createProjectButton;

    @FXML
    ModalFactory createModal = new ModalFactory("modals/info/coming-soon.fxml");

    final private int projects_per_page = 5;

    final private BInterface bizLogic = new BusinessLogic();

    private ProjectList projectList ;



    @FXML
    void createSampleProject(ActionEvent event) {
        Project project = new Project();
        project.setName("Debug project");
        project.setDescription("Debug project");
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        bizLogic.createProject(User.getId_static(),project);
    }

    @FXML
    void createNewProject(ActionEvent event) {;
        createModal.openModal(modalArea);
    }

    @FXML
    void initialize() {

        userButton.setGraphic(new FontIcon(Material2MZ.PERSON_OUTLINE));
        createSampleProjectButton.setGraphic(new FontIcon(Material2MZ.WARNING));
        createProjectButton.setGraphic(new FontIcon(Material2AL.CREATE_NEW_FOLDER));

        modalArea.getChildren().add(createModal.getModalPane());

        List<Project> projects = bizLogic.retrieveProjects(User.getId_static());
        projectList = new ProjectList(projects);

        if(!projects.isEmpty()) {

            final int page_number = (projects.size()/5)+1;


            projectPager.setStyle("-fx-arrows-visible: false");


            projectPager.setMaxPageIndicatorCount(page_number);
            projectPager.setPageCount(page_number);
            projectPager.setPageFactory(index -> projectList.loadProjectList(projectViewPane,
                                                modalArea,
                                                projectPager.currentPageIndexProperty().intValue(),
                                                projects_per_page));


            projectPager.setVisible(true);
        }

        welcomeMessage.setText("Welcome back, "+ User.getUsername_static()+"!");
    }
}
