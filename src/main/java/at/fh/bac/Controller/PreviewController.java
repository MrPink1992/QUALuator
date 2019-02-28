package at.fh.bac.Controller;


import at.fh.bac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import javax.annotation.Resource;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static at.fh.bac.Controller.MainController.selectedFilesList;

public class PreviewController implements Initializable {

    private SceneController sceneController = new SceneController();

    @FXML private TextArea fileNames;


    private List<File> selectedFiles;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        System.out.println("You selected following files: " + selectedFilesList.toString());


        for (File file : selectedFilesList.getFileList()) {
            fileNames.appendText(file.getName().toString() + "\n");
            System.out.print(file.toString());
        }
    }



    @FXML
    private void goBack(ActionEvent event) throws Exception{
        sceneController.changeScene("main.fxml", event);
    }


    private Stage getStage(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        return window;
    }

}
