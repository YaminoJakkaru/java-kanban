package ru.yandex.practicum.Manager.Tasks;

import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, String status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public int getEpic() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return Objects.equals(getName(), subtask.getName()) && Objects.equals(getDescription(), subtask.getDescription()) &&
                Objects.equals(getIdentificationNumber(), subtask.getIdentificationNumber()) && Objects.equals(getStatus(), subtask.getStatus()) &&
                Objects.equals(epicId, subtask.epicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getIdentificationNumber(), getStatus(), epicId);
    }

    @Override
    public String toString() {
        return "ru.yandex.practicum.Manager.Tasks.Subtask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", identificationNumber=" + getIdentificationNumber() +
                ", status='" + getStatus() + '\'' +
                ", epicId='" + epicId +
                '}';
    }

}
