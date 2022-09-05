package ru.yandex.practicum.Managers;

import java.util.Objects;

class Node<Task> {

    public Task data;
    public Node<Task> next;
    public Node<Task> prev;

    public Node(Task task) {
        this.data = task;
        this.next = null;
        this.prev = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(data, node.data) && Objects.equals(next, node.next) && Objects.equals(prev, node.prev);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, next, prev);
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                '}';
    }
}
