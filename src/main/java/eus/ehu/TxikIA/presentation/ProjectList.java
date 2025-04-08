package eus.ehu.TxikIA.presentation;

import atlantafx.base.theme.Styles;
import eus.ehu.TxikIA.domain.Project;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class ProjectList {

    private List<Project> projects;

    ListIterator<Project> iterator;

    private FxmlCacher cacher;

    public ProjectList(List<Project> project_list) {
        this.projects = project_list;
        this.iterator = projects.listIterator();
    }


    /*

    public List<Project> iteratePrevProjects(){

        int i = 0;
        int next_remaining_elements = projects.size() - iterator.nextIndex()-1;

        List<Project> prev_projects = new ArrayList<>();

        iterator = projects.listIterator(iterator.nextIndex() - 5);

        while (iterator.hasPrevious() && i < 5) {
                prev_projects.addFirst(iterator.previous());
                ++i;
        }

        iterator = projects.listIterator(iterator.nextIndex() + 5);


        return prev_projects;
    }


    public List<Project> iterateNextProjects(){
        int i = 0;
        List<Project> next_projects = new ArrayList<>();

            while (iterator.hasNext() && i < 5) {
                next_projects.add(iterator.next());
                ++i;
            }

        return next_projects;
    }
    */

    public List<Project> iterateProjectPages(int page, int per_page) {
        int i = page*per_page;
        List<Project> project_list = new ArrayList<>();
        while(i < projects.size() && i<(page*per_page)+per_page) {
            project_list.add(projects.get(i));
            ++i;
        }
        return project_list;
    }

    VBox loadProjectList(Pane content_pane, StackPane stack_pane, int page, int per_page) {

        VBox content = new VBox();

        Accordion project_accordion = new Accordion();

        List<Project> projects = iterateProjectPages(page, per_page);

        Stage stage = (Stage) content_pane.getScene().getWindow();



        for (Project project : projects) {
            ProjectEntry proekt = new ProjectEntry(project,content_pane,stack_pane);
            project_accordion.getPanes().add(proekt.projectEntryPane);
        }


        content.getChildren().add(project_accordion);

        content.setAlignment(Pos.CENTER);
        content.setSpacing(16);

        // box.getChildren().addAll(entry.getProjectEntryPane());
        return content;
    }

     public class ProjectEntry {
        @FXML
        private TitledPane projectEntryPane;

        @FXML
        private ButtonBar buttons = new ButtonBar();

        @FXML
        private Label name = new Label();

        @FXML
        private Label description = new Label();

        @FXML
        private Label createdAt = new Label();

        @FXML
        private Label lastModified = new Label();

        @FXML
        private Label subject = new Label();

        @FXML
        private ModalFactory editModal = new ModalFactory("projects/edit-project-view.fxml");

        @FXML
        private ModalFactory deleteModal = new ModalFactory("projects/delete-project-view.fxml");

        private Project cur_project;

        public ProjectEntry(Project p, Pane content_pane, StackPane stack_pane) {

            // Maybe we can use bindings to resize our content
            // and achieve a responsive design.............

            cur_project = p;

            String project_name = p.getName();
            String project_description = p.getDescription();


            stack_pane.getChildren().addAll(editModal.getModalPane(),deleteModal.getModalPane());

            VBox entry_content = new VBox();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            name.setText(project_name);
            description.setText(project_description);
            description.setWrapText(true);
            description.prefWidthProperty().bind(content_pane.widthProperty());
            createdAt.setText("Created at: "+formatter.format(p.getCreatedAt()));
            lastModified.setText("Last modified at: "+formatter.format(p.getUpdatedAt()));
            subject.setText("#"+p.getSubject());
            subject.setStyle("-fx-text-fill: red");


            Button openButton = new Button("Open");
            openButton.getStyleClass().add(Styles.SUCCESS);
            openButton.getStyleClass().add(Styles.BUTTON_OUTLINED);
            openButton.setOnAction(event -> openProject(content_pane));

            Button editButton = new Button("Edit");
            editButton.getStyleClass().add(Styles.ACCENT);
            editButton.getStyleClass().add(Styles.BUTTON_OUTLINED);
            editButton.setOnAction(event -> editProject(stack_pane));

            Button deleteButton = new Button("Delete");
            deleteButton.getStyleClass().add(Styles.DANGER);
            deleteButton.getStyleClass().add(Styles.BUTTON_OUTLINED);
            deleteButton.setOnAction(event -> deleteProject(stack_pane));

            buttons.getButtons().addAll(openButton, editButton, deleteButton);


            entry_content.setAlignment(Pos.CENTER_LEFT);
            entry_content.setSpacing(10);
            entry_content.getChildren().addAll(new TextFlow(description),
                    createdAt,
                    lastModified,
                    subject,
                    buttons);

            projectEntryPane = new TitledPane(name.getText()
                    ,entry_content);



            projectEntryPane.setExpanded(false);

        }

         public TitledPane getProjectEntryPane() {
             return projectEntryPane;
         }

         void openProject(Pane pane) {
            Stage stage = (Stage) buttons.getScene().getWindow();
            stage.setMinWidth(600);
            stage.setMinHeight(480);
            stage.setWidth(600);
            stage.setHeight(480);
            cacher = new FxmlCacher("projects/project-view.fxml");
            cacher.loadContent(pane);
         }

         void editProject(StackPane pane) {
            Project.setCurrent_UUID(cur_project.getUUID());
            System.out.println("Selected to Edit Project with UUID = "+ Project.getCurrent_UUID());
            editModal.openModal(pane);
         }

         void deleteProject(StackPane pane) {
            Project.setCurrent_UUID(cur_project.getUUID());
            System.out.println("Selected to Delete Project with UUID = "+ Project.getCurrent_UUID());
            deleteModal.openModal(pane);
         }

         public Project getCur_project() {
             return cur_project;
         }

     }


}
