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
    private HashMap<Integer, Epic> epics;

    public TaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
    }

    public static int getNewId() {
        idCounter++;
        return idCounter;
    }

    public void createNewTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void createNewTask(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void createNewTask(Subtask subtask) {
        epics.get(subtask.getEpicId()).getSubtasks().put(subtask.getId(), subtask);
        updateEpicStatus(subtask.getEpicId());
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }
    public Subtask getSubtask(int id) {
        for (Epic epic : epics.values()) {
            if (epic.getSubtasks().containsKey(id)) {
                return epic.getSubtasks().get(id);
            }
        }
        return null;
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public ArrayList<Task> getTasksList(TasksType tasksType) {
        ArrayList<Task> tasksArray = new ArrayList<>();
        switch (tasksType) {
            case TASK:
                tasksArray = new ArrayList<>(tasks.values());
                break;
            case SUBTASK:
                for (Epic epic : epics.values()) {
                    tasksArray.addAll(epic.getSubtasks().values());
                }
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
                for (Epic epic : epics.values()) {
                    epic.getSubtasks().clear();
                }
                break;
            case EPIC:
                for (Epic epic : epics.values()) {
                    epic.getSubtasks().clear();
                }
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
                for (Epic epic : epics.values()) {
                    epic.getSubtasks().remove(id);
                }
                break;
            case EPIC:
                epics.get(id).getSubtasks().clear();
                epics.remove(id);
            default:
                System.out.println("Неизвестный тип задач");
        }
    }

    public ArrayList<Subtask> getSubtasks(int epicId) {
        return new ArrayList<>(epics.get(epicId).getSubtasks().values());
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateTask(Subtask subtask) {
        epics.get(subtask.getEpicId()).getSubtasks().put(subtask.getId(), subtask);
        updateEpicStatus(subtask.getEpicId());
    }

    public void updateTask(Epic epic) {
        epics.get(epic.getId()).setName(epic.getName());
        epics.get(epic.getId()).setDescription(epic.getDescription());
    }

    private void updateEpicStatus(int epicId) {
        ArrayList<Subtask> subtasksArray = getSubtasks(epicId);
        if (subtasksArray.isEmpty()) {
            epics.get(epicId).setStatus(Status.NEW);
            return;
        }
        int newCount = 0;
        int doneCount = 0;
        for (Subtask subtask : subtasksArray) {
            switch (subtask.getStatus()) {
                case NEW:
                    newCount++;
                    break;
                case DONE:
                    doneCount++;
                    break;
                default:
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
