import ru.yandex.practicum.Managers.FileBackedTasksManager;
import ru.yandex.practicum.Managers.InMemoryHistoryManager;
import ru.yandex.practicum.Managers.Managers;
import ru.yandex.practicum.Managers.TaskManager;
import ru.yandex.practicum.Tasks.Epic;
import ru.yandex.practicum.Tasks.Status;
import ru.yandex.practicum.Tasks.Subtask;
import ru.yandex.practicum.Tasks.Task;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        FileBackedTasksManager manager =  new FileBackedTasksManager(new File("D:\\Users\\Vanya\\idea.project\\java-kanban\\resources\\resources"));
        manager.addEpic(new Epic(0 , " о ", Status.NEW, "Status.NEW"));
        manager.addSubtask(new Subtask(0 , " ок ",Status.NEW, "Status.NEW", 1));
        manager.addSubtask(new Subtask(0 , " о ",Status.NEW, "Status.NEW", 1));
        manager.addSubtask(new Subtask(0 , " ей ",Status.NEW, "Status.NEW", 1));
        manager.addEpic(new Epic(0, " ой ",Status.NEW, "Status.NEW"));
        manager.addTask(new Task(0 , " о ", Status.NEW, "Status.NEW"));
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
        FileBackedTasksManager manager2 =  new FileBackedTasksManager(new File("D:\\Users\\Vanya\\idea.project\\java-kanban\\resources\\resources"));
        manager2.addTask(new Task(0 , " о ", Status.IN_PROGRESS, "Stat"));
        System.out.println(manager2.getTask(7));
        System.out.println(manager2.getHistory());






        }





    }
