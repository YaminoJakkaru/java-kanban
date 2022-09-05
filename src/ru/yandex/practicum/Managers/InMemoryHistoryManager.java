package ru.yandex.practicum.Managers;

import ru.yandex.practicum.Tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {

    private final HashMap<Integer, Node<Task>> history;
    private static final int MAX_COUNT_ELEMENTS = 11;
    public Node<Task> head;
    public Node<Task> tail;

    public InMemoryHistoryManager() {
        history = new HashMap<>();
        head = new Node<>(null);
        tail = new Node<>(null);
        head.next = tail;
        tail.prev = head;
    }

    public void linkLast(Task task) {
        Node<Task> newNode = new Node<>(task);
        if (history.containsKey(task.getIdentificationNumber())) {
            removeNode(history.get(task.getIdentificationNumber()));
        }
        if (history.size() == 0) {
            newNode.prev = head;
            newNode.next = tail;
            head.next = newNode;
            tail.prev = newNode;
            history.put(task.getIdentificationNumber(), newNode);
            return;
        }
        newNode.prev = tail.prev;
        newNode.next = tail;
        tail.prev = newNode;
        newNode.prev.next = newNode;
        history.put(task.getIdentificationNumber(), newNode);
    }

    public void removeNode(Node<Task> node) {

        if (history.size() == 0 || node == null) {
            return;
        }

        node.next.prev = node.prev;
        node.prev.next = node.next;
    }

    @Override
    public void add(Task task) {
        if (history.size() == MAX_COUNT_ELEMENTS) {
            remove(head.next.data.getIdentificationNumber());

        }
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        removeNode(history.get(id));
        history.remove(id);
    }

    @Override
    public ArrayList<Task> getHistory() {

        ArrayList<Task> tasks = new ArrayList<>();
        Node<Task> currentNode = head.next;

        while (currentNode != tail) {

            tasks.add(currentNode.data);
            currentNode = currentNode.next;

        }

        return tasks;
    }


}
