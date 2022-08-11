import ru.yandex.practicum.Manager.Manager;
import ru.yandex.practicum.Manager.Tasks.Epic;
import ru.yandex.practicum.Manager.Tasks.Subtask;
import ru.yandex.practicum.Manager.Tasks.Task;

public class Main {
    public static void main(String[] args) {

        Manager manager = new Manager();
        int i = 0;
        while (i < 2) {
            i++;
            Task task = new Task("Проверка таска " + i, " окей ", "NEW");
            manager.addTask(task);
        }
        i = 0;

        while (i < 2) {
            i++;
            Epic epic = new Epic("Проверка эпика " + i, " окей ", "NEW");
            manager.addEpic(epic);
            int j = 0;
            while (j < i) {
                j++;
                Subtask subtask = new Subtask("Проверка субтаска " + i + j, " окей ", "NEW", epic.getIdentificationNumber());
                manager.addSubtask(subtask);
            }

        }


        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getTask(1));
        System.out.println(manager.getSubtask(4));
        System.out.println(manager.getEpic(5));
        Subtask subtask1 = new Subtask("Проверка обновления субтаска", " окей ", "DONE", 7);
        manager.updateSubtask(7, subtask1);
        manager.deleteById(6);
        Subtask subtask2 = new Subtask("Проверка обновления субтаска 2", " окей ", "IN_PROGRESS", 4);
        manager.updateSubtask(4, subtask2);
        manager.deleteById(2);
        System.out.println(manager.getAll());
        Epic epic = new Epic("Проверка обновления эпика" + i, "  окей ", "NEW");
        manager.updateEpic(3, epic);
        System.out.println(manager.getInfoById(3));
        manager.deleteAll();
        System.out.println(manager.getAll());

    }
}