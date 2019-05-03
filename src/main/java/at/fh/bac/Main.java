package at.fh.bac;

import at.fh.bac.Model.FileListModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main extends Application {



    @SuppressWarnings("WARNING")
    @Override
    public void start(Stage primaryStage) throws Exception {

        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream("results.json");
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            FileListModel testFileListModel = (FileListModel) in.readObject();

            in.close();
            file.close();

            System.out.println("Object has been deserialized ");
            System.out.println(testFileListModel);

        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }

        Parent root = FXMLLoader.load(getClass().getResource("/FXML/menu.fxml"));
        primaryStage.setTitle("QUALuator");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setResizable(false);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
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
}