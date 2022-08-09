import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task{

    private HashMap<Integer,Subtask> subtasks;
    public Epic(String name, String description,  String status) {
        super(name, description,  status);
        subtasks = new HashMap<>();
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }
    public void addSubtask(int id, Subtask subtask){
        subtasks.put(id, subtask);
    }

    public void deleteAllSubtasks(){
        subtasks.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", identificationNumber=" + getIdentificationNumber() +
                ", status='" + getStatus() + '\'' + '}';
    }
}
