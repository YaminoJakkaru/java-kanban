package ru.yandex.practicum.Tasks;

import java.util.Objects;

public class Subtask extends Task {
    private int epicId;


    public Subtask(int identificationNumber,String name, Status status, String description, int epicId, String startTime,long duration) {
        super(identificationNumber, name,status, description, startTime, duration);
        this.epicId = epicId;
    }

    public int getEpic() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
    @Override
    public Type getType(){return Type.SUBTASK;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return Objects.equals(getName(), subtask.getName()) && Objects.equals(getDescription(), subtask.getDescription()) &&
                Objects.equals(getIdentificationNumber(), subtask.getIdentificationNumber()) && Objects.equals(getStatus(), subtask.getStatus()) &&
                Objects.equals(epicId, subtask.epicId)&&
                Objects.equals(getStartTime(), subtask.getStartTime())&&Objects.equals(getDuration(), subtask.getDuration());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getIdentificationNumber(), getStatus(), getEpic(),getStartTime(),getDuration());
    }

    @Override
    public String toString() {
        return  getIdentificationNumber()+","+getType()+"," + getName() + ","+getStatus()+
                "," + getDescription()+","+getStartTime().format(formatter)+","+getDuration()+","+getEpic();
    }

}
