package fi.jyu.ohj2.ipetresx.todo.model;

import fi.jyu.ohj2.ipetresx.todo.persistance.RepositoryException;
import fi.jyu.ohj2.ipetresx.todo.persistance.TaskRepository;
import javafx.animation.PauseTransition;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.util.List;

public class TaskCollection {
    private final ObservableList<Task> tasks
            = FXCollections.observableArrayList(task -> new Observable[] {
                    task.isDoneProperty(),
                    task.titleProperty(),
                    task.descriptionProperty(),
                    task.priorityProperty()});
    private PauseTransition saver = new PauseTransition(Duration.millis(500));
    private TaskRepository repo;

    public TaskCollection(TaskRepository repo) {
        this.repo = repo;
        load();
        saver.setOnFinished(event -> save());
        tasks.addListener((ListChangeListener<Task>) change -> {
            saver.playFromStart();
        });
    }

    private void save(){
        try{
            repo.save(tasks);
        }
        catch (RepositoryException e){
            IO.println(e.getMessage());
        }
    }

    private void load(){
        try{
            tasks.addAll(repo.load());
        }
        catch (RepositoryException e){
            IO.println(e.getMessage());
        }
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
