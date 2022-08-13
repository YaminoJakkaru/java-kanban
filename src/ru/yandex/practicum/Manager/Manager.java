package ru.yandex.practicum.Manager;

import ru.yandex.practicum.Manager.Tasks.*;

import java.util.ArrayList;
import java.util.HashMap;


public class Manager {
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Subtask> subtasks;

    public Manager() {
        epics = new HashMap<>();
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
    }

    int IdentificationNumber = 0;

    public int getNewId() {
        return ++IdentificationNumber;
    }

    public void addTask(Task task) {
        task.setIdentificationNumber(getNewId());
        tasks.put(task.getIdentificationNumber(), task);

    }

    public void addSubtask(Subtask subtask) {
        subtask.setIdentificationNumber(getNewId());
        epics.get(subtask.getEpic()).addSubtask(subtask.getIdentificationNumber());
        subtasks.put(subtask.getIdentificationNumber(), subtask);
        updateEpicStatus(subtask.getEpic());
    }

    public void addEpic(Epic epic) {
        epic.setIdentificationNumber(getNewId());
        epics.put(epic.getIdentificationNumber(), epic);
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Subtask> getEpicSubtasks(int id) {

        ArrayList<Subtask> subtaskArrayList = new ArrayList<>();
        if (epics.containsKey(id)) {
            for (int subtaskId : epics.get(id).getSubtasks()) {
                subtaskArrayList.add(subtasks.get(subtaskId));
            }
        }
        return subtaskArrayList;
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.deleteAllSubtasks();
            updateEpicStatus(epic.getIdentificationNumber());
        }
    }

    public void deleteAllEpics() {
        epics.clear();
        deleteAllSubtasks();
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public Subtask getSubtask(int id) {

        return subtasks.get(id);
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getIdentificationNumber())) {
            tasks.put(task.getIdentificationNumber(), task);
        }
    }

    public void updateEpic(Epic epic) {
        if (!epics.containsKey(epic.getIdentificationNumber())) {
            return;
        }
        epics.get(epic.getIdentificationNumber()).setName(epic.getName());
        epics.get(epic.getIdentificationNumber()).setDescription(epic.getDescription());
    }

    public void updateSubtask(Subtask subtask) {
        if (!subtasks.containsKey(subtask.getIdentificationNumber()) || !epics.containsKey(subtask.getEpic())) {
            return;
        }
        subtasks.put(subtask.getIdentificationNumber(), subtask);
        updateEpicStatus(subtasks.get(subtask.getIdentificationNumber()).getEpic());
    }

    public void updateEpicStatus(int id) {
        int doneSubtasks = 0;

        for (int subtaskId : epics.get(id).getSubtasks()) {
            if (subtasks.get(subtaskId).getStatus().equals(Status.IN_PROGRESS)) {
                epics.get(id).setStatus(Status.IN_PROGRESS);
                return;
            }
            if (subtasks.get(subtaskId).getStatus().equals(Status.DONE)) {
                doneSubtasks++;
            }
        }
        if (doneSubtasks == epics.get(id).getSubtasks().size() && doneSubtasks != 0) {
            epics.get(id).setStatus(Status.DONE);
            return;
        }
        if (doneSubtasks != 0) {
            epics.get(id).setStatus(Status.IN_PROGRESS);
        }
    }

    public void deleteTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        }
    }

    public void deleteEpic(int id) {
        if (epics.containsKey(id)) {
            for (int subtaskId : epics.get(id).getSubtasks()) {
                deleteSubtask(subtaskId);
            }
            epics.remove(id);
        }
    }

    public void deleteSubtask(int id) {
        if (subtasks.containsKey(id)) {
            int epicId = subtasks.get(id).getEpic();
            epics.get(epicId).getSubtasks().remove((Integer) id);
            subtasks.remove(id);
            updateEpicStatus(epicId);

        }
    }
}
