package fi.jyu.ohj2.ipetresx.todo.model;

import javafx.animation.PauseTransition;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.util.List;

public class TaskCollection {
    private Path path = Path.of("tasks.json");;
    private ObservableList<Task> tasks
            = FXCollections.observableArrayList(task -> new Observable[] {
                    task.isDoneProperty(),
                    task.titleProperty(),
                    task.descriptionProperty(),
                    task.priorityProperty()});
    private ObjectMapper mapper = new ObjectMapper();
    private PauseTransition saver = new PauseTransition(Duration.millis(500));

    public TaskCollection(){
        load();
        saver.setOnFinished(event -> save());
        tasks.addListener((ListChangeListener<Task>) change -> {
            saver.playFromStart();
        });
    }

    public TaskCollection(Path path){
        this.path = path;
        load();
        saver.setOnFinished(event -> save());
        tasks.addListener((ListChangeListener<Task>) change -> {
            saver.playFromStart();
        });
    }

    private void load(){
        try {
            tasks.addAll(mapper.readValue(path.toFile(), new TypeReference<List<Task>>() {}));
        }
        catch (Exception _){} // file doesn't exist, or content is corrupted
    }

    private void save(){
        mapper.writeValue(path.toFile(), tasks);
    }

    public void addTask(String title){
        if(title == null  || title.isBlank())
            return;
        tasks.add(new Task(title.trim(), "", Priority.LOW, false));
    }

    public void removeTask(Task task){
        if(task == null)
            return;
        tasks.remove(task);
    }

    public ObservableList<Task> getTasks(){
        return tasks;
    }
}
