package ru.yandex.practicum.Managers;

import ru.yandex.practicum.Tasks.Epic;
import ru.yandex.practicum.Tasks.Subtask;
import ru.yandex.practicum.Tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {


    public int addTask(Task task);

    public int addSubtask(Subtask subtask) ;

    public int addEpic(Epic epic) ;

    public List<Task> getTasks();

    public List<Subtask> getSubtasks();

    public List<Subtask> getEpicSubtasks(int id) ;

    public List<Epic> getEpics() ;

    public void deleteAllTasks();

    public void deleteAllSubtasks();

    public void deleteAllEpics();

    public Task getTask(int id) ;

    public Epic getEpic(int id) ;

    public Subtask getSubtask(int id) ;

    public void updateTask(Task task) ;

    public void updateEpic(Epic epic) ;

    public void updateSubtask(Subtask subtask);
    public boolean checkIntersections(Task task);

    public void deleteTask(int id) ;

    public void deleteEpic(int id) ;

    public void deleteSubtask(int id) ;

    List<Task> getHistory();

    ArrayList<Task> getPrioritizedTasks();
}

