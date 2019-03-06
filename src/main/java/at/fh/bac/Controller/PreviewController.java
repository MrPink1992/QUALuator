package at.fh.bac.Controller;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import static at.fh.bac.Controller.MainController.selectedFilesList;

public class PreviewController implements Initializable {

    private SceneController sceneController = new SceneController();

    @FXML
    private ListView<String> fileListView;

    private ObservableList<String> fileNameList;

    private List<File> listOfFiles = selectedFilesList.getFileList();
    private List<String> listOfFileNames = new ArrayList<String>();


    @FXML
    private TextArea fileNames;


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


    /**
     * Read Content of File
     * @param filePath : Path of File
     * @return : returns a contentBuilder
     */
    private static String readLineByLineJava8(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }
}

