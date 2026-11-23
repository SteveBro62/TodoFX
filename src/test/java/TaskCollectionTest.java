import fi.jyu.ohj2.ipetresx.todo.*;
import static org.junit.jupiter.api.Assertions.*;

import fi.jyu.ohj2.ipetresx.todo.model.Priority;
import fi.jyu.ohj2.ipetresx.todo.model.Task;
import fi.jyu.ohj2.ipetresx.todo.model.TaskCollection;
import fi.jyu.ohj2.ipetresx.todo.persistance.*;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class TaskCollectionTest {

    @Test
    void taskIsAdded(){
        TaskCollection taskCollection = new TaskCollection(new MockTaskRepository());
        taskCollection.addTask("Java coding");
        assertEquals(1, taskCollection.getTasks().size(), "Task list size should be 1");
        assertEquals("Java coding", taskCollection.getTasks().getFirst().getTitle(), "Title should be the same");
        assertFalse(taskCollection.getTasks().getFirst().getIsDone(), "Tasks should initially be not done");
    }

    @Test
    void edgeSpacesAreRemoved(){
        TaskCollection taskCollection = new TaskCollection(new MockTaskRepository());
        taskCollection.addTask("  I am groot   ");
        assertEquals(1, taskCollection.getTasks().size(), "Task list size should be 1");
        assertEquals("I am groot", taskCollection.getTasks().getFirst().getTitle(), "Extra whitespace should be removed");
    }

    @Test
    void taskIsRemoved(){
        TaskCollection taskCollection = new TaskCollection(new MockTaskRepository());
        taskCollection.addTask("I am Iron Man");
        taskCollection.removeTask(taskCollection.getTasks().getFirst());
        assertEquals(0, taskCollection.getTasks().size(), "Task should be removed");
    }

    @Test
    void emptyTaskNotAdded(){
        TaskCollection taskCollection = new TaskCollection(new MockTaskRepository());
        taskCollection.addTask("   ");
        taskCollection.addTask(null);
        assertEquals(0, taskCollection.getTasks().size(), "Empty task shouldn't be added");
    }

    @Test
    void identicalTasksAreStored(){
        TaskCollection taskCollection = new TaskCollection(new MockTaskRepository());
        taskCollection.addTask("There are two of us");
        taskCollection.addTask("There are two of us");
        assertEquals(2, taskCollection.getTasks().size(), "Dupes should be kept");
        assertEquals(taskCollection.getTasks().getFirst().getTitle(), taskCollection.getTasks().getLast().getTitle(), "Dupes should be matching");
    }

    @Test
    void dupesDoNotShareBehaviour(){
        TaskCollection taskCollection = new TaskCollection(new MockTaskRepository());
        taskCollection.addTask("There are two of us");
        taskCollection.addTask("There are two of us");
        taskCollection.getTasks().getFirst().setIsDone(true);
        assertTrue(taskCollection.getTasks().getFirst().getIsDone(), "Task should be editable");
        assertFalse(taskCollection.getTasks().getLast().getIsDone(), "Dupe behaviour should be independent");
    }

    @Test
    void orderShouldBeKept(){
        TaskCollection taskCollection = new TaskCollection(new MockTaskRepository());
        taskCollection.addTask("I am the first one");
        taskCollection.addTask("I am the second one");
        assertEquals("I am the first one", taskCollection.getTasks().getFirst().getTitle(), "Tasks should be in add order");
        assertEquals("I am the second one", taskCollection.getTasks().getLast().getTitle(), "Tasks should be in add order");
    }

    @Test
    void tasksAreSavedAfterUpdatingTaskCollection() throws InterruptedException {
        // The TaskCollection saver is intentionally delaying saving, the reason this test fails.
        // Either temporarily remove saver, or add a way to wait for it to finish (hard to implement)
        MockTaskRepository repo = new MockTaskRepository();
        TaskCollection taskCollection = new TaskCollection(repo);
        taskCollection.addTask("whatever");
        assertEquals(1, repo.load().size(), "Task should be saved");
        taskCollection.addTask("Legendary");
        assertEquals(2, repo.load().size(), "Task should be saved");
        taskCollection.removeTask(taskCollection.getTasks().getLast());
        assertEquals(1, repo.load().size(), "Task should be saved");
    }

    @Test
    void emptyTaskNotSaved(){
        MockTaskRepository repo = new MockTaskRepository();
        TaskCollection taskCollection = new TaskCollection(repo);
        taskCollection.addTask(null);
        taskCollection.addTask("    ");
        assertEquals(0, repo.load().size(), "Empty task shouldn't be saved");
    }

    @Test
    void taskModificationsAreSaved() throws InterruptedException {
        // The TaskCollection saver is intentionally delaying saving, the reason this test fails.
        // Either temporarily remove saver, or add a way to wait for it to finish (hard to implement)
        MockTaskRepository repo = new MockTaskRepository();
        TaskCollection taskCollection = new TaskCollection(repo);
        taskCollection.addTask("whatever");
        Task task = taskCollection.getTasks().getFirst();
        task.setDescription("Amazing");
        TimeUnit.MILLISECONDS.sleep(500);
        assertEquals("Amazing", repo.load().getFirst().getDescription(), "Description should be saved");
        task.setIsDone(true);
        TimeUnit.MILLISECONDS.sleep(500);
        assertTrue(repo.load().getFirst().getIsDone(), "Completeness should be saved");
        task.setPriority(Priority.HIGH);
        TimeUnit.MILLISECONDS.sleep(500);
        assertEquals(Priority.HIGH, repo.load().getFirst().getPriority(), "Priority should be saved");
        task.setTitle("Legendary");
        TimeUnit.MILLISECONDS.sleep(500);
        assertEquals("Legendary", repo.load().getFirst().getTitle(), "Title should be saved");
    }
}
