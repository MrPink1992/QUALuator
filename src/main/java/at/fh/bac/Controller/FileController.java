package at.fh.bac.Controller;

import at.fh.bac.Model.FileListModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileController {

    private SceneController sceneController = new SceneController();

    public static FileListModel selectedFilesList = new FileListModel();

    private List<File> selectedFiles = new ArrayList<>();


    /**
     * Open file-menu to select files and store them in the fileListModel
     * //TODO Restrict file upload to .xml/.bpmn
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

        // save selected files to the FileListModel
        selectedFilesList.setFileList(selectedFiles);


        sceneController.changeScene("preview.fxml", event);
    }


    /**
     * Open file menu tu upload a directory and store each file from the dir in the fileListModel
     * SOURCE: https://www.mkyong.com/java/java-how-to-list-all-files-in-a-directory/
     * @param event : ActionEvent needed to get the stage
     * @throws Exception : Exception Handling
     */
    @FXML
    private void dirPicker(ActionEvent event) throws Exception{
        DirectoryChooser directoryChooser = new DirectoryChooser();

        try (Stream<Path> walk = Files.walk(Paths.get((directoryChooser.showDialog(SceneController.getStage(event))).toString()))) {

            List<String> result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());

            // create new File for each XML-File in directory
            for (String path : result){

                File file = new File(path);

                String fileExtension = FilenameUtils.getExtension(file.getName());


                if (!fileExtension.equals("bpmn")){
                    continue;}
                else{selectedFiles.add(file);}

            }



            selectedFilesList.setFileList(selectedFiles);
            sceneController.changeScene("preview.fxml", event);

        } catch (Exception e) {
            //e.printStackTrace();
            sceneController.changeScene("main.fxml", event);
        }


    }




    @FXML
    private void navigateToMenu(ActionEvent event) throws Exception{
        sceneController.changeScene("menu.fxml", event);
    }

}