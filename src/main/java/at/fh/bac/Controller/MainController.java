package at.fh.bac.Controller;

import at.fh.bac.Main;
import at.fh.bac.Model.FileListModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import sun.reflect.annotation.ExceptionProxy;


import java.io.File;
import java.util.List;

public class MainController {

    private List<File> selectedFiles;
    private SceneController sceneController = new SceneController();
    public static FileListModel selectedFilesList = new FileListModel();


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
        selectedFilesList.setFileList(selectedFiles);

        // Main.files = selectedFiles;

        System.out.println(selectedFiles);

        sceneController.changeScene("preview.fxml", event);
        //PreviewController previewController = new PreviewController();
        //previewController.initialize();


    }

    @FXML
    private void navigateToMenu(ActionEvent event) throws Exception{
        sceneController.changeScene("menu.fxml", event);
    }

}