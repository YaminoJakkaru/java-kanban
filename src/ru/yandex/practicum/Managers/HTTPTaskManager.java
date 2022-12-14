package ru.yandex.practicum.Managers;

import ru.yandex.practicum.Servers.KVTaskClient;
import ru.yandex.practicum.Tasks.Epic;
import ru.yandex.practicum.Tasks.Subtask;
import ru.yandex.practicum.Tasks.Task;
import ru.yandex.practicum.Tasks.Type;

public class HTTPTaskManager extends FileBackedTaskManager {

    private final KVTaskClient taskClient;

    public HTTPTaskManager(String url) {
        taskClient = new KVTaskClient(url);
        load();
    }

    @Override
    public void save() {
        StringBuilder builder = new StringBuilder();

        for (Task data : super.getTasks()) {
            builder.append(data.toString()).append(System.lineSeparator());
        }
        if (builder.length() != 0) {
            taskClient.put("task", builder.toString());
        }
        builder.setLength(0);
        for (Epic data : super.getEpics()) {
            builder.append(data.toString()).append(System.lineSeparator());
        }
        if (builder.length() != 0) {
            taskClient.put("epic", builder.toString());
        }
        builder.setLength(0);
        for (Subtask data : super.getSubtasks()) {
            builder.append(data.toString()).append(System.lineSeparator());
        }
        if (builder.length() != 0) {
            taskClient.put("subtask", builder.toString());
        }
        if (historyToString(historyManager).length() != 0) {
            taskClient.put("history", historyToString(historyManager));
        }
    }

    public void load() {
        int maxId = 0;

        for (Type type : Type.values()) {
            String text = taskClient.load(type.getTag());
            String[] lines = text.split(System.lineSeparator());
            for (String line : lines) {
                Task task = fromString(line);
                if (task == null) {
                    continue;
                }
                maxId = Math.max(maxId, task.getIdentificationNumber());
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
        }
        identificationNumber = maxId;
        for (int id : historyFromString(taskClient.load("history"))) {
            if (tasks.containsKey(id)) {
                historyManager.add(getTask(id));
            } else if (epics.containsKey(id)) {
                historyManager.add(getEpic(id));
            } else if (subtasks.containsKey(id)) {
                historyManager.add(getSubtask(id));
            }
        }

    }

}
