package at.fh.bac.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class XMLController implements Initializable {

    private ClassLoader cl = getClass().getClassLoader();
    private File schemaFile;

    @FXML
    TextArea validationTxtArea;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        validationTxtArea.setEditable(false);

        try {
            try {
                System.out.println(schemaFile);
                schemaFile = new File(cl.getResource("XML/schema.xsd").getFile());
                validationTxtArea.appendText("Schema already exists <--- this is good \n");
            } catch (NullPointerException np) {
                System.out.println(schemaFile);
                fetchXMLSchema();
                validationTxtArea.appendText("Successfully fetched XML Schema !\n");
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


        System.out.println("Successfully fetched XML Schema !");

    }

}
