import ru.yandex.practicum.Manager.Manager;
import ru.yandex.practicum.Manager.Tasks.Epic;
import ru.yandex.practicum.Manager.Tasks.Status;
import ru.yandex.practicum.Manager.Tasks.Subtask;
import ru.yandex.practicum.Manager.Tasks.Task;

public class Main {
    public static void main(String[] args) {

        Manager manager = new Manager();
        int i = 0;
        while (i < 2) {
            i++;
            Task task = new Task("Проверка таска " + i, " окей ", 0,Status.NEW);
            manager.addTask(task);
        }
        i = 0;

        while (i < 2) {
            i++;
            Epic epic = new Epic("Проверка эпика " + i, " окей ",0, Status.NEW);
            manager.addEpic(epic);
            int j = 0;
            while (j < i) {
                j++;
                Subtask subtask = new Subtask("Проверка субтаска " + i + j, " окей ",0, Status.NEW, epic.getIdentificationNumber());
                manager.addSubtask(subtask);
            }

        }


        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getTask(1));
        System.out.println(manager.getSubtask(4));
        System.out.println(manager.getEpic(5));
        Subtask subtask1 = new Subtask("Проверка обновления субтаска", " окей ",7, Status.DONE, 5);
        manager.updateSubtask( subtask1);

        Subtask subtask2 = new Subtask("Проверка обновления субтаска 2", " окей ",4 ,Status.IN_PROGRESS, 3);
        manager.updateSubtask( subtask2);


        Epic epic = new Epic("Проверка обновления эпика" + i, "  окей ", 5, Status.NEW);
        manager.updateEpic( epic);
        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getEpic(3)+"\n"+manager.getEpicSubtasks(3));
        manager.deleteSubtask(6);
        System.out.println(manager.getEpic(5)+"\n"+manager.getEpicSubtasks(5));




    }
}