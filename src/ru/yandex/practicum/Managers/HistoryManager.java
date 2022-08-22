package ru.yandex.practicum.Managers;

import ru.yandex.practicum.Tasks.Task;

import java.util.List;

public interface HistoryManager {
    public void add(Task task);
    public List<Task> getHistory();
}
