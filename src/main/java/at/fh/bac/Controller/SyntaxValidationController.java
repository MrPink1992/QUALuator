package at.fh.bac.Controller;

import at.fh.bac.ErrorHandler.SyntaxErrorHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import javafx.scene.control.Tooltip;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
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


public class SyntaxValidationController implements Initializable {

    //TODO for each file, generate a new file to put validation errors in it
    //TODO catch fucking exceptions and write them to file

    private ClassLoader cl = getClass().getClassLoader();
    private HashMap<String, String> xsdFileList = new HashMap<>();
    private SceneController sceneController = new SceneController();
    private List<File> filesToValidate;
    private int errorCount = 0;
    private HashMap<File, List<Exception>> errorFileMap;
    private List<Exception> errorList = new ArrayList<>();
    @FXML
    private Button nextButton = new Button();
    @FXML
    private Button goBackBtn = new Button();

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
        nextButton.setTooltip(new Tooltip("Go to semantic constraint definition"));
        goBackBtn.setTooltip(new Tooltip("Cancel test"));
        goBackBtn.setText("Cancel");
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

            URL schemaPath = new URL(cl.getResource("XSD/schema.xsd").toString());
            //URL schemaFile = new URL(cl.getResource("XSD/schema.xsd"));

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(schemaPath);

            validationTxtArea.appendText("Starting Validation...\n");

            for (File file : filesToValidate) {
                validationTxtArea.appendText("\n----------------------------------------------------------------------------------\n");
                errorCount = 0;
                errorList = new ArrayList<>();
                validateXml(schema, file.getPath());
                errorFileMap.put(file, errorList);

                //store errorFileMap in JSON object
                selectedFilesList.getTestJSON().put("errorFileMap", errorFileMap);
            }
            nextButton.setDisable(false);
            System.out.println(selectedFilesList.getTestJSON());

            createJson();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void fetchXMLSchema() throws Exception {

        for (String filename : xsdFileList.keySet()) {
            URL url = new URL(xsdFileList.get(filename));

            if (cl.getResource("XSD/" + filename) == null) {
                validationTxtArea.appendText("Fetching latest XSD schema ...\n");
                ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                FileOutputStream fos = new FileOutputStream("src/main/resources/XSD/" + filename);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            }


        }

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

            // preparing the XSD file as a SAX source
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

    private void createJson(){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", selectedFilesList.getDateOfTest());
        jsonObject.put("files", selectedFilesList.getFileList());
        //jsonObject.put("filesErrors", selectedFilesList.getErrorFileMap());
        jsonObject.put("constraints", "constraints");


        for (File file : selectedFilesList.getErrorFileMap().keySet()){
            List<String> errors = new ArrayList<>();
            try {
                //errors.add(selectedFilesList.getErrorFileMap().get(file).toString());
                for (Exception e : selectedFilesList.getErrorFileMap().get(file)){
                    errors.add(e.getMessage());
                }

            }catch(Exception e ){
                e.printStackTrace();
                errors.add("No Errors in this file!");
            }
            JSONObject errorFileList = new JSONObject();
            errorFileList.put(file.getName(), errors);
            jsonObject.put("fileErrorList", errorFileList);
        }

        System.out.println("my JSON object: " + jsonObject);

    }


    private void addException(Exception exception) {
        errorCount++;
        errorList.add(exception);
        validationTxtArea.appendText(exception.toString() + "\n");
        //exception.printStackTrace();
    }

    @FXML
    private void goBack(ActionEvent event) throws Exception {

        sceneController.changeScene("fileUpload.fxml", event);
    }

    @FXML
    private void next(ActionEvent event) throws Exception {
        sceneController.changeScene("semanticsForm.fxml", event);
    }


}





