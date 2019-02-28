package at.fh.bac;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class PreviewController {

    private SceneController sceneController = new SceneController();

    @FXML public TextArea fileNames;

    private List<File> selectedFiles;


    public void initialize(ActionEvent event) {

        try {
            for (File file : Main.files){
                fileNames.appendText(file.toString() + "\n");
                System.out.print(file.toString());
            }
        }catch(NullPointerException nullPointerException){
            System.out.println("no files specified");
            nullPointerException.printStackTrace();
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
