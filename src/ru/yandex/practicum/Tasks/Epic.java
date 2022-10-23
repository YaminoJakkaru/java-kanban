package ru.yandex.practicum.Tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    private final ArrayList<Integer> subtasks;
    private LocalDateTime endTime;


    public Epic( int identificationNumber,String name, Status status, String description,String startTime,long duration) {
        super( identificationNumber, name,status, description, startTime, duration);
        subtasks = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(int id) {
        subtasks.add(id);
    }


    public void deleteAllSubtasks() {
        subtasks.clear();
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public LocalDateTime getEndTime(){
        return endTime;
    }
    @Override
    public Type getType(){return Type.EPIC;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(getName(), epic.getName()) && Objects.equals(getDescription(), epic.getDescription()) &&
                Objects.equals(getIdentificationNumber(), epic.getIdentificationNumber()) && Objects.equals(getStatus(), epic.getStatus()) &&
                Objects.equals(subtasks, epic.subtasks)&&
                Objects.equals(getStartTime(), epic.getStartTime())&&Objects.equals(getDuration(), epic.getDuration());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getIdentificationNumber(), getStatus(), getSubtasks(),getStartTime(),getDuration());
    }

    @Override
    public String toString() {
        return  getIdentificationNumber()+","+getType()+"," + getName() + ","+getStatus()+
                "," + getDescription()+","+getStartTime().format(formatter)+","+getDuration();
    }
}
