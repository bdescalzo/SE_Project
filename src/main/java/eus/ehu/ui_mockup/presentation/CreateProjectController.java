package eus.ehu.ui_mockup.presentation;

import eus.ehu.ui_mockup.business_logic.BInterface;
import eus.ehu.ui_mockup.business_logic.BusinessLogic;
import eus.ehu.ui_mockup.domain.Project;
import eus.ehu.ui_mockup.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;

public class CreateProjectController {

    @FXML
    private TextField descriptionfField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField tagsField;

    final private BInterface blogic = new BusinessLogic();

    @FXML
    void createProject(ActionEvent event) {
        LocalDateTime now = LocalDateTime.now();
        Project project = new Project();
        project.setName(nameField.getText());
        project.setDescription(descriptionfField.getText());
        project.setSubject(tagsField.getText());
        project.setCreatedAt(now);
        project.setUpdatedAt(now);
        blogic.createProject(User.getId_static(), project);



    }

}
