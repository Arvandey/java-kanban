package ru.yandex.managers;

import ru.yandex.entities.Epic;
import ru.yandex.entities.Subtask;
import ru.yandex.entities.Task;
import ru.yandex.enums.TasksType;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    void createNewTask(Task task);

    void createNewTask(Epic epic);

    void createNewTask(Subtask subtask);

    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpic(int id);

    ArrayList<Task> getTasksList(TasksType tasksType);

    void deleteAllTasks(TasksType tasksType);

    void deleteTask(int id, TasksType tasksType);

    ArrayList<Subtask> getSubtasks(int epicId);

    void updateTask(Task task);

    void updateTask(Subtask subtask);

    void updateTask(Epic epic);

    List<Task> getHistory();
}
