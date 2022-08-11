package ru.yandex.practicum.Manager.Tasks;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    private ArrayList<Integer> subtasks;
    public Epic(String name, String description,  String status) {
        super(name, description,  status);
        subtasks = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }
    public void addSubtask( int id){
        subtasks.add(id);
    }


    public void deleteAllSubtasks(){
        subtasks.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(getName(), epic.getName())&&Objects.equals(getDescription(), epic.getDescription()) &&
                Objects.equals(getIdentificationNumber(), epic.getIdentificationNumber())&&Objects.equals(getStatus(), epic.getStatus())&&
                Objects.equals(subtasks, epic.subtasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getIdentificationNumber(), getStatus(), subtasks);
    }

    @Override
    public String toString() {
        return "ru.yandex.practicum.Manager.Tasks.Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", identificationNumber=" + getIdentificationNumber() +
                ", status='" + getStatus() + '\'' + '}';
    }
}
