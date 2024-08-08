package ru.yandex.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.enums.Status;
import ru.yandex.managers.Manager;
import ru.yandex.managers.TaskManager;

class TaskTest {

    private final TaskManager taskManager = Manager.getDefault();

    @Test
    public void shouldBeEqualsWithSameId() {
        Task task1 = new Task("Tittle1", "Description1", Status.NEW);
        task1.setId(1);
        Task task2 = new Task("Tittle2", "Description2", Status.NEW);
        task2.setId(1);
        Assertions.assertEquals(task1, task2);
    }

}