package fi.jyu.ohj2.ipetresx.todo.controller;

import fi.jyu.ohj2.ipetresx.todo.model.Priority;
import fi.jyu.ohj2.ipetresx.todo.model.Task;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class TaskEditController implements Initializable {
    @FXML
    private Button cancelButton, saveButton;

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private ComboBox<Priority> priorityCombo;

    private Task editedTask;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        priorityCombo.setItems(FXCollections.observableArrayList(Priority.values()));
        cancelButton.setOnAction(event -> close());
        saveButton.setOnAction(event ->{
            if(validate()){
                editedTask.setTitle(titleField.getText());
                editedTask.setDescription(descriptionField.getText());
                editedTask.setPriority(priorityCombo.getValue());
                close();
            }
        });
    }

    private boolean validate(){
        titleField.setStyle("");
        titleField.setPromptText("");
        String title = titleField.getText();
        if(title == null || title.isBlank()){
            titleField.setStyle(
                    "-fx-border-color: red; " + "-fx-background-color:#fdf2f2;"

            );
            titleField.clear();
            titleField.setPromptText("Title is missing!");
            return false;
        }
        return true;
    }

    public void setTask(Task task){
        editedTask = task;
        titleField.setText(editedTask.getTitle());
        descriptionField.setText(editedTask.getDescription());
        priorityCombo.setValue(editedTask.getPriority());
    }

    private void close(){
        Scene scene = titleField.getScene();
        Stage window = (Stage) scene.getWindow();
        window.close();
    }
}
