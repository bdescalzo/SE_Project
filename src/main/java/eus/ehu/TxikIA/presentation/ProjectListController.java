package eus.ehu.TxikIA.presentation;

import atlantafx.base.theme.Styles;
import eus.ehu.TxikIA.business_logic.BInterface;
import eus.ehu.TxikIA.business_logic.BusinessLogic;
import eus.ehu.TxikIA.data_access.DBController;
import eus.ehu.TxikIA.domain.Project;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;


import eus.ehu.TxikIA.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectListController {

    @FXML
    private GridPane projectViewPane;

    @FXML
    private Pagination projectPager;

    @FXML
    private MenuButton userButton;

    @FXML
    private Label welcomeMessage;

    @FXML
    private StackPane modalArea;

    @FXML
    private ButtonBar create_options = new ButtonBar();

    @FXML
    private Button createSampleProjectButton = new Button("Add Blank Project", new FontIcon(Material2MZ.WARNING));

    @FXML
    private Button createProjectButton = new Button("Create new Project", new FontIcon(Material2AL.CREATE_NEW_FOLDER));

    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

    private BInterface bizLogic = new BusinessLogic();

    private ProjectList projectList ;

    ModalBuilder onboardingModal = new ModalBuilder("modals/info/onboarding-modal.fxml");

    ModalBuilder logoutModal = new ModalBuilder("modals/inputs/logout-modal.fxml");

    ModalBuilder newProjectModal = new ModalBuilder("projects/create-project-view.fxml");

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(ProjectListController.class);

    final private int projects_per_page = 5;

    
    @FXML
    void warnLogOut() {
        logoutModal.openModal(modalArea);
    }
    
    
    @FXML
    void createSampleProject() {
        Project project = new Project();
        project.setName("Debug project");
        project.setDescription("Debug project");
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(project.getCreatedAt());
        bizLogic.createProject(User.getId_static(),project);
    }

    @FXML
    void createNewProject() {
        newProjectModal.openModal(modalArea);
    }

    @FXML
    void build_decoration(){

        projectViewPane.setMaxWidth(Double.MAX_VALUE);
        projectViewPane.setMaxHeight(Double.MAX_VALUE);

        userButton.setGraphic(new FontIcon(Material2MZ.PERSON_OUTLINE));

        createSampleProjectButton.setGraphic(new FontIcon(Material2MZ.WARNING));
        createSampleProjectButton.getStyleClass().addAll(Styles.BUTTON_OUTLINED, Styles.DANGER);
        createSampleProjectButton.setOnAction(event -> createSampleProject());

        createProjectButton.setGraphic(new FontIcon(Material2AL.CREATE_NEW_FOLDER));
        createProjectButton.getStyleClass().addAll(Styles.BUTTON_OUTLINED, Styles.ACCENT);
        createProjectButton.setOnAction(event -> createNewProject());

        create_options.getButtons().addAll(createSampleProjectButton,createProjectButton);
        create_options.setPadding(new Insets(10,0,0,0));


        modalArea.getChildren().addAll(newProjectModal.getModalPane(),
                                       logoutModal.getModalPane(),
                                        onboardingModal.getModalPane());
    }


    @FXML
    void initialize() {

        build_decoration();


        Platform.runLater(() -> {

            scene = projectViewPane.getScene();
            stage = (Stage) projectViewPane.getScene().getWindow();

            stage.setMinHeight(520);
            stage.setMinWidth(360);

            modalArea.prefWidthProperty().bind(stage.widthProperty());
            modalArea.prefHeightProperty().bind(stage.heightProperty());

        });



        List<Project> projects = bizLogic.retrieveProjects(User.getId_static());
        projectList = new ProjectList(projects);

        projectPager.setStyle("-fx-arrows-visible: false");


        if (!projects.isEmpty()) {

            int page_number = (projects.size() / 5) + 1;

            projectPager.setMaxPageIndicatorCount(page_number);
            projectPager.setPageCount(page_number);


            projectPager.setPageFactory(index -> {
                VBox page = projectList.loadProjectList(projectViewPane,
                        modalArea,
                        projectPager.currentPageIndexProperty().intValue(),
                        projects_per_page);

                page.getChildren().add(create_options);

                Accordion accordion = (Accordion) page.getChildren().getFirst();


                accordion.expandedPaneProperty().addListener((obs, oldHeight, newHeight) -> {
                    if (accordion.getExpandedPane() != null) {
                        stage.setHeight(stage.getHeight() + 240);
                    } else {
                        stage.setHeight(stage.getHeight() - 240);
                    }
                });
                projectPager.currentPageIndexProperty().addListener((obs, oldPage, newPage) -> {
                    accordion.setExpandedPane(null);
                });


                return page;
            });

        } else {

            projectPager.setPageFactory(index -> {


                projectPager.setMaxPageIndicatorCount(1);
                projectPager.setPageCount(1);

                VBox no_projects = new VBox();
                no_projects.setAlignment(Pos.CENTER);

                Label empty_list_message = new Label("No projects found! Let's create our first project");
                empty_list_message.setFont(new Font(20));
                empty_list_message.setTextFill(Color.web("#6b6868"));
                empty_list_message.setWrapText(true);
                empty_list_message.setPrefWidth(180);
                empty_list_message.setTextAlignment(TextAlignment.CENTER);

                no_projects.getChildren().addAll(empty_list_message, create_options);

                return no_projects;
            });
            if (bizLogic.firstLogin(User.getId_static())) {
                log.warn("This is the user's first login: Showing onboarding modal.....");
                Platform.runLater(() -> {
                    stage.setWidth(720);
                    stage.setHeight(540);
                    stage.setMinWidth(720);
                    stage.setMinHeight(420);
                    modalArea.prefWidthProperty().bind(stage.widthProperty());
                    modalArea.prefHeightProperty().bind(stage.heightProperty());
                    onboardingModal.openModal(modalArea);
                });
            }
        }

        projectPager.setVisible(true);

        welcomeMessage.setText("Welcome back, " + User.getUsername_static() + "!");

    }
}
