package fi.jyu.ohj2.ipetresx.todo.model;

import javafx.beans.property.*;

public class Task{
    private BooleanProperty isDone = new SimpleBooleanProperty(false);
    private StringProperty title = new SimpleStringProperty("");
    private StringProperty description = new SimpleStringProperty("");
    private ObjectProperty<Priority> priority = new SimpleObjectProperty<>() {};

    public Task(){}

    public Task(String title, String description, Priority priority, boolean isDone){
        setTitle(title);
        setIsDone(isDone);
        setDescription(description);
        setPriority(priority);
    }

    public boolean getIsDone(){
        return isDone.get();
    }

    public String getDescription(){
        return description.get();
    }

    public Priority getPriority(){
        return priority.get();
    }

    public String getTitle(){
        return title.get();
    }

    public void setTitle(String title){
        this.title.set(title);
    }

    public void setIsDone(boolean isDone){
        this.isDone.set(isDone);
    }

    public void setPriority(Priority priority){
        this.priority.set(priority);
    }

    public void setDescription(String description){
        this.description.set(description);
    }

    public BooleanProperty isDoneProperty(){
        return isDone;
    }

    public StringProperty titleProperty(){
        return title;
    }

    public StringProperty descriptionProperty(){
        return description;
    }

    public ObjectProperty<Priority> priorityProperty(){
        return priority;
    }
}