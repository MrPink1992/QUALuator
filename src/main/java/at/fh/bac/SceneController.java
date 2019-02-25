package at.fh.bac;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {

    public void changeScene(String sceneName, ActionEvent event) throws Exception{
        Parent parent = FXMLLoader.load(getClass().getResource("/FXML/" + sceneName));
        Scene mainScene = new Scene(parent, 800, 600);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }
}
