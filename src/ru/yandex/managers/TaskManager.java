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
    private HashMap<Integer, Subtask> subtasks;

    public TaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks =new HashMap<>();
    }

    private static int getNewId() {
        idCounter++;
        return idCounter;
    }

    public void createNewTask(Task task) {
        task.setId(getNewId());
        tasks.put(task.getId(), task);
    }

    public void createNewTask(Epic epic) {
        epic.setId(getNewId());
        epics.put(epic.getId(), epic);
    }

    public void createNewTask(Subtask subtask) {
        subtask.setId(getNewId());
        if (epics.containsKey(subtask.getEpicId())) {
            epics.get(subtask.getEpicId()).getSubtasks().put(subtask.getId(), subtask);
        } else {
            System.out.println("Эпика с данным id не существует.");
        }
        updateEpicStatus(subtask.getEpicId());
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
        Task taskToUpdate = tasks.get(task.getId());
        if (taskToUpdate != null) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Задача не найден.");
        }

    }

    public void updateTask(Subtask subtask) {
        Subtask subtaskToUpdate = subtasks.get(subtask.getId());
        if (subtaskToUpdate != null) {
            subtasks.put(subtaskToUpdate.getId(), subtaskToUpdate);
            epics.get(subtaskToUpdate.getEpicId()).getSubtasks().put(subtaskToUpdate.getId(), subtaskToUpdate);
            updateEpicStatus(subtask.getEpicId());
        } else {
            System.out.println("Подзадача не найден.");
        }

    }

    public void updateTask(Epic epic) {
        Epic epicToUpdate = epics.get(epic.getId());
        if (epicToUpdate != null) {
            epicToUpdate.setName(epic.getName());
            epicToUpdate.setDescription(epic.getDescription());
            epics.put(epicToUpdate.getId(), epicToUpdate);
        } else {
            System.out.println("Эпика не найден.");
        }
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
