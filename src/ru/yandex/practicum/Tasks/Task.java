package ru.yandex.practicum.Tasks;

import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private int identificationNumber;
    private Status status;

    public Task(String name, String description, int identificationNumber, Status status) {
        this.name = name;
        this.description = description;
        this.identificationNumber = identificationNumber;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getIdentificationNumber() {
        return identificationNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIdentificationNumber(int identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return identificationNumber == task.identificationNumber && Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, identificationNumber, status);
    }

    @Override
    public String toString() {
        return "ru.yandex.practicum.Manager.InMemoryTaskManager.Tasks.Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", identificationNumber=" + identificationNumber +
                ", status='" + status + '\'' +
                '}';
    }
}
