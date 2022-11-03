package ru.yandex.practicum.Managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.yandex.practicum.Tasks.Epic;
import ru.yandex.practicum.Tasks.Subtask;
import ru.yandex.practicum.Tasks.Task;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static ru.yandex.practicum.Managers.FileBackedTaskManager.historyFromString;

public class Managers {
    public static HTTPTaskManager getDefault(String url) throws MalformedURLException {
        return new HTTPTaskManager(url);

    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        final FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

        try {
            int maxId = 0;
            String text = Files.readString(Path.of(file.getPath()));
            String[] lines = text.split(System.lineSeparator());

            for (int i = 1; i < lines.length - 2; i++) {
                Task task = taskManager.fromString(lines[i]);
                maxId = Math.max(maxId, task.getIdentificationNumber());

                switch (task.getType()) {
                    case TASK:
                        taskManager.tasks.put(task.getIdentificationNumber(), task);
                        break;
                    case EPIC:
                        taskManager.epics.put(task.getIdentificationNumber(), (Epic) task);
                        break;
                    case SUBTASK:
                        taskManager.subtasks.put(task.getIdentificationNumber(), (Subtask) task);
                        taskManager.epics.get(((Subtask) task).getEpic()).addSubtask(task.getIdentificationNumber());
                        break;
                }
                taskManager.identificationNumber = maxId;


            }
            if(lines.length >0) {
                for (int id : historyFromString(lines[lines.length - 1])) {
                    if (taskManager.tasks.containsKey(id)) {
                        taskManager.historyManager.add(taskManager.getTask(id));
                    } else if (taskManager.epics.containsKey(id)) {
                        taskManager.historyManager.add(taskManager.getEpic(id));
                    } else if (taskManager.subtasks.containsKey(id)) {
                        taskManager.historyManager.add(taskManager.getSubtask(id));
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
        return taskManager;
    }

}
