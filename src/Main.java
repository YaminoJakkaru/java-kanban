import ru.yandex.practicum.Managers.FileBackedTaskManager;
import ru.yandex.practicum.Managers.HTTPTaskManager;
import ru.yandex.practicum.Managers.Managers;
import ru.yandex.practicum.Managers.TaskManager;
import ru.yandex.practicum.Servers.HttpTaskServer;
import ru.yandex.practicum.Servers.KVServer;
import ru.yandex.practicum.Tasks.Epic;
import ru.yandex.practicum.Tasks.Status;
import ru.yandex.practicum.Tasks.Subtask;
import ru.yandex.practicum.Tasks.Task;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) throws IOException {
        KVServer kvServer = new KVServer();
        kvServer.start();
        TaskManager manager = Managers.getDefault("http://localhost:8078/");
        Task task1 = new Task(1, " t1 ", Status.IN_PROGRESS, "Stat", "22.02.2010.22:22", 30);
        Task task2 = new Task(1, " t1 ", Status.IN_PROGRESS, "Stat", "22.02.2008.22:22", 30);
        Epic epic3 = new Epic(3, " t1 ", Status.IN_PROGRESS, "Stat", "26.02.2011.22:22", 30);
        Epic epic4 = new Epic(4, " t2 ", Status.IN_PROGRESS, "Stat", "25.02.2012.22:22", 30);
        Subtask subtask5 = new Subtask(5, " t1 ", Status.IN_PROGRESS, "Stat", 4, "25.02.2012.22:27", 3);
        Subtask subtask6 = new Subtask(6, " t2 ", Status.IN_PROGRESS, "Stat", 4, "25.02.2012.10:40", 30);
        manager.addTask(task1);
        System.out.println(manager.getTask(1));
        manager.addTask(task2);
        manager.addEpic(epic3);
        manager.addEpic(epic4);
        manager.addSubtask(subtask5);
        manager.addSubtask(subtask6);
        System.out.println(manager.getTask(1));
        System.out.println(manager.getHistory());
        TaskManager manager2 = Managers.getDefault("http://localhost:8078/");
        TaskManager emptyTaskManager = Managers.getDefault("http://localhost:8078/");
        System.out.println(manager2.getEpics());
        System.out.println(manager2.getSubtask(5));
        System.out.println(manager2.getHistory());
        kvServer.stop();


    }


}
