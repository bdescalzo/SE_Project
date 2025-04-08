package eus.ehu.TxikIA.presentation;

import eus.ehu.TxikIA.business_logic.BInterface;
import eus.ehu.TxikIA.business_logic.BusinessLogic;
import eus.ehu.TxikIA.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EditProjectController {

    @FXML
    private Button applyButton;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField nameField;

    final private BInterface blogic = new BusinessLogic();

    @FXML
    void applyChanges(ActionEvent event) {
        String name = nameField.getText();
        String description = descriptionField.getText();
        blogic.editProject(User.getId_static(), name, description);
    }



}
