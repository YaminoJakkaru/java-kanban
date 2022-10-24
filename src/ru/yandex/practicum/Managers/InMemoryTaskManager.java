package ru.yandex.practicum.Managers;

import ru.yandex.practicum.Tasks.Epic;
import ru.yandex.practicum.Tasks.Status;
import ru.yandex.practicum.Tasks.Subtask;
import ru.yandex.practicum.Tasks.Task;

import java.time.LocalDateTime;
import java.util.*;


public class InMemoryTaskManager implements TaskManager {
    protected final HistoryManager historyManager;
    protected final HashMap<Integer, Epic> epics;
    protected final HashMap<Integer, Task> tasks;
    protected final HashMap<Integer, Subtask> subtasks;

    protected int identificationNumber = 0;

    public InMemoryTaskManager() {
        historyManager = Managers.getDefaultHistory();
        epics = new HashMap<>();
        tasks = new HashMap<>();
        subtasks = new HashMap<>();

    }


    private int getNewId() {
        return ++identificationNumber;
    }

    @Override
    public int addTask(Task task) {
        if(checkIntersections(task)) {
            return task.getIdentificationNumber();
        }
            task.setIdentificationNumber(getNewId());
            tasks.put(task.getIdentificationNumber(), task);

        return task.getIdentificationNumber();
    }

    @Override
    public int addSubtask(Subtask subtask) {
        if(checkIntersections(subtask)) {
            return subtask.getIdentificationNumber();
        }
        subtask.setIdentificationNumber(getNewId());
        epics.get(subtask.getEpic()).addSubtask(subtask.getIdentificationNumber());
        subtasks.put(subtask.getIdentificationNumber(), subtask);
        updateEpicStatus(subtask.getEpic());
        updateEpicTime(subtask.getEpic());
        return subtask.getIdentificationNumber();
    }

    @Override
    public int addEpic(Epic epic) {
        if(checkIntersections(epic)) {
            return epic.getIdentificationNumber();
        }
        epic.setIdentificationNumber(getNewId());
        epics.put(epic.getIdentificationNumber(), epic);
        return epic.getIdentificationNumber();
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(int id) {

        ArrayList<Subtask> subtaskArrayList = new ArrayList<>();
        if (epics.containsKey(id)) {
            for (int subtaskId : epics.get(id).getSubtasks()) {
                subtaskArrayList.add(subtasks.get(subtaskId));
            }
        }
        return subtaskArrayList;
    }

    @Override
    public ArrayList<Task> getPrioritizedTasks(){
        Comparator<Task> comparator = Comparator.comparingInt(o -> o.getStartTime().getYear()+
                o.getStartTime().getDayOfYear()+o.getStartTime().getHour()+o.getStartTime().getMinute());
        TreeSet<Task> prioritizedTasks = new TreeSet<>(comparator);
        prioritizedTasks.addAll(getSubtasks());
        prioritizedTasks.addAll(getTasks());

        return new ArrayList<Task>(prioritizedTasks) ;
    }

    public boolean checkIntersections(Task task){
        for(Task value:getPrioritizedTasks()){
            if(task.getStartTime().isBefore(value.getEndTime())&&task.getEndTime().isAfter(value.getStartTime())){
                return  true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteAllTasks() {
        for (Task task : tasks.values()) {
            historyManager.remove(task.getIdentificationNumber());
        }
        tasks.clear();

    }

    @Override
    public void deleteAllSubtasks() {
        for (Subtask subtask : subtasks.values()) {
            historyManager.remove(subtask.getIdentificationNumber());
        }
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.deleteAllSubtasks();
            updateEpicStatus(epic.getIdentificationNumber());


        }
    }

    @Override
    public void deleteAllEpics() {
        for (Epic epic : epics.values()) {
            historyManager.remove(epic.getIdentificationNumber());
        }

        epics.clear();
        deleteAllSubtasks();

    }

    @Override
    public Task getTask(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public void updateTask(Task task) {
        if(checkIntersections(task)) {
            return ;
        }
        if (tasks.containsKey(task.getIdentificationNumber())) {
            tasks.put(task.getIdentificationNumber(), task);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (!epics.containsKey(epic.getIdentificationNumber())) {
            return;
        }
        if(checkIntersections(epic)) {
            return ;
        }
        epics.get(epic.getIdentificationNumber()).setName(epic.getName());
        epics.get(epic.getIdentificationNumber()).setDescription(epic.getDescription());
        updateEpicTime(epic.getIdentificationNumber());
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if(checkIntersections(subtask)) {
            return ;
        }
        if (!subtasks.containsKey(subtask.getIdentificationNumber()) || !epics.containsKey(subtask.getEpic())) {
            return;
        }
        subtasks.put(subtask.getIdentificationNumber(), subtask);
        updateEpicStatus(subtasks.get(subtask.getIdentificationNumber()).getEpic());
        updateEpicTime(subtasks.get(subtask.getIdentificationNumber()).getEpic());
    }


    private void updateEpicStatus(int id) {
        int doneSubtasks = 0;

        for (int subtaskId : epics.get(id).getSubtasks()) {
            if (subtasks.get(subtaskId).getStatus().equals(Status.IN_PROGRESS)) {
                epics.get(id).setStatus(Status.IN_PROGRESS);
                return;
            }
            if (subtasks.get(subtaskId).getStatus().equals(Status.DONE)) {
                doneSubtasks++;
            }
        }
        if (doneSubtasks == epics.get(id).getSubtasks().size() && doneSubtasks != 0) {
            epics.get(id).setStatus(Status.DONE);
            return;
        }
        if (doneSubtasks != 0) {
            epics.get(id).setStatus(Status.IN_PROGRESS);
        }
    }

    private void updateEpicTime(int id){

        LocalDateTime epicStartTime=LocalDateTime.of(3000, 1, 1, 0, 0, 0, 0);
        long epicDuration=0;
        LocalDateTime epicEndTime=LocalDateTime.of(2000, 1, 1, 0, 0, 0, 0);
        if(epics.get(id).getSubtasks().isEmpty()){
            return;
        }

        for (int subtaskId : epics.get(id).getSubtasks()) {
            if (epicEndTime.isBefore(subtasks.get(subtaskId).getEndTime())) {
                epicEndTime=subtasks.get(subtaskId).getEndTime();
            }
            if(epicStartTime.isAfter(subtasks.get(subtaskId).getStartTime())){
                epicStartTime=subtasks.get(subtaskId).getStartTime();
            }
            epicDuration+=subtasks.get(subtaskId).getDuration();
        }
        epics.get(id).setEndTime(epicEndTime);
        epics.get(id).setStartTime(epicStartTime);
        epics.get(id).setDuration(epicDuration);
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        if (epics.containsKey(id)) {
            for (int subtaskId : epics.get(id).getSubtasks()) {
                historyManager.remove(subtaskId);
                subtasks.remove(subtaskId);
            }
            epics.remove(id);
            historyManager.remove(id);
        }
    }

    @Override
    public void deleteSubtask(int id) {
        if (subtasks.containsKey(id)) {
            int epicId = subtasks.get(id).getEpic();
            epics.get(epicId).getSubtasks().remove((Integer) id);
            subtasks.remove(id);
            updateEpicStatus(epicId);
            updateEpicTime(epicId);
            historyManager.remove(id);

        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();

    }
}
