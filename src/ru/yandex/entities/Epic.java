package ru.yandex.entities;

import ru.yandex.enums.Status;

public class Epic extends Task {

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }
}
