package fi.jyu.ohj2.ipetresx.todo.controller;

import java.util.Comparator;
import java.util.List;
import java.nio.file.*;

import fi.jyu.ohj2.ipetresx.todo.model.Task;
import fi.jyu.ohj2.ipetresx.todo.model.TaskCollection;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.cell.CheckBoxTableCell;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

public class MainController implements Initializable {
    @FXML
    private Button addNewTaskButton, deleteTaskButton;

    @FXML
    private TextField newTaskName;

    @FXML
    private TableView <Task> taskTable;

    private TaskCollection taskCollection = new TaskCollection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SortedList<Task> sortedTasks = taskCollection.getTasks().sorted(Comparator.comparing(Task::getIsDone));
        taskTable.setItems(sortedTasks);
        setTable();
        newTaskName.setText("");
        addNewTaskButton.setOnAction(event -> {
            addTask();
        });
        newTaskName.setOnAction(event ->{
            addTask();
        });
        deleteTaskButton.setOnAction(event ->{
            deleteSelected();
        });
        deleteTaskButton.setDisable(true);
    }

    private void setTable(){
        taskTable.setEditable(true);
        TableColumn<Task, Boolean> completedColumn = new TableColumn<>("Completed");
        completedColumn.setCellValueFactory(n -> n.getValue().isDoneProperty());
        completedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(completedColumn));
        taskTable.getColumns().add(completedColumn);
        TableColumn<Task, String> nameColumn = new TableColumn<>("Task");
        nameColumn.setCellValueFactory(n -> n.getValue().nameProperty());
        taskTable.getColumns().add(nameColumn);
        taskTable.getSelectionModel().selectedItemProperty().addListener((obs, oldv, newv) -> {
            deleteTaskButton.setDisable(newv == null);
        });
    }

    private void deleteSelected(){
        Task task = taskTable.getSelectionModel().getSelectedItem();
        if(task != null)
            taskCollection.removeTask(task);
    }



    private void addTask(){
        String text = newTaskName.getText();
        newTaskName.requestFocus();
        if(text == null || text.isBlank())
            return;
        taskCollection.addTask(text);
        newTaskName.setText("");
    }
}
