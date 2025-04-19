package eus.ehu.TxikIA.presentation;

import eus.ehu.TxikIA.business_logic.BInterface;
import eus.ehu.TxikIA.business_logic.BusinessLogic;
import eus.ehu.TxikIA.domain.Project;
import eus.ehu.TxikIA.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;

public class CreateProjectController {

    @FXML
    private TextArea descriptionfField;

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
