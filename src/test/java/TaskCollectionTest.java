import fi.jyu.ohj2.ipetresx.todo.*;
import static org.junit.jupiter.api.Assertions.*;
import fi.jyu.ohj2.ipetresx.todo.model.Task;
import fi.jyu.ohj2.ipetresx.todo.model.TaskCollection;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

public class TaskCollectionTest {
    @Test
    void taskIsAdded(){
        TaskCollection taskCollection = new TaskCollection(Path.of("testtasks.json"));
        taskCollection.addTask("Java coding");
        assertEquals(1, taskCollection.getTasks().size(), "Task list size should be 1");
        assertEquals("Java coding", taskCollection.getTasks().getFirst().getTitle(), "Title should be the same");
        assertFalse(taskCollection.getTasks().getFirst().getIsDone(), "Tasks should initially be not done");
    }

    @Test
    void edgeSpacesAreRemoved(){
        TaskCollection taskCollection = new TaskCollection(Path.of("testtasks.json"));
        taskCollection.addTask("  I am groot   ");
        assertEquals(1, taskCollection.getTasks().size(), "Task list size should be 1");
        assertEquals("I am groot", taskCollection.getTasks().getFirst().getTitle(), "Extra whitespace should be removed");
    }

    @Test
    void taskIsRemoved(){
        TaskCollection taskCollection = new TaskCollection(Path.of("testtasks.json"));
        taskCollection.addTask("I am Iron Man");
        taskCollection.removeTask(taskCollection.getTasks().getFirst());
        assertEquals(0, taskCollection.getTasks().size(), "Task should be removed");
    }

    @Test
    void emptyTaskNotAdded(){
        TaskCollection taskCollection = new TaskCollection(Path.of("testtasks.json"));
        taskCollection.addTask("   ");
        assertEquals(0, taskCollection.getTasks().size(), "Empty task shouldn't be added");
    }

    @Test
    void identicalTasksAreStored(){
        TaskCollection taskCollection = new TaskCollection(Path.of("testtasks.json"));
        taskCollection.addTask("There are two of us");
        taskCollection.addTask("There are two of us");
        assertEquals(2, taskCollection.getTasks().size(), "Dupes should be kept");
        assertEquals(taskCollection.getTasks().getFirst().getTitle(), taskCollection.getTasks().getLast().getTitle(), "Dupes should be matching");
    }

    @Test
    void dupesDoNotShareBehaviour(){
        TaskCollection taskCollection = new TaskCollection(Path.of("testtasks.json"));
        taskCollection.addTask("There are two of us");
        taskCollection.addTask("There are two of us");
        taskCollection.getTasks().getFirst().setIsDone(true);
        assertTrue(taskCollection.getTasks().getFirst().getIsDone(), "Task should be editable");
        assertFalse(taskCollection.getTasks().getLast().getIsDone(), "Dupe behaviour should be independent");
    }

    @Test
    void orderShouldBeKept(){
        TaskCollection taskCollection = new TaskCollection(Path.of("testtasks.json"));
        taskCollection.addTask("I am the first one");
        taskCollection.addTask("I am the second one");
        assertEquals("I am the first one", taskCollection.getTasks().getFirst().getTitle(), "Tasks should be in add order");
        assertEquals("I am the second one", taskCollection.getTasks().getLast().getTitle(), "Tasks should be in add order");
    }

}
