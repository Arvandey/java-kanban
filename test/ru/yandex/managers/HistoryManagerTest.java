package ru.yandex.managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.entities.Task;
import ru.yandex.enums.Status;

import java.util.List;

class HistoryManagerTest {

    private final HistoryManager historyManager = Manager.getDefaultHistory();

    private final TaskManager taskManager = Manager.getDefault();

    @Test
    void shouldBeAddedToHistory() {
        Task task = new Task("Tittle1", "Description1", Status.NEW);
        task.setId(1);
        taskManager.createNewTask(task);
        historyManager.add(task);
        List<Task> tasksList = historyManager.getHistory();
        Task taskHistory = tasksList.get(0);
        Assertions.assertEquals(task.getId(), taskHistory.getId());
        Assertions.assertEquals(task.getName(), taskHistory.getName());
        Assertions.assertEquals(task.getDescription(), taskHistory.getDescription());
        Assertions.assertEquals(task.getStatus(), taskHistory.getStatus());
    }

}