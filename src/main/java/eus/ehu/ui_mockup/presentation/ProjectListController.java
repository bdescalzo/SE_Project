package eus.ehu.ui_mockup.presentation;

import atlantafx.base.theme.Styles;
import eus.ehu.ui_mockup.business_logic.BInterface;
import eus.ehu.ui_mockup.business_logic.BusinessLogic;
import eus.ehu.ui_mockup.domain.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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
    private VBox emptyProjectList;

    @FXML
    private Pagination projectPager;


    @FXML
    private MenuButton userButton;

    @FXML
    private Label welcomeMessage;

    @FXML
    private StackPane modalArea;

    @FXML
    private Label emptyListMsg;

    @FXML
    private ButtonBar create_options = new ButtonBar();

    @FXML
    private Button createSampleProjectButton = new Button("Add Blank Project", new FontIcon(Material2MZ.WARNING));

    @FXML
    private Button createProjectButton = new Button("Create new Project", new FontIcon(Material2AL.CREATE_NEW_FOLDER));

    private BInterface bizLogic = new BusinessLogic();

    private ProjectList projectList ;

    ModalFactory newModal = new ModalFactory("projects/create-project-view.fxml");

    final private int projects_per_page = 5;

    @FXML
    void createSampleProject() {
        Project project = new Project();
        project.setName("Debug project");
        project.setDescription("Debug project");
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        bizLogic.createProject(User.getId_static(),project);
    }

    @FXML
    void createNewProject() {
        newModal.openModal(modalArea);
    }

    @FXML
    void initialize() {

        userButton.setGraphic(new FontIcon(Material2MZ.PERSON_OUTLINE));
        createSampleProjectButton.setGraphic(new FontIcon(Material2MZ.WARNING));
        createSampleProjectButton.getStyleClass().addAll(Styles.BUTTON_OUTLINED, Styles.DANGER);
        createSampleProjectButton.setOnAction(event -> createSampleProject());
        createProjectButton.setGraphic(new FontIcon(Material2AL.CREATE_NEW_FOLDER));
        createProjectButton.getStyleClass().addAll(Styles.BUTTON_OUTLINED, Styles.ACCENT);
        createProjectButton.setOnAction(event -> createNewProject());

        create_options.getButtons().addAll(createSampleProjectButton,createProjectButton);
        create_options.setPadding(new Insets(10,0,0,0));


        modalArea.getChildren().add(newModal.getModalPane());

        List<Project> projects = bizLogic.retrieveProjects(User.getId_static());
        projectList = new ProjectList(projects);

        if(!projects.isEmpty()) {
            projectPager.setStyle("-fx-arrows-visible: false");

            int page_number = (projects.size()/5)+1;

            projectPager.setMaxPageIndicatorCount(page_number);
            projectPager.setPageCount(page_number);
            projectPager.setPageFactory(index -> {
                VBox page = new VBox();
                page.getChildren().addAll(
                    projectList.loadProjectList(projectViewPane,
                        modalArea,
                        projectPager.currentPageIndexProperty().intValue(),
                        projects_per_page),

                    create_options);
                return page;
            });

            projectPager.setVisible(true);
        } else {
            emptyProjectList.getChildren().addAll(create_options);
            emptyProjectList.setVisible(true);
            emptyProjectList.setDisable(false);
        }

        welcomeMessage.setText("Welcome back, "+ User.getUsername_static()+"!");
    }
}
