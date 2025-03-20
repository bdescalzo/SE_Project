package eus.ehu.ui_mockup.presentation;

import atlantafx.base.theme.Styles;
import eus.ehu.ui_mockup.domain.Project;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;


public class ProjectList {

    private List<Project> projects;

    public ProjectList(List<Project> project_list) {
        this.projects = project_list;
    }

    VBox loadProjectList(){

        ButtonBar buttonBar = new ButtonBar();

        VBox content = new VBox();

        Label info = new Label("Description: Analisi Matematikoa \n" +
                "Created: 2025-03-14\n" +
                "Last Modified: 2025-03-16\n" +
                "Subject: Analisi Matematikoa\n");
        info.setStyle("-fx-padding: 0 0 12 0");

        content.getChildren().addAll(info,buttonBar);




        Project p = new Project();
        p.setName("Prueba DEBUG DEBUG DEBUG");
        ProjectEntry entry = new ProjectEntry(p);
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(16);
        box.getChildren().addAll(entry.getProjectEntryPane());
        return box;
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

        public ProjectEntry(Project p) {


            description.setWrapText(true);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            name.setText(p.getName());
            description.setText(p.getDescription());
            createdAt.setText("Created at: "+formatter.format(p.getCreatedAt()));
            lastModified.setText("Last modified at: "+formatter.format(p.getUpdatedAt()));
            subject.setText("#"+p.getSubject());
            subject.setStyle("-fx-text-fill: red");


            Button openButton = new Button("Open");
            openButton.getStyleClass().add(Styles.SUCCESS);
            openButton.getStyleClass().add(Styles.BUTTON_OUTLINED);
            openButton.setOnAction(event -> openProject());

            Button editButton = new Button("Edit");
            editButton.getStyleClass().add(Styles.ACCENT);
            editButton.getStyleClass().add(Styles.BUTTON_OUTLINED);
            editButton.setOnAction(event -> editProject());

            Button deleteButton = new Button("Delete");
            deleteButton.getStyleClass().add(Styles.DANGER);
            deleteButton.getStyleClass().add(Styles.BUTTON_OUTLINED);
            deleteButton.setOnAction(event -> deleteProject());

            buttons.getButtons().addAll(openButton, editButton, deleteButton);

            VBox entry_content = new VBox();
            entry_content.setAlignment(Pos.CENTER_LEFT);
            entry_content.setSpacing(10);
            entry_content.getChildren().addAll(description,
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

         void openProject() {

         }

         void editProject() {

         }

         void deleteProject() {

         }

     }
}
