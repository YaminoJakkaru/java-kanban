package ru.yandex.practicum.Managers;

import ru.yandex.practicum.Tasks.Epic;
import ru.yandex.practicum.Tasks.Subtask;
import ru.yandex.practicum.Tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    public int getNewId();

    public void addTask(Task task);

    public void addSubtask(Subtask subtask) ;

    public void addEpic(Epic epic) ;

    public ArrayList<Task> getTasks();

    public ArrayList<Subtask> getSubtasks();

    public ArrayList<Subtask> getEpicSubtasks(int id) ;

    public ArrayList<Epic> getEpics() ;

    public void deleteAllTasks();

    public void deleteAllSubtasks();

    public void deleteAllEpics();

    public Task getTask(int id) ;

    public Epic getEpic(int id) ;

    public Subtask getSubtask(int id) ;

    public void updateTask(Task task) ;

    public void updateEpic(Epic epic) ;

    public void updateSubtask(Subtask subtask);

    public void updateEpicStatus(int id) ;

    public void deleteTask(int id) ;

    public void deleteEpic(int id) ;

    public void deleteSubtask(int id) ;

    List<Task> getHistory();
}

