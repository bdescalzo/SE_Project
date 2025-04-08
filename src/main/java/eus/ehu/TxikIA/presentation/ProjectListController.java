package eus.ehu.TxikIA.presentation;

import atlantafx.base.theme.Styles;
import eus.ehu.TxikIA.business_logic.BInterface;
import eus.ehu.TxikIA.business_logic.BusinessLogic;
import eus.ehu.TxikIA.domain.Project;
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

    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

    private BInterface bizLogic = new BusinessLogic();

    private ProjectList projectList ;

    ModalFactory newModal = new ModalFactory("projects/create-project-view.fxml");

    final private int projects_per_page = 5;



    public void setStage(Stage stage) {
        this.stage = stage;
    }

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
        modalArea.prefWidthProperty().bind(stage.widthProperty());
        modalArea.prefHeightProperty().bind(stage.heightProperty());
        newModal.openModal(modalArea);
    }

    @FXML
    void build_decoration(){
        modalArea.prefWidthProperty().bind(projectViewPane.widthProperty());
        modalArea.prefHeightProperty().bind(projectViewPane.heightProperty());

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


        modalArea.getChildren().add(newModal.getModalPane());
    }

    @FXML
    void initialize() {

        build_decoration();

        List<Project> projects = bizLogic.retrieveProjects(User.getId_static());
        projectList = new ProjectList(projects);


        if(!projects.isEmpty()) {
            projectPager.setStyle("-fx-arrows-visible: false");

            int page_number = (projects.size()/5)+1;

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


            projectPager.setVisible(true);



        } else {

            VBox no_projects = new VBox();
            no_projects.setAlignment(Pos.CENTER);

            Label empty_list_message = new Label("No projects found! Let's create our first project");
            empty_list_message.setFont(new Font(20));
            empty_list_message.setTextFill(Color.web("#6b6868"));
            empty_list_message.setWrapText(true);
            empty_list_message.setPrefWidth(180);
            empty_list_message.setTextAlignment(TextAlignment.CENTER);

            no_projects.getChildren().addAll(empty_list_message,create_options);
            // projectViewPane.getChildren().addFirst(no_projects);
            projectViewPane.add(no_projects,0,1);
        }

        welcomeMessage.setText("Welcome back, "+ User.getUsername_static()+"!");


        projectViewPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            scene = newScene;
            scene.windowProperty().addListener((obs1, oldScene1, newScene1) -> {

                stage = (Stage) projectViewPane.getScene().getWindow();

                stage.setMinHeight(520);
                stage.setMinWidth(360);

            });
        });

    }

}
