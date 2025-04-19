package eus.ehu.TxikIA.presentation;

import eus.ehu.TxikIA.business_logic.BInterface;
import eus.ehu.TxikIA.business_logic.BusinessLogic;
import eus.ehu.TxikIA.domain.Project;
import eus.ehu.TxikIA.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class RemoveProjectController {

    final private BInterface blogic = new BusinessLogic();

    @FXML
    void deleteProject(ActionEvent event) {
        blogic.deleteProject(User.getId_static(),Project.getCurrent_UUID());
    }

    @FXML
    void initialize() {

    }

}
