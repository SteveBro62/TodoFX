package fi.jyu.ohj2.ipetresx.todo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button addNewTaskButton;

    @FXML
    private TextField newTaskName;

    @FXML
    private Label taskList;

    @FXML
    private VBox pendingTasks, completedTasks;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newTaskName.setText("");
        addNewTaskButton.setOnAction(event -> {
            addTask();
        });
        newTaskName.setOnAction(event ->{
            addTask();
        });
    }

    private void addTask(){
        String text = newTaskName.getText();
        newTaskName.requestFocus();
        if(text == null || text.isBlank())
            return;
        CheckBox box = new CheckBox(text.trim());
        pendingTasks.getChildren().add(box);
        box.setOnAction(add -> {
            if(box.getParent() == completedTasks){
                completedTasks.getChildren().remove(box);
                pendingTasks.getChildren().add(box);
            }
            else{
                pendingTasks.getChildren().remove(box);
                completedTasks.getChildren().add(box);
            }
        });
        newTaskName.setText("");
    }
}
