package eus.ehu.ui_mockup.presentation;

import atlantafx.base.controls.ModalPane;
import eus.ehu.ui_mockup.business_logic.BInterface;
import eus.ehu.ui_mockup.business_logic.BusinessLogic;
import eus.ehu.ui_mockup.domain.Project;
import eus.ehu.ui_mockup.domain.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.UUID;

public class RemoveProjectController {

    @FXML
    private VBox container;
    @FXML
    private Button cancelButton;

    @FXML
    private Button deleteButton;

    final private BInterface blogic = new BusinessLogic();

    @FXML
    void deleteProject(ActionEvent event) {
        System.out.println("Proekt = "+Project.getCurrent_UUID());
        blogic.deleteProject(User.getId_static(),Project.getCurrent_UUID());
    }

    @FXML
    void initialize() {

    }

}
