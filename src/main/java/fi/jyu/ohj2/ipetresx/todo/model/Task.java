package fi.jyu.ohj2.ipetresx.todo.model;

import javafx.beans.property.*;

public class Task{
    private BooleanProperty isDone = new SimpleBooleanProperty(false);
    private StringProperty name = new SimpleStringProperty("");

    public Task(){}

    public Task(String name, boolean isDone){
        setName(name);
        setIsDone(isDone);
    }

    public boolean getIsDone(){
        return isDone.get();
    }

    public String getName(){
        return name.get();
    }

    public void setName(String name){
        this.name.set(name);
    }

    public void setIsDone(boolean isDone){
        this.isDone.set(isDone);
    }

    public BooleanProperty isDoneProperty(){
        return isDone;
    }

    public StringProperty nameProperty(){
        return name;
    }
}