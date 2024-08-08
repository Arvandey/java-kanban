package ru.yandex.managers;

import ru.yandex.entities.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private List<Task> browsingHistory;

    InMemoryHistoryManager() {
        browsingHistory = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        if (browsingHistory.size() < 10) {
            browsingHistory.add(task);
        } else {
            browsingHistory.remove(0);
            browsingHistory.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return browsingHistory;
    }

}
