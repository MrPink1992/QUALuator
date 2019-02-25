package at.fh.bac;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import javax.sound.midi.SysexMessage;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class PreviewController {


    private String content = "Test String";

    private SceneController sceneController = new SceneController();


    @FXML public TextArea fileContent;


    public void initialize() {
        try {
            System.out.println(content);
            fileContent.setText(content);

        }catch(NullPointerException nullPointerException){
            System.out.println("SOmething went wrong: ");
            nullPointerException.printStackTrace();
        }
        }



    @FXML
    private void goBack(ActionEvent event) throws Exception{
        sceneController.sceneHandler("main.fxml", event);
    }



}
