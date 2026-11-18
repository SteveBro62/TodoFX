package fi.jyu.ohj2.ipetresx.todo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newTaskName.setText("Hello");
        addNewTaskButton.setOnAction(event -> {
            String text = newTaskName.getText();
            taskList.setText(taskList.getText() + text + "\n");
            newTaskName.setText("");
        });
    }
}
