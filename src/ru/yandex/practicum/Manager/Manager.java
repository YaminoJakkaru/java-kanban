package ru.yandex.practicum.Manager;

import ru.yandex.practicum.Manager.Tasks.*;

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

    public String getAll() {
        return getTasks() + "\n" + getEpics();
    }

    public void deleteAll() {
        deleteAllEpics();
        deleteAllTasks();
    }

    public void deleteById(int id) {
        deleteEpic(id);
        deleteSubtask(id);
        deleteTask(id);
    }

    public String getInfoById(int id) {
        String info = null;
        if (tasks.containsKey(id))
            info = getTask(id);
        else if (epics.containsKey(id))
            info = getEpic(id);
        else
            info = getSubtask(id);
        return info;
    }


    public void addTask(Task task) {
        task.setIdentificationNumber(++IdentificationNumber);
        tasks.put(IdentificationNumber, task);

    }

    public void addSubtask(Subtask subtask) {
        subtask.setIdentificationNumber(++IdentificationNumber);
        epics.get(subtask.getEpic()).addSubtask(IdentificationNumber);
        subtasks.put(IdentificationNumber, subtask);
    }

    public void addEpic(Epic epic) {
        epic.setIdentificationNumber(++IdentificationNumber);
        epics.put(IdentificationNumber, epic);
        if (epic.getSubtasks().size() != 0) {
            updateEpicStatus(epic.getIdentificationNumber());
        }
    }

    public String getTasks() {
        String info = "";

        for (Task task : tasks.values()) {
            info = info + task.toString() + "\n";
        }
        return info;
    }

    public String getSubtasks() {
        String info = "";

        for (Subtask subtask : subtasks.values()) {
            info = info + subtask.toString() + "\n";
        }
        return info;
    }

    public String getEpicSubtasks(int id) {
        String info = "";

        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpic() == id)
                info = info + subtask.toString() + "\n";
        }
        return info;
    }

    public String getEpics() {
        String info = "";

        for (Epic epic : epics.values()) {
            info = info + epic.toString() + "\n" + getEpicSubtasks(epic.getIdentificationNumber()) + "\n";
        }
        return info;
    }


    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.deleteAllSubtasks();
        }
    }

    public void deleteAllEpics() {
        epics.clear();
    }

    public String getTask(int id) {
        return tasks.get(id).toString() + "\n";
    }

    public String getEpic(int id) {
        return epics.get(id).toString() + "\n" + getEpicSubtasks(id) + "\n";
    }

    public String getSubtask(int id) {

        return subtasks.get(id).toString() + "\n";

    }

    public void updateTask(int id, Task task) {
        if (tasks.containsKey(id)) {
            if (!tasks.get(id).getName().equals(task.getName())) {
                tasks.get(id).setName(task.getName());
            }
            if (!tasks.get(id).getDescription().equals(task.getDescription())) {
                tasks.get(id).setDescription(task.getDescription());
            }
            if (!tasks.get(id).getStatus().equals(task.getStatus())) {
                tasks.get(id).setStatus(task.getStatus());
            }
        }
    }

    public void updateEpic(int id, Epic epic) {
        if (epics.containsKey(id)) {
            if (!epics.get(id).getName().equals(epic.getName())) {
                epics.get(id).setName(epic.getName());
            }
            if (!epics.get(id).getDescription().equals(epic.getDescription())) {
                epics.get(id).setDescription(epic.getDescription());
            }
            if (!epics.get(id).getStatus().equals(epic.getStatus())) {
                epics.get(id).setStatus(epic.getStatus());
            }
            updateEpicStatus(id);
        }

    }

    public void updateSubtask(int id, Subtask subtask) {

        if (subtasks.containsKey(id)) {
            if (!subtasks.get(id).getName().equals(subtask.getName())) {
                subtasks.get(id).setName(subtask.getName());
            }
            if (!subtasks.get(id).getDescription().equals(subtask.getDescription())) {
                subtasks.get(id).setDescription(subtask.getDescription());
            }
            if (!subtasks.get(id).getStatus().equals(subtask.getStatus())) {
                subtasks.get(id).setStatus(subtask.getStatus());
            }
            updateEpicStatus(subtasks.get(id).getEpic());


        }

    }

    public void updateEpicStatus(int id) {
        int doneSubtasks = 0;

        for (Subtask subtask : subtasks.values()) {


            if (subtask.getEpic() == id) {


                if (!subtask.getStatus().equals("NEW")) {
                    epics.get(id).setStatus("IN_PROGRESS");
                }
                if (subtask.getStatus().equals("DONE")) {
                    doneSubtasks++;
                }
            }
        }

        if (doneSubtasks == epics.get(id).getSubtasks().size()) {
            epics.get(id).setStatus("DONE");
        }
    }

    public void deleteTask(int id) {
        if (tasks.containsKey(id))
            tasks.remove(id);
    }

    public void deleteEpic(int id) {
        if (epics.containsKey(id))
            epics.remove(id);

    }

    public void deleteSubtask(int id) {

        if (subtasks.containsKey(id)) {
            int epicId = subtasks.get(id).getEpic();
            epics.get(subtasks.get(id).getEpic()).getSubtasks().remove((Integer) id);
            subtasks.remove(id);
            updateEpicStatus(epicId);

        }

    }


}
