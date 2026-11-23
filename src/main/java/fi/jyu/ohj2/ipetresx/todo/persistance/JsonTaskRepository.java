package fi.jyu.ohj2.ipetresx.todo.persistance;
import java.nio.file.Files;
import java.util.*;
import fi.jyu.ohj2.ipetresx.todo.model.Task;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Path;

public class JsonTaskRepository implements TaskRepository{
    private final Path saveFile;
    private final ObjectMapper mapper = new ObjectMapper();

    public JsonTaskRepository(Path path) {
        saveFile = path;
    }

    @Override
    public List<Task> load() throws RepositoryException {
        if(Files.notExists(saveFile))
            return List.of();
        try {
            return mapper.readValue(saveFile.toFile(), new TypeReference<>() {});
        }
        catch (JacksonException e){
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public void save(List<Task> tasks) throws RepositoryException{
        try {
            mapper.writeValue(saveFile.toFile(), tasks);
        }
        catch (JacksonException e){
            throw new RepositoryException(e.getMessage());
        }
    }
}
