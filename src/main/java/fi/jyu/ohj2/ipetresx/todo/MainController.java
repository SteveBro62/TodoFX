package fi.jyu.ohj2.ipetresx.todo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import tools.jackson.core.*;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

public class MainController implements Initializable {
    @FXML
    private Button addNewTaskButton;

    @FXML
    private TextField newTaskName;

    @FXML
    private Label taskList;

    @FXML
    private VBox pendingTasks, completedTasks;

    private Path path;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        path = Path.of("tasks.json");
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Task> lst = mapper.readValue(path.toFile(), new TypeReference<List<Task>>(){});
            lst.forEach(e -> addCheckbox(new CheckBox(e.name()), e.done() ? completedTasks : pendingTasks));
        }
        catch(JacksonException _) {
        }
        newTaskName.setText("");
        addNewTaskButton.setOnAction(event -> {
            addTask();
        });
        newTaskName.setOnAction(event ->{
            addTask();
        });
    }

    private List<Task> load(VBox box){
        return box.getChildren().stream()
                .filter(e -> e instanceof CheckBox c)
                .map(e -> (CheckBox) e)
                .map(e -> new Task(e.getText(), e.isSelected()))
                .toList();
    }

    private void save(){
        ObjectMapper mapper = new ObjectMapper();
        List<Task> lst = new ArrayList<>();
        lst.addAll(load(pendingTasks));
        lst.addAll(load(completedTasks));
        mapper.writeValue(path.toFile(), lst);
    }

    private void addCheckbox(CheckBox box, VBox vbox){
        if(vbox == completedTasks)
            box.setSelected(true);
        vbox.getChildren().add(box);
        save();
        box.setOnAction(add -> {
            if(box.getParent() == completedTasks){
                completedTasks.getChildren().remove(box);
                pendingTasks.getChildren().add(box);
            }
            else{
                pendingTasks.getChildren().remove(box);
                completedTasks.getChildren().add(box);
            }
            save();
        });
    }

    private void addTask(){
        String text = newTaskName.getText();
        newTaskName.requestFocus();
        if(text == null || text.isBlank())
            return;
        CheckBox box = new CheckBox(text.trim());
        addCheckbox(box, pendingTasks);
        newTaskName.setText("");
    }
}
