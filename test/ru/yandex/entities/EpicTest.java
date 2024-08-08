package ru.yandex.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.managers.Manager;
import ru.yandex.managers.TaskManager;


class EpicTest {

    private final TaskManager taskManager = Manager.getDefault();

    @Test
    public void shouldBeEqualsWithSameId() {
        Epic epic1 = new Epic("Tittle1", "Description1");
        epic1.setId(1);
        Epic epic2 = new Epic("Tittle2", "Description2");
        epic2.setId(1);
        Assertions.assertEquals(epic1, epic2);
    }

}