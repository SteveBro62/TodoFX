package fi.jyu.ohj2.ipetresx.todo.model;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.util.List;

public class TaskCollection {
    private Path path = Path.of("tasks.json");;
    private ObservableList<Task> tasks
            = FXCollections.observableArrayList(task -> new Observable[] {task.isDoneProperty()});
    private ObjectMapper mapper = new ObjectMapper();

    public TaskCollection(){
        load();
        tasks.addListener((ListChangeListener<Task>) change -> {
            save();
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

    public void addTask(String name){
        if(name == null  || name.isEmpty())
            return;
        tasks.add(new Task(name.trim(), false));
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
