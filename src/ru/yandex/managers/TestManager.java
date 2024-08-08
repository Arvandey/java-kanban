package ru.yandex.managers;

import ru.yandex.entities.Epic;
import ru.yandex.entities.Subtask;
import ru.yandex.entities.Task;
import ru.yandex.enums.Status;
import ru.yandex.enums.TasksType;

import java.util.ArrayList;
import java.util.Scanner;

public class TestManager {
    public static void startTest() {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = Manager.getDefault();

        enterTestData(taskManager);

        while (true) {
            printMenu();
            String command = scanner.nextLine();

            switch (command) {
                case "1":
                    printTasks(scanner, taskManager);
                    break;
                case "2":
                    createNewTask(scanner, taskManager);
                    break;
                case "3":
                    deleteTasks(scanner, taskManager);
                    break;
                case "4":
                    printTask(scanner, taskManager);
                    break;
                case "5":
                    deleteTask(scanner, taskManager);
                    break;
                case "6":
                    updateTask(scanner, taskManager);
                    break;
                case "7":
                    printSubtasks(scanner, taskManager);
                    break;
                case "8":
                    System.out.println(taskManager.getHistory());
                    break;
                case "0":
                    return;
                default:

            }
        }
    }

    private static void printMenu() {
        System.out.println("Введите номер команды:");
        System.out.println("1 - Получить список всех задач");
        System.out.println("2 - Создать новую задачу");
        System.out.println("3 - Удаление задач");
        System.out.println("4 - Посмотреть задачу");
        System.out.println("5 - Удалить задачу");
        System.out.println("6 - Обновить задачу");
        System.out.println("7 - Получить список подзадач эпика");
        System.out.println("8 - История просмотра");
        System.out.println("0 - Выход");
    }

    private static void printTasksTypes() {
        System.out.println("Введите тип задач:");
        System.out.println("task - Задача");
        System.out.println("epic - Эпик");
        System.out.println("subtask - Подзадача");
    }

    private static void printTasksStatuses() {
        System.out.println("new - Новая");
        System.out.println("in progress - Выполняется");
        System.out.println("done - Сделана");
    }

    private static void createNewTask(Scanner scanner, TaskManager taskManager) {
        String type;
        TasksType tasksType;
        String epicId;
        System.out.println("Выберете тип:");
        printTasksTypes();
        type = scanner.nextLine();
        tasksType = TasksType.valueOf(type.toUpperCase());
        System.out.println("Введите название:");
        String title = scanner.nextLine();
        System.out.println("Введите описание:");
        String description = scanner.nextLine();
        System.out.println("Выберете статус:");
        printTasksStatuses();
        String status = scanner.nextLine().replace(" ", "_");
        Status taskStatus = Status.valueOf(status.toUpperCase());
        if (tasksType == TasksType.SUBTASK) {
            System.out.println("Введите id эпика:");
            epicId = scanner.nextLine();
            if (!epicId.isEmpty()) {
                Subtask testSubtask = new Subtask(title,description, taskStatus, Integer.parseInt(epicId));
                taskManager.createNewTask(testSubtask);
            } else {
                System.out.println("Необходимо ввести id эпика:");
            }
        } else if (tasksType == TasksType.EPIC) {
            Epic testEpic = new Epic(title, description);
            taskManager.createNewTask(testEpic);
        } else if (tasksType == TasksType.TASK) {
            Task testTask = new Task(title, description, taskStatus);
            taskManager.createNewTask(testTask);
        }

    }

    private static void printTasks(Scanner scanner, TaskManager taskManager) {
        String type;
        TasksType tasksType;
        printTasksTypes();
        type = scanner.nextLine();
        tasksType = TasksType.valueOf(type.toUpperCase());
        ArrayList<Task> tasks = taskManager.getTasksList(tasksType);
        if (tasks != null && !tasks.isEmpty()) {
            for (Task task : tasks) {
                System.out.println(task);
            }
        } else {
            System.out.println("Список пуст");
        }
    }

    private static void deleteTasks(Scanner scanner, TaskManager taskManager) {
        String type;
        TasksType tasksType;
        printTasksTypes();
        type = scanner.nextLine();
        tasksType = TasksType.valueOf(type.toUpperCase());
        taskManager.deleteAllTasks(tasksType);
    }

    private static void printTask(Scanner scanner, TaskManager taskManager) {
        String taskId;
        String type;
        TasksType tasksType;
        printTasksTypes();
        type = scanner.nextLine();
        tasksType = TasksType.valueOf(type.toUpperCase());
        System.out.println("Введите id задачи:");
        taskId = scanner.nextLine();
        switch (tasksType) {
            case TASK:
                Task task = taskManager.getTask(Integer.parseInt(taskId));
                if (task != null) {
                    System.out.println(task);
                } else {
                    System.out.println("Задача отсутствует");
                }
                break;
            case SUBTASK:
                Subtask subtask = taskManager.getSubtask(Integer.parseInt(taskId));
                if (subtask != null) {
                    System.out.println(subtask);
                } else {
                    System.out.println("Подзадача отсутствует");
                }
                break;
            case EPIC:
                Epic epic = taskManager.getEpic(Integer.parseInt(taskId));
                if (epic != null) {
                    System.out.println(epic);
                } else {
                    System.out.println("Эпик отсутствует");
                }
            default:
                System.out.println("Неизвестный тип задач");
        }

    }

    private static void deleteTask(Scanner scanner, TaskManager taskManager) {
        TasksType tasksType;
        String type;
        String taskId;
        printTasksTypes();
        type = scanner.nextLine();
        tasksType = TasksType.valueOf(type.toUpperCase());
        System.out.println("Введите id задачи:");
        taskId = scanner.nextLine();
        taskManager.deleteTask(Integer.parseInt(taskId), tasksType);
    }

    private static void updateTask(Scanner scanner, TaskManager taskManager) {
        String newTaskId;;
        String oldTaskId;
        TasksType tasksType;
        String type;
        printTasksTypes();
        type = scanner.nextLine();
        tasksType = TasksType.valueOf(type.toUpperCase());
        System.out.println("Введите id задачи:");
        oldTaskId = scanner.nextLine();
        System.out.println("Введите новый id задачи:");
        newTaskId  = scanner.nextLine();
        System.out.println("Введите новое название:");
        String newTitle = scanner.nextLine();
        System.out.println("Введите новое описание:");
        String newDescription = scanner.nextLine();
        System.out.println("Выберете новый статус:");
        printTasksStatuses();
        String newStatus = scanner.nextLine().replace(" ", "_");
        Status newTaskStatus = Status.valueOf(newStatus.toUpperCase());
        switch (tasksType) {
            case TASK:
                Task task = taskManager.getTask(Integer.parseInt(oldTaskId));
                task.setId(Integer.parseInt(newTaskId));
                task.setName(newTitle);
                task.setDescription(newDescription);
                task.setStatus(newTaskStatus);
                taskManager.updateTask(task);
                break;
            case SUBTASK:
                System.out.println("Введите новый id эпика:");
                String newEpicId = scanner.nextLine();
                Subtask subtask = taskManager.getSubtask(Integer.parseInt(oldTaskId));
                subtask.setId(Integer.parseInt(newTaskId));
                subtask.setName(newTitle);
                subtask.setDescription(newDescription);
                subtask.setStatus(newTaskStatus);
                subtask.setEpicId(Integer.parseInt(newEpicId));
                taskManager.updateTask(subtask);
                break;
            case EPIC:
                Epic epic = taskManager.getEpic(Integer.parseInt(oldTaskId));
                epic.setId(Integer.parseInt(newTaskId));
                epic.setName(newTitle);
                epic.setDescription(newDescription);
                epic.setStatus(newTaskStatus);
                taskManager.updateTask(epic);
            default:
                System.out.println("Неизвестный тип задач");
        }

    }

    private static void printSubtasks(Scanner scanner, TaskManager taskManager) {
        System.out.println("Введите id эпика:");
        String epicId = scanner.nextLine();
        ArrayList<Subtask> subtasks = taskManager.getSubtasks(Integer.parseInt(epicId));
        if (subtasks != null && !subtasks.isEmpty()) {
            for (Subtask subtask : subtasks) {
                System.out.println(subtask);
            }
        } else {
            System.out.println("Список пуст");
        }
    }

    private static void enterTestData(TaskManager taskManager) {
        Task testTask1 = new Task("Уборка", "Сделать уборку дома", Status.NEW);
        Task testTask2 = new Task("Cпринт 4", "Доделать ДЗ", Status.NEW);
        taskManager.createNewTask(testTask1);
        taskManager.createNewTask(testTask2);
        Epic testEpic1 = new Epic("Приготовить обед", "Приготовить обед");
        Epic testEpic2 = new Epic("Приготовить ужин", "Приготовить ужин");
        taskManager.createNewTask(testEpic1);
        taskManager.createNewTask(testEpic2);
        Subtask testSubtask1 = new Subtask("Суп", "Сварить суп", Status.NEW, testEpic1.getId());
        Subtask testSubtask2 = new Subtask("Блюдо основное", "Приготовить основное блюдо", Status.NEW, testEpic1.getId());
        Subtask testSubtask3 = new Subtask("Салат", "Приготовить салат", Status.NEW, testEpic2.getId());
        Subtask testSubtask4 = new Subtask("Блюдо на ужин", "Приготовить блюдо на ужин", Status.NEW, testEpic2.getId());
        taskManager.createNewTask(testSubtask1);
        taskManager.createNewTask(testSubtask2);
        taskManager.createNewTask(testSubtask3);
        taskManager.createNewTask(testSubtask4);
    }
}
