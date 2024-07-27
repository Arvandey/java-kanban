package ru.yandex.managers;

import ru.yandex.entities.Epic;
import ru.yandex.entities.Subtask;
import ru.yandex.entities.Task;
import ru.yandex.enums.Status;
import ru.yandex.enums.TasksType;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private static int idCounter = 0;

    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Subtask> subtasks;
    private HashMap<Integer, Epic> epics;

    public TaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
    }

    public static int getNewId() {
        idCounter++;
        return idCounter;
    }

    public void createNewTask(TasksType tasksType, Task task, int epicId) {
        switch (tasksType) {
            case TASK:
                tasks.put(task.getId(), task);
                break;
            case EPIC:
                epics.put(task.getId(), (Epic) task);
                break;
            case SUBTASK:
                Subtask subtask = new Subtask(task.getName(), task.getDescription(), task.getStatus(), epicId);
                subtasks.put(subtask.getId(), subtask);
                updateEpicStatus(epicId);
                break;
            default:
        }
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }
    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public ArrayList<Task> getTasksList(TasksType tasksType) {
        ArrayList<Task> tasksArray;
        switch (tasksType) {
            case TASK:
                tasksArray = new ArrayList<>(tasks.values());
                break;
            case SUBTASK:
                tasksArray = new ArrayList<>(subtasks.values());
                break;
            case EPIC:
                tasksArray = new ArrayList<>(epics.values());
                break;
            default:
                tasksArray = null;
        }
        return tasksArray;
    }

    public void deleteAllTasks(TasksType tasksType) {
        switch (tasksType) {
            case TASK:
                tasks.clear();
                break;
            case SUBTASK:
                subtasks.clear();
                break;
            case EPIC:
                epics.clear();
                break;
            default:
                System.out.println("Неизвестный тип задач");
        }
    }



    public void deleteTask(int id, TasksType tasksType) {
        switch (tasksType) {
            case TASK:
                tasks.remove(id);
                break;
            case SUBTASK:
                subtasks.remove(id);
                break;
            case EPIC:
                epics.remove(id);
            default:
                System.out.println("Неизвестный тип задач");
        }
    }

    public ArrayList<Subtask> getSubtasks(int epicId) {
        ArrayList<Subtask> subtasksArray = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                subtasksArray.add(subtask);
            }
        }
        return subtasksArray;
    }

    public void updateTask(Task task) {
        tasks.remove(task.getId());
        tasks.put(task.getId(), task);
    }

    public void updateTask(Subtask subtask) {
        subtasks.remove(subtask.getId());
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(subtask.getEpicId());
    }

    public void updateTask(Epic epic) {
        epics.remove(epic.getId());
        epics.put(epic.getId(), epic);
    }

    public void updateEpicStatus(int epicId) {
        ArrayList<Subtask> subtasksArray = getSubtasks(epicId);
        if (subtasksArray.isEmpty()) {
            epics.get(epicId).setStatus(Status.NEW);
            return;
        }
        int newCount = 0;
        int inProgressCount = 0;
        int doneCount = 0;
        for (Subtask subtask : subtasksArray) {
            switch (subtask.getStatus()) {
                case NEW:
                    newCount++;
                    break;
                case IN_PROGRESS:
                    inProgressCount++;
                    break;
                case DONE:
                    doneCount++;
                    break;
            }
        }
        if (newCount == subtasksArray.size()) {
            epics.get(epicId).setStatus(Status.NEW);
        } else if (doneCount == subtasksArray.size()) {
            epics.get(epicId).setStatus(Status.DONE);
        } else {
            epics.get(epicId).setStatus(Status.IN_PROGRESS);
        }

    }

}
