package ru.yandex.managers;

import ru.yandex.entities.Task;

import java.util.List;

public interface HistoryManager {

    void add(Task task);

    List<Task> getHistory();
}
