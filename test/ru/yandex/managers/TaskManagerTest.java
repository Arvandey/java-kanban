package ru.yandex.managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.entities.Epic;
import ru.yandex.entities.Subtask;
import ru.yandex.entities.Task;
import ru.yandex.enums.Status;


class TaskManagerTest {

    private  Task task;
    private  Epic epic;
    private  Subtask subtask;
    private  TaskManager taskManager;

    @BeforeEach
    void  initEach() {
        taskManager = Manager.getDefault();
        task = new Task("Tittle1", "Description1", Status.NEW);
        task.setId(1);
        taskManager.createNewTask(task);
        epic = new Epic("Tittle2", "Description2");
        epic.setId(2);
        taskManager.createNewTask(epic);
        subtask = new Subtask("Tittle3", "Description3", Status.NEW, 2);
        subtask.setId(3);
        taskManager.createNewTask(subtask);
    }


    @Test
    void taskShouldBeAdded() {
        Task addedTask = taskManager.getTask(1);
        Assertions.assertNotNull(addedTask);
        Assertions.assertEquals(task, addedTask);
    }

    @Test
    void epicShouldBeAdded() {
        Epic addedEpic = taskManager.getEpic(2);
        Assertions.assertNotNull(addedEpic);
        Assertions.assertEquals(epic, addedEpic);
    }

    @Test
    void subtaskShouldBeAdded() {
        Subtask addedSubtask = taskManager.getSubtask(3);
        Assertions.assertNotNull(addedSubtask);
        Assertions.assertEquals(subtask, addedSubtask);
    }

    @Test
    void taskShouldBeUnchanged() {
        Task addedTask = taskManager.getTask(1);
        Assertions.assertEquals(task.getId(), addedTask.getId());
        Assertions.assertEquals(task.getName(), addedTask.getName());
        Assertions.assertEquals(task.getDescription(), addedTask.getDescription());
        Assertions.assertEquals(task.getStatus(), addedTask.getStatus());
    }

    @Test
    void epicShouldBeUnchanged() {
        Epic addedEpic = taskManager.getEpic(2);
        Assertions.assertEquals(epic.getId(), addedEpic.getId());
        Assertions.assertEquals(epic.getName(), addedEpic.getName());
        Assertions.assertEquals(epic.getDescription(), addedEpic.getDescription());
        Assertions.assertEquals(epic.getStatus(), addedEpic.getStatus());
        Assertions.assertEquals(epic.getSubtasks(), addedEpic.getSubtasks());
    }

    @Test
    void subtaskShouldBeUnchanged() {
        Subtask addedSubtask = taskManager.getSubtask(3);
        Assertions.assertEquals(subtask.getId(), addedSubtask.getId());
        Assertions.assertEquals(subtask.getName(), addedSubtask.getName());
        Assertions.assertEquals(subtask.getDescription(), addedSubtask.getDescription());
        Assertions.assertEquals(subtask.getStatus(), addedSubtask.getStatus());
        Assertions.assertEquals(subtask.getEpicId(), addedSubtask.getEpicId());
    }

}