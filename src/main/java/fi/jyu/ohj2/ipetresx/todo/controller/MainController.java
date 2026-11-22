package fi.jyu.ohj2.ipetresx.todo.controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.nio.file.*;

import fi.jyu.ohj2.ipetresx.todo.App;
import fi.jyu.ohj2.ipetresx.todo.model.Task;
import fi.jyu.ohj2.ipetresx.todo.model.TaskCollection;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
        nameColumn.setCellValueFactory(n -> n.getValue().titleProperty());
        taskTable.getColumns().add(nameColumn);
        taskTable.getSelectionModel().selectedItemProperty().addListener((obs, oldv, newv) -> {
            deleteTaskButton.setDisable(newv == null);
        });
        taskTable.setRowFactory(tv -> {
            TableRow<Task> row = new TableRow<>();
            row.setOnMouseClicked(event ->{
                if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2 &&
                !row.isEmpty()){
                    Task task = row.getItem();
                    openTaskEditor(task);
                }
            });
            return row;
        });
    }

    private void openTaskEditor(Task task){
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("task-edit.fxml"));
            Scene scene = new Scene(loader.load());
            TaskEditController controller = loader.getController();
            controller.setTask(task);
            Stage dialog = new Stage();
            dialog.setMinWidth(400);
            dialog.setMinHeight(300);
            dialog.setScene(scene);
            dialog.setTitle("Edit task: " + task.getTitle());
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
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
