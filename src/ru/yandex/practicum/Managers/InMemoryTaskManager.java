package ru.yandex.practicum.Managers;

import ru.yandex.practicum.Tasks.Epic;
import ru.yandex.practicum.Tasks.Status;
import ru.yandex.practicum.Tasks.Subtask;
import ru.yandex.practicum.Tasks.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InMemoryTaskManager implements TaskManager {
    HistoryManager historyManager= Managers.getDefaultHistory();
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Subtask> subtasks;

    int IdentificationNumber = 0;

    public InMemoryTaskManager() {
        epics = new HashMap<>();
        tasks = new HashMap<>();
        subtasks = new HashMap<>();

    }

    @Override
    public int getNewId() {
        return ++IdentificationNumber;
    }

    @Override
    public void addTask(Task task) {
        task.setIdentificationNumber(getNewId());
        tasks.put(task.getIdentificationNumber(), task);

    }

    @Override
    public void addSubtask(Subtask subtask) {
        subtask.setIdentificationNumber(getNewId());
        epics.get(subtask.getEpic()).addSubtask(subtask.getIdentificationNumber());
        subtasks.put(subtask.getIdentificationNumber(), subtask);
        updateEpicStatus(subtask.getEpic());
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setIdentificationNumber(getNewId());
        epics.put(epic.getIdentificationNumber(), epic);
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(int id) {

        ArrayList<Subtask> subtaskArrayList = new ArrayList<>();
        if (epics.containsKey(id)) {
            for (int subtaskId : epics.get(id).getSubtasks()) {
                subtaskArrayList.add(subtasks.get(subtaskId));
            }
        }
        return subtaskArrayList;
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.deleteAllSubtasks();
            updateEpicStatus(epic.getIdentificationNumber());
        }
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        deleteAllSubtasks();
    }

    @Override
    public Task getTask(int id) {
       historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getIdentificationNumber())) {
            tasks.put(task.getIdentificationNumber(), task);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (!epics.containsKey(epic.getIdentificationNumber())) {
            return;
        }
        epics.get(epic.getIdentificationNumber()).setName(epic.getName());
        epics.get(epic.getIdentificationNumber()).setDescription(epic.getDescription());
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (!subtasks.containsKey(subtask.getIdentificationNumber()) || !epics.containsKey(subtask.getEpic())) {
            return;
        }
        subtasks.put(subtask.getIdentificationNumber(), subtask);
        updateEpicStatus(subtasks.get(subtask.getIdentificationNumber()).getEpic());
    }

    @Override
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

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        if (epics.containsKey(id)) {
            for (int subtaskId : epics.get(id).getSubtasks()) {
                subtasks.remove(subtaskId);
            }
            epics.remove(id);
        }
    }

    @Override
    public void deleteSubtask(int id) {
        if (subtasks.containsKey(id)) {
            int epicId = subtasks.get(id).getEpic();
            epics.get(epicId).getSubtasks().remove((Integer) id);
            subtasks.remove(id);
            updateEpicStatus(epicId);

        }
    }

    @Override
    public List<Task> getHistory(){
        return historyManager.getHistory();

    }
}
