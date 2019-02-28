package at.fh.bac;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class Controller {

    private SceneController sceneController = new SceneController();


    /**
     * parse scene name to load to SceneController
     * @param event : ActionEvent needed to get the Stage
     * @throws Exception : Handles Exceptions
     */
    @FXML
    private void changeScene(ActionEvent event) throws Exception{

        sceneController.changeScene("main.fxml", event);

    }

    @FXML
    private void quit(){
        System.exit(0);
    }
}
