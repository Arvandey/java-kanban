package ru.yandex.entities;

import ru.yandex.enums.Status;

import java.util.HashMap;

public class Epic extends Task {

    private HashMap<Integer, Subtask> subtasks;

    public Epic(int id, String name, String description) {
        super(id, name, description, Status.NEW);
        this.subtasks = new HashMap<>();
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(HashMap<Integer, Subtask> subtasks) {
        this.subtasks = subtasks;
    }
}
