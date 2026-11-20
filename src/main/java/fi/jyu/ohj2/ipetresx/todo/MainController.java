package fi.jyu.ohj2.ipetresx.todo;

import java.nio.file.*;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import java.net.URL;
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
    private VBox pendingTasks, completedTasks;

    private Path path;

    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tasks.addListener((ListChangeListener<Task>) change -> {
            render();
            save();
        });
        path = Path.of("tasks.json");
        try {
            tasks.addAll(load());
        }
        catch (Exception _){}
        newTaskName.setText("");
        addNewTaskButton.setOnAction(event -> {
            addTask();
        });
        newTaskName.setOnAction(event ->{
            addTask();
        });
    }

    private List<Task> load(){
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(path.toFile(), new TypeReference<List<Task>>(){});
    }

    private void save(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(path.toFile(), tasks);
    }

    private void render(){
        pendingTasks.getChildren().clear();
        completedTasks.getChildren().clear();
        tasks.forEach(this::addCheckbox);
    }

    private void addCheckbox(Task task){
        CheckBox box = createCheckbox(task);
        if(box.isSelected())
            completedTasks.getChildren().add(box);
        else
            pendingTasks.getChildren().add(box);
    }

    private CheckBox createCheckbox(Task task){
        CheckBox box = new CheckBox(task.getName());
        box.setSelected(task.getIsDone());
        box.setOnAction(check -> {
            tasks.remove(task);
            tasks.add(new Task(task.getName(), !task.getIsDone()));
        });
        return box;
    }

    private void addTask(){
        String text = newTaskName.getText();
        newTaskName.requestFocus();
        if(text == null || text.isBlank())
            return;
        tasks.add(new Task(text, false));
        newTaskName.setText("");
    }
}
