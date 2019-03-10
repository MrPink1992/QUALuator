package at.fh.bac.Controller;

import at.fh.bac.ErrorHandler.SyntaxErrorHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


import javax.xml.XMLConstants;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import static at.fh.bac.Controller.FileController.selectedFilesList;


public class XMLController implements Initializable {

    //TODO for each file, generate a new file to put validation errors in it
    //TODO catch fucking exceptions and write them to file

    private ClassLoader cl = getClass().getClassLoader();
    private HashMap<String, String> xsdFileList = new HashMap<>();
    private SyntaxErrorHandler syntaxErrorHandler = new SyntaxErrorHandler();
    private SceneController sceneController = new SceneController();
    private List<File> filesToValidate;
    private int errorCount = 0;
    private HashMap<File, List<Exception>> errorFileMap;
    private List<Exception> errorList = new ArrayList<>();
    @FXML private Button nextButton = new Button();

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
        nextButton.setDisable(true);
        validationTxtArea.setEditable(false);

        xsdFileList.put("schema.xsd", "https://www.omg.org/spec/BPMN/20100501/BPMN20.xsd");
        xsdFileList.put("Semantic.xsd", "https://www.omg.org/spec/BPMN/20100501/Semantic.xsd");
        xsdFileList.put("BPMNDI.xsd", "https://www.omg.org/spec/BPMN/20100501/BPMNDI.xsd");
        xsdFileList.put("DC.xsd", "https://www.omg.org/spec/BPMN/20100501/DC.xsd");
        xsdFileList.put("DI.xsd", "https://www.omg.org/spec/BPMN/20100501/DI.xsd");

        try {
            filesToValidate = selectedFilesList.getFileList();
            errorFileMap = new HashMap<>();

            fetchXMLSchema();
            //fileValidator();

            URL schemaPath = new URL(cl.getResource("XML/schema.xsd").toString());
            //URL schemaFile = new URL(cl.getResource("XML/schema.xsd"));

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(schemaPath);

            validationTxtArea.appendText("Starting Validation...\n");

            for (File file : filesToValidate) {
                validationTxtArea.appendText("\n----------------------------------------------------------------------------------\n");
                errorCount = 0;
                errorList = new ArrayList<>();
                validateXml(schema, file.getPath());
                errorFileMap.put(file, errorList);
                //System.out.println(errorCount + " errors have been found in " + file.getName());
                //System.out.println(errorFileMap);
            }
            nextButton.setDisable(false);

            /*
            errorFileMap.forEach((key, value) -> {
                System.out.println("File : " + key + " Exceptions : " + value);
            });
            */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Read Content of File
     *
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



    private void fetchXMLSchema() throws Exception {

        for (String filename : xsdFileList.keySet()) {
            URL url = new URL(xsdFileList.get(filename));

            if (cl.getResource("XML/" + filename) == null) {
                validationTxtArea.appendText("Fetching latest XML schema ...\n");
                ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                FileOutputStream fos = new FileOutputStream("src/main/resources/XML/" + filename);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            }


        }

    }

    @FXML
    private void goBack(ActionEvent event) throws Exception {

        sceneController.changeScene("main.fxml", event);
    }

    @FXML
    private void next(ActionEvent event) throws Exception{
        sceneController.changeScene("semanticsForm.fxml", event);
    }


    private void validateXml(Schema schema, String xmlName) {
        try {

            validationTxtArea.appendText("Validating file: " + xmlName + "/" + "\n");
            // creating a Validator instance
            Validator validator = schema.newValidator();

            ErrorHandler errorHandler = new ErrorHandler() {
                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    addException(exception);
                    //exception.printStackTrace();
                }

                @Override
                public void error(SAXParseException exception) throws SAXException {
                    addException(exception);
                    //exception.printStackTrace();
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    addException(exception);
                    //exception.printStackTrace();

                }
            };

            // setting my own error handler
            validator.setErrorHandler(errorHandler);

            // preparing the XML file as a SAX source
            SAXSource source = new SAXSource(
                    new InputSource(new java.io.FileInputStream(xmlName)));

            // validating the SAX source against the schema
            validator.validate(source);
            validationTxtArea.appendText("Finished with: " + errorCount + " errors.");

        } catch (Exception e) {
            // catching all validation exceptions
            System.out.println();
            System.out.println(e.toString());
            validationTxtArea.appendText(e.toString());
            //e.printStackTrace();
        }
    }

    private void addException(Exception exception){
        errorCount++;
        errorList.add(exception);
        System.out.println(exception.toString());
        validationTxtArea.appendText(exception.toString() + "\n");
        //exception.printStackTrace();
    }

}





