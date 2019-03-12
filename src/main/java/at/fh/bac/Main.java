package at.fh.bac;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
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

        URI jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI();
        System.out.println(jarPath);

        File resultsFolder = new File(jarPath + "/TestResults/");
        System.out.println(resultsFolder);

        boolean created =  resultsFolder.mkdir();

        if(created)
            System.out.println("Folder was created !");
        else
            System.out.println("Unable to create folder");


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