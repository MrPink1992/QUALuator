package at.fh.bac.Controller;

import at.fh.bac.Model.TripletModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SemanticsFormController implements Initializable {

    @FXML
    private TextField constraintNameField;

    @FXML
    private ComboBox comboBox;

    private ObservableList<String> typeList = FXCollections.observableArrayList();
    private ObservableList<String> constraintList = FXCollections.observableArrayList();
    private SceneController sceneController = new SceneController();

    private ObservableList<TripletModel<String, String, String>> listOfConstraints = FXCollections.observableArrayList();
    private ObservableList<String> listOfStringConstraints = FXCollections.observableArrayList();

    private ObservableList<String> semanticOverviewList = FXCollections.observableArrayList();


    @FXML
    private ListView constraintListView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        typeList.add("StartEvent");
        typeList.add("Task");
        typeList.add("EndEvent");
        typeList.add("Gateway");

        constraintList.add("Name");
        constraintList.add("Number of occurrences");
        //constraintList.add("");
        //constraintList.add("");
        //constraintList.add("");

    }


    @FXML
    @SuppressWarnings("unchecked")
    private void addConstraint() {
        Dialog<TripletModel<String, String, String>> dialog = new Dialog<>();
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

        ComboBox typeComboBox = new ComboBox();
        typeComboBox.setItems(typeList);

        ComboBox constraintComboBox = new ComboBox();
        constraintComboBox.setItems(constraintList);

        constraintNameField = new TextField();
        constraintNameField.setPromptText("Name");


        grid.add(new Label("Type: "), 0, 0);
        grid.add(typeComboBox, 1, 0);

        grid.add(new Label("Constraint: "), 0, 1);
        grid.add(constraintComboBox, 1, 1);

        grid.add(new Label("Value: "), 0, 2);
        grid.add(constraintNameField, 1, 2);

        Node addButton = dialog.getDialogPane().lookupButton(addButtonType);


        dialog.getDialogPane().setContent(grid);


        dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == addButtonType) {

                        try {
                            //return new Pair<>(constraintComboBox.getValue().toString(), constraintNameField.getText());
                            return new TripletModel<>(typeComboBox.getValue().toString(), constraintComboBox.getValue().toString(), constraintNameField.getText());
                        }catch(NullPointerException npe) {
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Fields must not be empty!");
                            alert.showAndWait();
                            addConstraint();
                        }
                    }
                    return null;
                });

                //Optional<Pair<String, String>> result = dialog.showAndWait();
                Optional < TripletModel < String, String, String >> result = dialog.showAndWait();


        result.ifPresent(newConstraint -> {
            String name = newConstraint.getValue();
            String type = newConstraint.getType();
            String constraint = newConstraint.getConstraint();
            listOfConstraints.add(newConstraint);
            listOfStringConstraints.add(formatForListView(newConstraint));
            constraintListView.setItems(listOfStringConstraints);
            addConstraint();
        });



}

    private String formatForListView(TripletModel entry) {
        String type = entry.getType().toString().toUpperCase();
        String constraint = entry.getConstraint().toString().toUpperCase();
        String value = entry.getValue().toString().toUpperCase();

        return ("Type " + type + " has constraint " + constraint + " with value " + value);
    }

    @FXML
    private void goBack(ActionEvent event) throws Exception {
        sceneController.changeScene("fileUpload.fxml", event);
    }

    @FXML
    private void next(ActionEvent event) throws Exception {
        //fillOverviewList();
        sceneController.changeScene("semanticValidation.fxml", event);
    }

}
