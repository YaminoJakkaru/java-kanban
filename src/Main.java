import ru.yandex.practicum.Managers.FileBackedTaskManager;
import ru.yandex.practicum.Managers.Managers;
import ru.yandex.practicum.Tasks.Epic;
import ru.yandex.practicum.Tasks.Status;
import ru.yandex.practicum.Tasks.Subtask;
import ru.yandex.practicum.Tasks.Task;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        FileBackedTaskManager manager = Managers.loadFromFile(new File("resources/resources"));
        manager.addEpic(new Epic(0, " о ", Status.NEW, "Status.NEW"));
        manager.addSubtask(new Subtask(0, " ок ", Status.NEW, "Status.NEW", 1));
        manager.addSubtask(new Subtask(0, " о ", Status.NEW, "Status.NEW", 1));
        manager.addSubtask(new Subtask(0, " ей ", Status.NEW, "Status.NEW", 1));
        manager.addEpic(new Epic(0, " ой ", Status.NEW, "Status.NEW"));
        manager.addTask(new Task(0, " о ", Status.NEW, "Status.NEW"));
        System.out.println(manager.getTasks());
        System.out.println(manager.getEpic(1));
        System.out.println(manager.getEpic(5));
        System.out.println(manager.getEpic(1));
        System.out.println(manager.getHistory());
        System.out.println(manager.getSubtask(3));
        System.out.println(manager.getHistory());
        System.out.println(manager.getSubtask(3));
        System.out.println(manager.getHistory());
        System.out.println(manager.getSubtask(4));
        System.out.println(manager.getEpic(1));
        System.out.println(manager.getHistory());
        FileBackedTaskManager manager2 = Managers.loadFromFile(new File("resources/resources"));
        manager2.addTask(new Task(0, " о ", Status.IN_PROGRESS, "Stat"));
        System.out.println(manager2.getTask(7));
        System.out.println(manager2.getEpic(1));
        System.out.println(manager2.getEpic(5));
        System.out.println(manager2.getEpic(1));
        System.out.println(manager2.getHistory());
        System.out.println(manager2.getTask(7));
        System.out.println(manager2.getSubtask(3));
        System.out.println(manager2.getHistory());
        System.out.println(manager2.getSubtask(3));
        System.out.println(manager2.getHistory());
        System.out.println(manager2.getSubtask(4));
        System.out.println(manager2.getEpic(1));
        System.out.println(manager2.getHistory());




    }


}
