package at.fh.bac.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SemanticsFormController implements Initializable {

    @FXML
    private Button addConstraintButton;
    @FXML
    private TextField constraintNameField;

    @FXML private  ComboBox comboBox;

    private ObservableList<String> choices = FXCollections.observableArrayList();

    private ObservableList<Pair<String, String>> listOfConstraints =  FXCollections.observableArrayList();

    private String name;
    private String type;

    @FXML private ListView constraintListView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        choices.add("StartEvent");
        choices.add("Task");
        choices.add("EndEvent");
        choices.add("Gateway");

    }


    @FXML
    private void addConstraint() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add Constraint");
        dialog.setHeaderText("Please add a semantic constraint");
        dialog.setContentText("Constraint");

        // Set the button types.
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox comboBox = new ComboBox();
        comboBox.setItems(choices);

        constraintNameField = new TextField();
        constraintNameField.setPromptText("Name");


        grid.add(new Label("Type: "), 0, 1);
        grid.add(comboBox, 1, 1);
        grid.add(new Label("must be named: "), 0, 0);
        grid.add(constraintNameField, 1, 0);

        Node addButton = dialog.getDialogPane().lookupButton(addButtonType);


        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Pair<>(comboBox.getValue().toString(), constraintNameField.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(listStringPair -> {
            name = listStringPair.getValue();
            type = listStringPair.getKey();
            listOfConstraints.add(listStringPair);
            System.out.println("Type " + type + " must be named " + name);
            System.out.println(listOfConstraints);
            constraintListView.setItems(listOfConstraints);
            System.out.println(constraintListView.getItems());

            addConstraint();
        });

    }

    private void saveConstraint() {
        String name = constraintNameField.getText();
        String type = comboBox.getTypeSelector();
    }

}
