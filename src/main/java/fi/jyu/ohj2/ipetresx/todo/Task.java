package fi.jyu.ohj2.ipetresx.todo;

public class Task{
    private boolean isDone;
    private String name;

    public Task(){}

    public Task(String name, boolean isDone){
        this.name = name;
        this.isDone = isDone;
    }

    public boolean getIsDone(){
        return isDone;
    }

    public String getName(){
        return name;
    }
}