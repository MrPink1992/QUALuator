package at.fh.bac.Controller;

import at.fh.bac.Model.FileListModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.commons.io.FilenameUtils;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.scene.control.Tooltip;

public class FileController implements Initializable {

    private SceneController sceneController = new SceneController();
    public static FileListModel selectedFilesList = new FileListModel();
    private List<File> selectedFiles = new ArrayList<>();

    @FXML
    private Button filePickerBtn = new Button();
    @FXML
    private Button directoryPickerBtn = new Button();
    @FXML
    private Button backToMenuBtn = new Button();

    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date date = new Date();


    public void initialize(URL url, ResourceBundle rb){

        filePickerBtn.setTooltip(new Tooltip("Test a single file"));
        directoryPickerBtn.setTooltip(new Tooltip("Test multiple files in single directory"));
        backToMenuBtn.setTooltip(new Tooltip("Back to menu"));



    }

    /**
     * Open file-menu to select files and store them in the fileListModel
     *
     * //TODO Persist filenames
     * @param event : ActionEvent needed to get the stage
     * @throws Exception : handles exceptions
     */

    @FXML
    private void filePicker(ActionEvent event) throws Exception {

            FileChooser fileChooser = new FileChooser();
            // ExtensionFilter to only accept .xml files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("BPMN files (*.bpmn)", "*.bpmn");
            fileChooser.getExtensionFilters().add(extFilter);


            // open the File-Dialog
            selectedFiles = fileChooser.showOpenMultipleDialog(SceneController.getStage(event));


            if (selectedFiles == null){
                Alert alert = new Alert(Alert.AlertType.WARNING, "You have to upload a file!");
                alert.showAndWait();
                sceneController.changeScene("fileUpload.fxml", event);
            }else {

                // save selected files to the FileListModel
                selectedFilesList.setDateOfTest(dateFormat.format(date));
                selectedFilesList.setFileList(selectedFiles);
                System.out.println(selectedFilesList.getDateOfTest());

                sceneController.changeScene("preview.fxml", event);
            }


    }


    /**
     * Open file menu tu upload a directory and store each file from the dir in the fileListModel
     * SOURCE: https://www.mkyong.com/java/java-how-to-list-all-files-in-a-directory/
     * @param event : ActionEvent needed to get the stage
     * @throws Exception : Exception Handling
     */

    @FXML
    private  void dirPicker(ActionEvent event) throws Exception{

            DirectoryChooser directoryChooser = new DirectoryChooser();

            try (Stream<Path> walk = Files.walk(Paths.get((directoryChooser.showDialog(SceneController.getStage(event))).toString()))) {

                List<String> result = walk.filter(Files::isRegularFile)
                        .map(x -> x.toString()).collect(Collectors.toList());

                // create new File for each XSD-File in directory
                for (String path : result) {

                    File file = new File(path);

                    String fileExtension = FilenameUtils.getExtension(file.getName());


                    if (!fileExtension.equals("bpmn")) {
                        continue;
                    } else {
                        selectedFiles.add(file);
                    }

                }

                selectedFilesList.setDateOfTest(dateFormat.format(date));
                selectedFilesList.setFileList(selectedFiles);
                sceneController.changeScene("preview.fxml", event);

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Cannot test without file!");
                alert.showAndWait();
                sceneController.changeScene("fileUpload.fxml", event);
            }
        }


    @FXML
    private void navigateToMenu(ActionEvent event) throws Exception{
        sceneController.changeScene("menu.fxml", event);
    }

}