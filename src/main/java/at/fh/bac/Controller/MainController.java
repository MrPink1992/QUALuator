package at.fh.bac;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.util.List;

public class MainController {

    public List<File> selectedFiles;
    private SceneController sceneController = new SceneController();



    /**
     * Select files from dropdown menu
     * //TODO Restrict file upload to .xml/.bpmn
     * //TODO Persist filenames
     * @param event : ActionEvent needed to get the stage
     * @throws Exception : handles exceptions
     */
    @FXML
    private void filePicker(ActionEvent event) throws Exception {

        FileChooser fileChooser = new FileChooser();
        selectedFiles = fileChooser.showOpenMultipleDialog(SceneController.getStage(event));
        Main.files = selectedFiles;

        System.out.println(selectedFiles);

        sceneController.changeScene("preview.fxml", event);
        PreviewController previewController = new PreviewController();
        previewController.initialize(event);


    }

    @FXML
    private void navigateToMenu(ActionEvent event) throws Exception{
        sceneController.changeScene("menu.fxml", event);
    }

}