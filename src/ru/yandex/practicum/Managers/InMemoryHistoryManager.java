package ru.yandex.practicum.Managers;

import ru.yandex.practicum.Tasks.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class InMemoryHistoryManager implements HistoryManager {
    private final LinkedList<Task> history;
    private static final int MAX_COUNT_ELEMENTS = 10;

    public InMemoryHistoryManager() {
        history = new LinkedList<>();
    }


    @Override
    public void add(Task task) {
        if (history.size() == MAX_COUNT_ELEMENTS) {
            history.removeFirst();
        }
        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
}
