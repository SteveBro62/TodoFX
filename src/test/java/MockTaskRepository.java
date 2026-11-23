import fi.jyu.ohj2.ipetresx.todo.model.*;
import fi.jyu.ohj2.ipetresx.todo.persistance.*;
import java.util.*;

public class MockTaskRepository implements TaskRepository {
    private List<Task> savedTasks = new ArrayList<>();

    @Override
    public void save(List<Task> tasks){
        savedTasks.clear();
        tasks.forEach(task -> {
            Task copy = new Task();
            copy.setTitle(task.getTitle());
            copy.setIsDone(task.getIsDone());
            copy.setPriority(task.getPriority());
            copy.setDescription(task.getDescription());
            savedTasks.add(copy);
        });
    }

    @Override
    public List<Task> load(){
        return savedTasks;
    }
}
