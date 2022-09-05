package ru.yandex.practicum.Managers;

import ru.yandex.practicum.Tasks.Task;

import java.util.List;

public interface HistoryManager {
    public void add(Task task);
    public void remove(int id);
    public List<Task> getHistory();
}
