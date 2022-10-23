package ru.yandex.practicum.Tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private int identificationNumber;
    private Status status;
    private LocalDateTime startTime;
    private long duration;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.HH:mm");



    public Task( int identificationNumber,String name, Status status, String description,String startTime,long duration) {
        this.name = name;
        this.description = description;
        this.identificationNumber = identificationNumber;
        this.status = status;
        this.startTime=LocalDateTime.parse(startTime, formatter);
        this.duration=duration;

    }

    public LocalDateTime getEndTime(){
        return startTime.plusMinutes(duration);
    }

    public long getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(long duration) {
        this.duration = duration;
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
    public Type getType(){return Type.TASK;}

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
        return identificationNumber == task.identificationNumber && Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) && Objects.equals(status, task.status)&&
                Objects.equals(getStartTime(), task.getStartTime())&&Objects.equals(getDuration(), task.getDuration());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(),getDescription(), getIdentificationNumber(), getStatus(),getStartTime(),getDuration());
    }

    @Override
    public String toString() {
        return  getIdentificationNumber()+","+getType()+"," + getName() + ","+getStatus()+
                "," + getDescription()+","+getStartTime().format(formatter)+","+getDuration();

    }
}
