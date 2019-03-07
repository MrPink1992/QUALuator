package at.fh.bac.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ResourceBundle;


import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;

import static at.fh.bac.Controller.MainController.selectedFilesList;


public class XMLController implements Initializable {

    private ClassLoader cl = getClass().getClassLoader();
    private File schemaFile;

    @FXML
    TextArea validationTxtArea;

    @FXML
    ProgressIndicator progressIndicator = new ProgressIndicator();


    /**
     * When FXML is loaded, this function checks if the schema.xsd
     * exists and downloads it if it not exists
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        validationTxtArea.setEditable(false);

        try {
            try {
                System.out.println(schemaFile);
                schemaFile = new File(cl.getResource("XML/schema.xsd").getFile());
                progressIndicator.setProgress(0.25);
                validationTxtArea.appendText("Schema already exists <--- this is good \n");
            } catch (NullPointerException np) {
                System.out.println(schemaFile);
                fetchXMLSchema();
                validationTxtArea.appendText("Successfully fetched XML Schema !\n");
                progressIndicator.setProgress(0.25);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    private void fetchXMLSchema() throws Exception {
        validationTxtArea.appendText("Fetching latest XML schema ...\n");
        URL website = new URL("https://www.omg.org/spec/BPMN/20100501/BPMN20.xsd");
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream("src/main/resources/XML/schema.xsd");
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }


    /*
    private void validateFiles(){
//TODO
        for (File file : selectedFilesList.getFileList()){
            Bpmn.readModelFromFile(file);
        }

    }
    */

}
