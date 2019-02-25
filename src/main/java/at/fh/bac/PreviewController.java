package at.fh.bac;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

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
        sceneController.changeScene("main.fxml", event);
    }



}
