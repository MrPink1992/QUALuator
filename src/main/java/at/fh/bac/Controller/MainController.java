package at.fh.bac.Controller;

import at.fh.bac.Main;
import at.fh.bac.Model.FileListModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;


import java.io.File;
import java.util.List;

public class MainController {

    private SceneController sceneController = new SceneController();

    public static FileListModel selectedFilesList = new FileListModel();

    List<File> selectedFiles;


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

        // open the File-Dialog
        selectedFiles = fileChooser.showOpenMultipleDialog(SceneController.getStage(event));

        // save selected files to the FileListModel
        selectedFilesList.setFileList(selectedFiles);


        sceneController.changeScene("preview.fxml", event);


    }

    @FXML
    private void navigateToMenu(ActionEvent event) throws Exception{
        sceneController.changeScene("menu.fxml", event);
    }

}