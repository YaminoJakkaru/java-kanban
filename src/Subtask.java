public class Subtask extends Task{
private int epicId=0;

    public Subtask(String name, String description,  String status ) {
        super(name, description,  status);

    }

    public int getEpic() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", identificationNumber=" + getIdentificationNumber() +
                ", status='" + getStatus() + '\'' +
                ", epicId='" + epicId +
                '}';
    }

}
