package fi.jyu.ohj2.ipetresx.todo.persistance;
import fi.jyu.ohj2.ipetresx.todo.model.Task;
import java.util.*;

public interface TaskRepository {
    List<Task> load() throws RepositoryException;
    void save(List<Task> tasks) throws RepositoryException;
}
