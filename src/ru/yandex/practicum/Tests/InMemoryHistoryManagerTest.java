package ru.yandex.practicum.Tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.Managers.HistoryManager;
import ru.yandex.practicum.Managers.Managers;
import ru.yandex.practicum.Tasks.Epic;
import ru.yandex.practicum.Tasks.Status;
import ru.yandex.practicum.Tasks.Subtask;
import ru.yandex.practicum.Tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    protected HistoryManager history;
    protected HistoryManager emptyHistory;
    Task task1= new Task(1, " t1 ", Status.IN_PROGRESS, "Stat","22.02.2022.22:22",30);
    Task task2 =new Task(2, " t2 ", Status.IN_PROGRESS, "Stat","24.02.2022.22:22",30);
    Epic epic3 =new Epic(3, " t1 ", Status.IN_PROGRESS, "Stat","26.02.2022.22:22",30);
    Epic epic4=new Epic(4, " t2 ", Status.IN_PROGRESS, "Stat","25.02.2022.22:22",30);
    Subtask subtask5=new Subtask(5, " t1 ", Status.IN_PROGRESS, "Stat",4,"25.02.2022.22:27",3);
    Subtask subtask6=new Subtask(6, " t2 ", Status.IN_PROGRESS, "Stat",4,"25.02.2022.22:40",30);
    @BeforeEach
    public void setUp() {
        history = Managers.getDefaultHistory();

        emptyHistory = Managers.getDefaultHistory();
        history.add(task2);
        history.add(subtask5);
        history.add(epic3);
        history.add(epic4);
        history.add(subtask6);
    }

    @Test
    public void testAdd(){

        emptyHistory.add(task1);
        Assertions.assertEquals(task1,emptyHistory.getHistory().get(0),"Не верно добавляется в историю");
    }

    @Test
    public void testDoubleAdd(){
        history.add(task2);
        Assertions.assertEquals(List.of(subtask5,epic3,epic4,subtask6,task2),history.getHistory(),
                "В историю не верно сохраняются дубликаты");
    }

    @Test
    public void testFirstRemove(){
        history.remove(2);
        Assertions.assertEquals(List.of(subtask5,epic3,epic4,subtask6),history.getHistory(),
                "Неверно удаляется перовое значение");
    }

    @Test
    public void testLastRemove(){
        history.remove(6);
        Assertions.assertEquals(List.of(task2,subtask5,epic3,epic4),history.getHistory(),
                "Неверно удаляется последнее значение");
    }
    @Test
    public void testMidRemove(){
        history.remove(3);
        Assertions.assertEquals(List.of(task2,subtask5,epic4,subtask6),history.getHistory(),
                "Неверно удаляется средние значение");
    }
   


}