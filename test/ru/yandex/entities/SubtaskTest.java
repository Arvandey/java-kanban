package ru.yandex.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.enums.Status;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    public void shouldBeEqualsWithSameId() {
        Subtask subtask1 = new Subtask("Tittle1", "Description1", Status.NEW,2);
        subtask1.setId(1);
        Subtask subtask2 = new Subtask("Tittle2", "Description2", Status.NEW, 2);
        subtask2.setId(1);
        Assertions.assertEquals(subtask1, subtask2);
    }

}