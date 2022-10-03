package ru.yandex.practicum.Managers;

import ru.yandex.practicum.Tasks.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.*;


public class FileBackedTaskManager extends InMemoryTaskManager {


    private final File file;

    public FileBackedTaskManager(File file) {
        super();
        this.file = file;
    }

    private void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("id,type,name,status,description,epic");
            bw.newLine();
            for (Task task : super.getTasks()) {
                bw.write(task.toString());
                bw.newLine();
            }
            for (Task epic : super.getEpics()) {
                bw.write(epic.toString());
                bw.newLine();
            }
            for (Task subtask : super.getSubtasks()) {
                bw.write(subtask.toString());
                bw.newLine();
            }
            bw.newLine();
            bw.write(historyToString(historyManager));
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
    }

    public Task fromString(String value) {
        String[] lineContents = value.split(",");

        switch (lineContents[1]) {
            case "TASK":
                return new Task(Integer.parseInt(lineContents[0]), lineContents[2], Status.valueOf(lineContents[3]),
                        lineContents[4]);
            case "EPIC":

                return new Epic(Integer.parseInt(lineContents[0]), lineContents[2], Status.valueOf(lineContents[3]),
                        lineContents[4]);
            case "SUBTASK":
                return new Subtask(Integer.parseInt(lineContents[0]), lineContents[2], Status.valueOf(lineContents[3]),
                        lineContents[4], Integer.parseInt(lineContents[5]));
        }
        throw new ManagerSaveException();
    }

    public static String historyToString(HistoryManager manager) {
        StringBuilder builder = new StringBuilder();

        for (Task value : manager.getHistory()) {
            builder.append(value.getIdentificationNumber()).append(",");
        }
        return builder.toString();
    }

    public static List<Integer> historyFromString(String value) {
        try {
            String[] lines = value.split(System.lineSeparator());
            List<Integer> identificationNumbers = new ArrayList<>();
            String[] lineContents = lines[lines.length - 1].split(",");

            for (String content : lineContents) {
                identificationNumbers.add(Integer.parseInt(content));
            }
            return identificationNumbers;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public int addTask(Task task) {
        int id = super.addTask(task);

        save();
        return id;
    }

    @Override
    public int addSubtask(Subtask subtask) {
        int id = super.addSubtask(subtask);

        save();
        return id;
    }

    @Override
    public int addEpic(Epic epic) {
        int id = super.addEpic(epic);

        save();
        return id;
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);

        save();
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = super.getEpic(id);

        save();
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = super.getSubtask(id);

        save();
        return subtask;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }
}
