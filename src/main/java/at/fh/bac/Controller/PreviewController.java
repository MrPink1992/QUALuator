package at.fh.bac.Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static at.fh.bac.Controller.FileController.selectedFilesList;

public class PreviewController implements Initializable {

    private SceneController sceneController = new SceneController();

    @FXML
    private ListView<String> fileListView;

    private ObservableList<String> fileNameList;

    private List<File> listOfFiles = selectedFilesList.getFileList();
    private List<String> listOfFileNames = new ArrayList<String>();

    @FXML
    private TextArea fileNames = new TextArea();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getFileNames();
        for (String file : listOfFileNames) {
            fileListView.getItems().add(file);
        }
    }

    private List<String> getFileNames() {

        for (File file : listOfFiles) {
            listOfFileNames.add(file.getName());
        }
        return fileNameList;
    }

    @FXML
    private void goToSyntaxValidation(ActionEvent event) throws Exception {
        sceneController.changeScene("syntaxValidation.fxml", event);
    }


    @FXML
    private void goBack(ActionEvent event) throws Exception {
        selectedFilesList.setFileList(null);
        sceneController.changeScene("main.fxml", event);
    }


}

