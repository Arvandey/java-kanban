package ru.yandex.managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {

    @Test
    void shouldReturnInitializedInMemoryTaskManager() {
        TaskManager taskManager = Manager.getDefault();
        Assertions.assertEquals(taskManager.getClass(), InMemoryTaskManager.class);
        Assertions.assertNotNull(taskManager);
    }

    @Test
    void shouldReturnInitializedInMemoryHistoryManager() {
        HistoryManager historyManager = Manager.getDefaultHistory();
        Assertions.assertEquals(historyManager.getClass(), InMemoryHistoryManager.class);
        Assertions.assertNotNull(historyManager);
    }
}