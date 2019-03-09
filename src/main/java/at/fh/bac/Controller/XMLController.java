package at.fh.bac.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.xml.impl.validation.ValidationResultsCollectorImpl;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import static at.fh.bac.Controller.FileController.selectedFilesList;


public class XMLController implements Initializable {

    private ClassLoader cl = getClass().getClassLoader();


    private HashMap<String, String> xsdFileList = new HashMap<>();

    private BpmnModelInstance modelInstance;

    private Validator validator;


    @FXML
    TextArea validationTxtArea;

    @FXML
    ProgressIndicator progressIndicator = new ProgressIndicator();


    /**
     * When FXML is loaded, this function checks if the schema.xsd
     * exists and downloads it if it not exists
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        validationTxtArea.setEditable(false);

        xsdFileList.put("schema.xsd", "https://www.omg.org/spec/BPMN/20100501/BPMN20.xsd");
        xsdFileList.put("Semantic.xsd", "https://www.omg.org/spec/BPMN/20100501/Semantic.xsd");
        xsdFileList.put("BPMNDI.xsd", "https://www.omg.org/spec/BPMN/20100501/BPMNDI.xsd");
        xsdFileList.put("DC.xsd", "https://www.omg.org/spec/BPMN/20100501/DC.xsd");
        xsdFileList.put("DI.xsd", "https://www.omg.org/spec/BPMN/20100501/DI.xsd");


        try {
            try {
                validationTxtArea.appendText("Checking XML Schema file ...\n");
                File schemaFile = new File(cl.getResource("XML/schema.xsd").getFile());
                File semanticsFile = new File(cl.getResource("XML/Semantic.xsd").getFile());
                File bpmndiFile = new File(cl.getResource("XML/BPMNDI.xsd").getFile());
                File dcFile = new File(cl.getResource("XML/DC.xsd").getFile());
                File diFile = new File(cl.getResource("XML/DI.xsd").getFile());
                progressIndicator.setProgress(0.25);
                validationTxtArea.appendText("Schema already exists <--- this is good \n");
                validationTxtArea.appendText("Validating XML Files... \n");
                validateFiles();

            } catch (NullPointerException np) {
                fetchXMLSchema();
                validationTxtArea.appendText("Successfully fetched XML Schema !\n");
                progressIndicator.setProgress(0.25);
                validationTxtArea.appendText("Validating XML Files... \n");
                validateFiles();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void fetchXMLSchema() throws Exception {
        validationTxtArea.appendText("Fetching latest XML schema ...\n");

        for(String filename : xsdFileList.keySet()){
            URL url = new URL(xsdFileList.get(filename));
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream("src/main/resources/XML/" + filename);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);


        }

    }


    private void validateFiles() {

        try {
            for (File file : selectedFilesList.getFileList()) {
                modelInstance = Bpmn.readModelFromFile(file);
                Bpmn.validateModel(modelInstance);
                xsdValidator(file);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Validation complete !");
        validationTxtArea.appendText("Validation complete!\n");
        progressIndicator.setProgress(0.5);


    }

    private void xsdValidator(File file) {

        String xml = readLineByLineJava8(file.getPath());

        try {
            System.out.println("----------------------------------------------------------------------------------");
            validationTxtArea.appendText("Validating file: " + file.getName() + "\n");
            System.out.println("Validating file : " + file.getName() + "  against XSD Schema...");
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(cl.getResource("XML/schema.xsd"));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xml)));
            System.out.println("Validation is successful");
        } catch (IOException e) {
            // handle exception while reading source
        } catch (SAXException e) {
            validationTxtArea.appendText("SYNTAX ERROR in file " + file.getName() + ": " + e.getMessage() + "\n");
            System.out.println("Error when validate XML against XSD Schema\n");
            System.out.println("Message: " + e.getMessage() + "\n");
            System.out.println("----------------------------------------------------------------------------------");
        }
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


