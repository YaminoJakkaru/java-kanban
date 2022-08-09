import java.util.HashMap;
import java.util.Objects;

public class Tasks {
   private   HashMap<Integer,Task> tasks;

    public Tasks() {
        tasks = new HashMap<>();
    }

    public void addTask(int id, Task task){
    tasks.put(id,task);
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public void deleteAllTasks(){
        tasks.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tasks tasks1 = (Tasks) o;
        return Objects.equals(tasks, tasks1.tasks);
    }


}
