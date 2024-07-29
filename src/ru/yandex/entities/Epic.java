package ru.yandex.entities;

import ru.yandex.enums.Status;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {

    private HashMap<Integer, Subtask> subtasks;

    public Epic(String name, String description) {
        super(name, description, Status.NEW);
        this.subtasks = new HashMap<>();
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void add(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    public void clearSubtasks() {
        subtasks.clear();
    }

    public void removeSubtask(int subtaskId) {
        subtasks.remove(subtaskId);
    }

}
