package ru.yandex.practicum.Tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.Managers.TaskManager;
import ru.yandex.practicum.Tasks.Epic;
import ru.yandex.practicum.Tasks.Status;
import ru.yandex.practicum.Tasks.Subtask;
import ru.yandex.practicum.Tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class TaskManagerTest<T extends TaskManager> {
    T taskManager;
    T emptyTaskManager;
    Task task1;
    Task task2 ;
    Epic epic3;
    Epic epic4;
    Subtask subtask5;
    Subtask subtask6;
    protected void setUp(){
        task1= new Task(1, " t1 ", Status.IN_PROGRESS, "Stat","22.02.2010.22:22",30);
        task2=new Task(1, " t1 ", Status.IN_PROGRESS, "Stat","22.02.2008.22:22",30);
        epic3 =new Epic(3, " t1 ", Status.IN_PROGRESS, "Stat","26.02.2011.22:22",30);
        epic4=new Epic(4, " t2 ", Status.IN_PROGRESS, "Stat","25.02.2012.22:22",30);
        subtask5=new Subtask(5, " t1 ", Status.IN_PROGRESS, "Stat",4,"25.02.2012.22:27",3);
        subtask6=new Subtask(6, " t2 ", Status.IN_PROGRESS, "Stat",4,"25.02.2012.10:40",30);

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic3);
        taskManager.addEpic(epic4);
        taskManager.addSubtask(subtask5);
        taskManager.addSubtask(subtask6);
    }

    @Test
    public void getPrioritizedTasksTest(){

        Assertions.assertEquals(taskManager.getPrioritizedTasks().toString(),List.of(task2,task1,subtask5,subtask6).toString());

    }

    @Test
    public void getHistoryTest(){
        List<Task> emptyHistory=taskManager.getHistory();
        taskManager.getTask(1);
        taskManager.getTask(1);
        List<Task> notEmptyHistory=taskManager.getHistory();
        taskManager.getTask(2);
        List<Task> history=taskManager.getHistory();

                Assertions.assertEquals(0,emptyHistory.size(),"Не верное количество задач");
                Assertions.assertEquals(taskManager.getTask(1),notEmptyHistory.get(0),"Значения не совпадают");
                Assertions.assertEquals(1,notEmptyHistory.size(),"В истории не удаляются повторы");
                Assertions.assertEquals(2,history.size(),"В историю неверно сохраняются значения");

    }

    @Test
    public void getTasksTest(){

        List<Task> tasks =taskManager.getTasks();
        List<Task> clearTasks = emptyTaskManager.getTasks();

                Assertions.assertEquals(2,tasks.size(),"Количество задач не совпадает");
                Assertions.assertTrue(tasks.contains(taskManager.getTask(1)),"Нет значения 1");
                Assertions.assertTrue(tasks.contains(taskManager.getTask(2)),"Нет значения 2");
                Assertions.assertEquals(0,clearTasks.size(),
                        "Количество задач в пустом списке не совпадает");



    }

    @Test
    public void  getTaskTest(){

                Assertions.assertEquals(task1,
                        taskManager.getTask(1)," Нет нужного значения");
                Assertions.assertThrows(NullPointerException.class,()-> taskManager.getTask(654),
                        "Не выдается NullPointerException");
                Assertions.assertEquals(task1,
                        taskManager.getHistory().get(0)," В истории нет нужного значения");

    }

    @Test
    public void addTaskTest() {
        Task task = new Task(0,"Test addNewTask", Status.NEW,
                "Test addNewTask description","29.02.2022.22:22",30 );
        final int taskId = taskManager.addTask(task);

        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();

                Assertions.assertNotNull(tasks, "Задачи на возвращаются.");
               Assertions.assertEquals(3, tasks.size(), "Неверное количество задач.");
                Assertions.assertEquals(task, tasks.get(2), "Задачи не совпадают.");

    }

    @Test
    public void deleteAllTasksTest(){
        final Task task = taskManager.getTask(1);
        taskManager.deleteAllTasks();

                Assertions.assertEquals(0,taskManager.getTasks().size(),"Количество не совпадает");
                Assertions.assertFalse(taskManager.getHistory().contains(task),
                        "Задача не удалена из истории");

    }



    @Test
    public void updateTaskTest(){
        taskManager.updateTask(new Task(1, " updateTaskTest1 ", Status.IN_PROGRESS,
                "updateTaskTest1","25.02.2022.22:22",30));
        emptyTaskManager.updateTask(new Task(1, " updateTaskTest1 ", Status.IN_PROGRESS,
                "updateTaskTest1","25.02.2022.22:22",30));

               Assertions.assertEquals(new Task(1, " updateTaskTest1 ", Status.IN_PROGRESS,
                        "updateTaskTest1","25.02.2022.22:22",30),taskManager.getTask(1),"Нет нужного значения");
               Assertions.assertEquals(0, emptyTaskManager.getTasks().size(),
                        "Количество задач в пустом списке не совпадает");


    }

    @Test
    public void deleteTaskTest(){
        taskManager.deleteTask(2);
        List<Task> tasks =taskManager.getTasks();

               Assertions.assertEquals(1,tasks.size(),"Количество не совпадает");
                Assertions.assertTrue(tasks.contains(task1),"Нет значения 1");
                Assertions.assertFalse(tasks.contains(task2),"Нет значения 1");
               Assertions.assertEquals(0,taskManager.getHistory().size(),
                        "Количество задач не совпадает");

    }
    @Test
    public void getSubtasksTest(){

        List<Subtask> subtasks =taskManager.getSubtasks();
        List<Subtask> clearSubtasks = emptyTaskManager.getSubtasks();

                Assertions.assertEquals(2,subtasks.size(),"Количество подзадач не совпадает");
                Assertions.assertTrue(subtasks.contains(taskManager.getSubtask(5)),"Нет подзадачи 5");
                Assertions.assertTrue(subtasks.contains(taskManager.getSubtask(6)),"Нет подзадачи 6");
                Assertions.assertEquals(0,clearSubtasks.size(),
                        "Количество подзадач в пустом списке не совпадает");



    }

    @Test
    public void  getSubtaskTest(){

               Assertions.assertEquals(subtask5, taskManager.getSubtask(5)," Нет нужного значения");
               Assertions.assertThrows(NullPointerException.class,()-> taskManager.getSubtask(654),
                        "Не выдается NullPointerException");
              Assertions.assertEquals(subtask5,
                        taskManager.getHistory().get(0),"В истории нет нужной подзадачи");

    }

    @Test
    public void addSubtaskTest() {
        Subtask subtask = new Subtask(0,"Test addNewTask", Status.NEW,
                "Test addNewTask description",4,"26.02.2022.22:22",30);
        Subtask fakeSubtask = new Subtask(0,"Test addNewTask", Status.NEW,
                "Test addNewTask description",79,"25.02.2028.22:22",30);
        final int taskId = taskManager.addSubtask(subtask);
        final Task savedTask = taskManager.getSubtask(taskId);
        assertNotNull(savedTask, "Подзадача не найдена.");
        assertEquals(subtask, savedTask, "Подзадачи не совпадают.");

        final List<Subtask> subtasks = taskManager.getSubtasks();

                Assertions.assertNotNull(subtasks, "Подзадачи на возвращаются.");
                Assertions.assertEquals(3, subtasks.size(), "Неверное количество подзадач.");
                Assertions.assertEquals(subtask, subtasks.get(2), "Подзадачи не совпадают.");
                Assertions.assertThrows(NullPointerException.class,()-> taskManager.addSubtask(fakeSubtask),
                        "Не выдается NullPointerException");
                Assertions.assertTrue(taskManager.getEpic(4).getSubtasks().contains(7),
                        "В эпике не не появилось такой подзадачи");

    }

    @Test
    public void deleteAllSubtaskTest() {
        final Subtask subtask = taskManager.getSubtask(5);
        taskManager.deleteAllSubtasks();

                Assertions.assertEquals(0, taskManager.getSubtasks().size(),
                        "Количество подзадач не совпадает");
                Assertions.assertEquals(0, taskManager.getEpicSubtasks(4).size(),
                        "Количество подзадач в Эпике не совпадает");
                 Assertions.assertFalse(taskManager.getHistory().contains(subtask),
                        "Подзадача не удалена из истории");

    }



    @Test
    public void updateSubtaskTest(){
        taskManager.updateSubtask(new Subtask(5, " updateSubtaskTest1 ", Status.DONE,
                "updateSubtaskTest1",4,"25.02.2022.22:22",30));
        emptyTaskManager.updateSubtask(new Subtask(1, " updateTaskTest1 ", Status.IN_PROGRESS,
                "updateTaskTest1",0,"25.02.2022.22:22",30));

              Assertions.assertEquals(new Subtask(5, " updateSubtaskTest1 ", Status.DONE,
                                "updateSubtaskTest1",4,"25.02.2022.22:22",30),
                      taskManager.getSubtask(5), "Нет нужного значения");
              Assertions.assertEquals(0, emptyTaskManager.getSubtasks().size(),
                      "Количество подзадач в пустом списке не совпадает");


    }

    @Test
    public void deleteSubtaskTest(){
        taskManager.deleteSubtask(5);
        List<Subtask> subtasks =taskManager.getSubtasks();

                Assertions.assertEquals(1,subtasks.size(),"Количество не совпадает");
                Assertions.assertTrue(subtasks.contains(subtask6),"Нет подзадачи 5");
                Assertions.assertFalse(taskManager.getEpic(4).getSubtasks().contains(5),
                        "Из Эпика не удалена подзадача");

    }

    @Test
    public void updateEpicStatusTest(){
        taskManager.addEpic(new Epic(7,"Test addNewTask", Status.NEW,
                "Test addNewTask description","24.02.2022.22:22",30));
        taskManager.addSubtask(new Subtask(8, " t2 ",
                Status.DONE, "Stat",7,"24.02.2022.22:22",30));
        taskManager.addEpic(new Epic(9,"Test addNewTask", Status.NEW,
                "Test addNewTask description","25.02.2024.22:22",30));
        taskManager.addSubtask(new Subtask(10, " t2 ",
                Status.DONE, "Stat",9,"24.02.2025.22:22",30));
        taskManager.addSubtask(new Subtask(11, " t2 ",
                Status.NEW, "Stat",9,"25.02.2025.22:22",30));
        taskManager.addEpic(new Epic(12,"Test addNewTask", Status.NEW,
                "Test addNewTask description","25.02.2026.22:22",30));
        taskManager.addSubtask(new Subtask(13, " t2 ",
                Status.NEW, "Stat",12,"25.02.2026.12:22",30));

                Assertions.assertEquals(Status.IN_PROGRESS,taskManager.getEpic(4).getStatus(),
                        " Статус считается неправильно когда все подзадачи IN_PROGRESS ");
                Assertions.assertEquals(Status.IN_PROGRESS,taskManager.getEpic(3).getStatus(),
                        " Статус считается неправильно в эпике без подзадач");
                Assertions.assertEquals(Status.DONE,taskManager.getEpic(7).getStatus(),
                        " Статус считается неправильно когда все подзадачи DONE");
               Assertions.assertEquals(Status.IN_PROGRESS,taskManager.getEpic(9).getStatus(),
                        " Статус считается неправильно когда  подзадачи NEW и DONE");
                Assertions.assertEquals(Status.NEW,taskManager.getEpic(12).getStatus(),
                        " Статус считается неправильно когда  подзадачи  все NEW");

    }
    @Test
    public void  updateEpicTimeTest(){
                Assertions.assertEquals(epic3.getStartTime().toString(),"2011-02-26T22:22",
                        " не верно считается StartTime пустого эпика");
                Assertions.assertEquals(epic4.getStartTime(),subtask6.getStartTime(),
                " не верно считается  StartTime  эпика");
                Assertions.assertEquals(epic4.getDuration(),33,
                        " не верно считается  Duration  эпика");
                Assertions.assertEquals(epic4.getEndTime(),subtask5.getEndTime(),
                        " не верно считается  EndTime  эпика");
    }
    @Test
    public void checkIntersectionsTest(){
               Assertions.assertTrue(taskManager.checkIntersections(new Subtask(8, " t2 ",
                        Status.DONE, "Stat",7,"22.02.2012.22:22",10000)),
                       "не  определяется пересечение");
                        Assertions.assertFalse(taskManager.checkIntersections(new Subtask(8, " t2 ",
                Status.DONE, "Stat",7,"22.02.2040.22:22",1)),
                                "не  определяется отсутствие пересечения");

    }

    @Test
    public void getEpicsTest(){

        List<Epic> epics =taskManager.getEpics();
        List<Epic> clearEpics = emptyTaskManager.getEpics();

                Assertions.assertEquals(2,epics.size(),"Количество эпиков не совпадает");
                Assertions.assertTrue(epics.contains(taskManager.getEpic(3)),"Нет эпика 3");
                Assertions.assertTrue(epics.contains(taskManager.getEpic(4)),"Нет эпика 4");
                Assertions.assertEquals(0,clearEpics.size(),
                        "Количество эпиков в пустом списке не совпадает");



    }

    @Test
    public void  getEpicTest(){

                Assertions.assertEquals(epic3, taskManager.getEpic(3)," Нет нужного значения");
                Assertions.assertThrows(NullPointerException.class,()-> taskManager.getEpic(654),
                        "Не выдается NullPointerException");
                Assertions.assertEquals(epic3,
                        taskManager.getHistory().get(0),"В истории нет нужного значения");

    }

    @Test
    public void addEpicTest() {
        Epic epic = new Epic(0,"Test addNewTask", Status.NEW,
                "Test addNewTask description","25.02.2023.22:22",30);
        final int taskId = taskManager.addEpic(epic);
        final Task savedTask = taskManager.getEpic(taskId);
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(epic, savedTask, "Задачи не совпадают.");

        final List<Epic> epics = taskManager.getEpics();

                Assertions.assertNotNull(epics, "Эпики на возвращаются.");
                Assertions.assertEquals(3, epics.size(), "Неверное количество эпиков.");
                Assertions.assertEquals(epic, epics.get(2), "Эпики не совпадают.");

    }

    @Test
    public void deleteAllEpicsTest() {
        final Epic epic = taskManager.getEpic(4);
        taskManager.deleteAllEpics();

                 Assertions.assertEquals(0, taskManager.getSubtasks().size(),
                        "Количество эпиков не совпадает");
                 Assertions.assertEquals(0, taskManager.getSubtasks().size(),
                        "Количество подзадач в эпике не совпадает");
                Assertions.assertFalse(taskManager.getHistory().contains(epic),
                        "Подзадача не удалена из истории");

    }



    @Test
    public void updateEpicTest(){
        taskManager.updateEpic(new Epic(3, " updateSubtaskTest1 ", Status.DONE,
                "updateSubtaskTest1","26.02.2045.22:22",30));
        emptyTaskManager.updateEpic(new Epic(1, " updateTaskTest1 ", Status.IN_PROGRESS,
                "updateTaskTest1","26.02.2011.22:22",30));
               Assertions.assertEquals(new Epic(3, " updateSubtaskTest1 ",
                                Status.IN_PROGRESS, "updateSubtaskTest1","26.02.2011.22:22",30),
                       taskManager.getEpic(3), "Нет нужного эпика");
                Assertions.assertEquals(Status.IN_PROGRESS,taskManager.getEpic(3).getStatus(),
                        "Статус эпика не должен меняться");
                Assertions.assertEquals(0, emptyTaskManager.getEpics().size(),
                        "Количество эпиков в пустом списке не совпадает");


    }

    @Test
    public void deleteEpicTest(){
        taskManager.deleteEpic(4);
        List<Epic> epics =taskManager.getEpics();

                Assertions.assertEquals(1,epics.size(),"Количество не совпадает");
                Assertions.assertTrue(epics.contains(epic3),"Нет эпика 3");
                Assertions.assertEquals(0,taskManager.getSubtasks().size(),
                        "Подзадачи эпика не удалены");

    }
}
