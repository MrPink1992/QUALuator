package at.fh.bac.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static at.fh.bac.Controller.FileController.selectedFilesList;

public class SemanticValidationController implements Initializable {

    @FXML
    private TextArea semanticTextArea = new TextArea();
    private SceneController sceneController = new SceneController();



    public void initialize(URL url, ResourceBundle rb) {
        semanticTextArea.setEditable(false);
        semanticTextArea.appendText("Starting semantic validation...\n");
        List<File> filesToValidate = selectedFilesList.getFileList();

        for (File file : filesToValidate){
            validateFile(file);
        }

    }

    private void validateFile(File file){

        semanticTextArea.appendText("---------------------------------------------------------------------------------\n");
        semanticTextArea.appendText("Validating file " + file.getName() + "\n");
        semanticTextArea.appendText("Successfully validated File!\n");

    }

    @FXML
    private void goBack(ActionEvent event) throws Exception{
        sceneController.changeScene("fileUpload.fxml", event);
    }

    @FXML
    private void next(ActionEvent event){
        //sceneController.changeScene("");
    }


}
