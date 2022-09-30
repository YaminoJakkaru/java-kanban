package ru.yandex.practicum.Managers;

import ru.yandex.practicum.Tasks.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class FileBackedTasksManager extends InMemoryTaskManager {


    private final File file;

    public FileBackedTasksManager(File file) throws IOException {
        super();
        this.file = file;
        int maxId=0;
            for (Task task: loadFromFile(file)){
                if(maxId<task.getIdentificationNumber()){
                    maxId=task.getIdentificationNumber();
                }


                    switch (task.getType()) {
                        case TASK:
                            tasks.put(task.getIdentificationNumber(), task);
                            break;
                        case EPIC:
                            epics.put(task.getIdentificationNumber(), (Epic) task);
                            break;
                        case SUBTASK:
                            subtasks.put(task.getIdentificationNumber(), (Subtask) task);
                            epics.get(((Subtask) task).getEpic()).addSubtask(task.getIdentificationNumber());
                            break;
                    }
                }
        IdentificationNumber = maxId;
            try {


                for (int id : historyFromString(Files.readString(Path.of(file.getPath())))) {
                    if (tasks.containsKey(id)) {
                        historyManager.add(getTask(id));
                    } else if (epics.containsKey(id)) {
                        historyManager.add(getEpic(id));
                    } else {
                        historyManager.add(getSubtask(id));
                    }
                }
            }catch (NullPointerException ignored){

            }

    }

    private void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("id,type,name,status,description,epic");
            bw.newLine();

            for(Task value: super.getTasks()) {
                bw.write(value.toString());
                bw.newLine();
            }

            for(Task value: super.getEpics()) {
                bw.write(value.toString());
                bw.newLine();
            }

            for(Task value: super.getSubtasks()) {
                bw.write(value.toString());
                bw.newLine();
            }
            bw.newLine();
            Collection<Task> valuesH=getHistory();
            StringBuilder builder=new StringBuilder();
            for(Task value: valuesH) {
                builder.append(value.getIdentificationNumber()).append(",");
            }
            bw.write(builder.toString());
        } catch (IOException e) {
          //  throw ManagerSaveException(e);

        }
    }

    public static Task  fromString(String value)  {
            String[] lineContents = value.split(",");

        switch (lineContents[1]) {
            case "TASK":

            return new Task(Integer.parseInt(lineContents[0]), lineContents[2],
                    Status.valueOf(lineContents[3]), lineContents[4]);


            case "EPIC":

                return new Epic(Integer.parseInt(lineContents[0]), lineContents[2],
                        Status.valueOf(lineContents[3]), lineContents[4]);

            case "SUBTASK":


                return new Subtask(Integer.parseInt(lineContents[0]), lineContents[2],
                        Status.valueOf(lineContents[3]), lineContents[4],
                        Integer.parseInt(lineContents[5]));

        }
        return  null;
            }
    public static String historyToString(HistoryManager manager){

        StringBuilder builder=new StringBuilder();
        for(Task value: manager.getHistory()) {
            builder.append(value.getIdentificationNumber()).append(",");
        }
        return builder.toString();
    }

    public static List<Integer> historyFromString(String value){
        try {


            String[] lines = value.split(System.lineSeparator());
            List<Integer> identificationNumbers = new ArrayList<>();
            String[] lineContents = lines[lines.length - 1].split(",");
            for (String content : lineContents) {
                identificationNumbers.add(Integer.parseInt(content));
            }
            return identificationNumbers;
        }catch (NumberFormatException e){
          return null;
        }


    }

            public static  List<Task> loadFromFile(File file) throws IOException {

                List< Task> tasks = new ArrayList<>();


                String text = Files.readString(Path.of(file.getPath()));
                String[] lines = text.split(System.lineSeparator());
                for (int i=1;i<lines.length-2;i++) {
                    Task lineContents =fromString(lines[i]);
                            tasks.add(lineContents);

                }

                return tasks;
            }




    @Override
    public ArrayList<Task> getTasks() {
        return super.getTasks();
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        return super.getSubtasks();
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(int id) {
        return super.getEpicSubtasks(id);
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return super.getEpics();
    }

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }

    @Override
    public int addTask(Task task) {

        int t= super.addTask(task);
            save();
            return  t;
    }

    @Override
    public int addSubtask(Subtask subtask) {
        int s= super.addSubtask(subtask);
        save();
        return s;
    }

    @Override
    public int addEpic(Epic epic) {
        int e= super.addEpic(epic);
        save();
        return e;
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public Task getTask(int id) {
        Task t= super.getTask(id);
        save();
        return t;
    }

    @Override
    public Epic getEpic(int id) {
        Epic e= super.getEpic(id);
        save();
        return e;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask s= super.getSubtask(id);
        save();
        return s;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }


}
