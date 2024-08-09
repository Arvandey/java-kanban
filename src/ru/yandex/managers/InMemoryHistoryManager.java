package ru.yandex.managers;

import ru.yandex.entities.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private List<Task> browsingHistory;

    public InMemoryHistoryManager() {
        browsingHistory = new LinkedList<>();
    }

    @Override
    public void add(Task task) {
        if (browsingHistory.size() >= 10) {
            browsingHistory.removeFirst();
        }
        browsingHistory.addLast(task);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(browsingHistory);
    }

}
