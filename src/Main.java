import ru.yandex.practicum.Managers.Managers;
import ru.yandex.practicum.Managers.TaskManager;
import ru.yandex.practicum.Tasks.Epic;
import ru.yandex.practicum.Tasks.Status;
import ru.yandex.practicum.Tasks.Subtask;
import ru.yandex.practicum.Tasks.Task;

public class Main {
    public static void main(String[] args) {

        TaskManager manager = Managers.getDefault();





            manager.addEpic(new Epic("Пр " , " о ",0, Status.NEW));
        manager.addSubtask(new Subtask("Пр " , " ок ",0, Status.NEW, 1));
        manager.addSubtask(new Subtask("Прове " , " о ",0, Status.NEW, 1));
        manager.addSubtask(new Subtask("Про" , " ей ",0, Status.NEW, 1));
        manager.addEpic(new Epic("а " , " ой ",0, Status.NEW));
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
        manager.deleteEpic(1);


        System.out.println(manager.getHistory());







        }





    }
