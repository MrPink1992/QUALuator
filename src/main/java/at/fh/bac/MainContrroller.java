package at.fh.bac;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;

public class MainContrroller {

   private File selectedFile;
   public String fileContent;

   private SceneController sceneController = new SceneController();

    @FXML
    private void navigateToMenu(ActionEvent event) throws Exception {
      sceneController.changeScene("menu.fxml", event);

    }

    @FXML
    private void filePicker(ActionEvent event) throws Exception{
        FileChooser fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(getStage(event));

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Please Confirm Upload");
        alert.setContentText("Are you sure to upload " + selectedFile.getName() + " ?");

        alert.showAndWait();

        sceneController.changeScene("preview.fxml", event);
        PreviewController previewController = new PreviewController();
        previewController.initialize();


    }

   private void processFile(File file){
        return;
   }

    private Stage getStage(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        return window;
    }

}