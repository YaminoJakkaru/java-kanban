package ru.yandex.practicum.Tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.Managers.FileBackedTaskManager;
import ru.yandex.practicum.Managers.ManagerSaveException;
import ru.yandex.practicum.Managers.Managers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager>{
    private File file;

    @BeforeEach
        public void setUp(){
         file=new File("resources/test");
        taskManager=new FileBackedTaskManager(file);
        emptyTaskManager = new FileBackedTaskManager(file);
        initTask();
    }
    @AfterEach
    public void cleanUp(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("");
        }catch (IOException e) {
            throw new ManagerSaveException();
        }
    }
    @Test
    public void saveEmptyTest() {
        emptyTaskManager.save();
        String text;
        try {
            text = Files.readString(Path.of(file.getPath())).replaceAll("\\s+","");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals( "id,type,name,status,description,startTime,duration,epic",text,
                "Неправильно сохраняется пустой список ");


    }
    @Test
    public void saveTest(){
        taskManager.deleteAllTasks();
        taskManager.deleteAllSubtasks();
        taskManager.deleteEpic(4);
        taskManager.save();
        String text;
        try {
            text = Files.readString(Path.of(file.getPath())).replaceAll("\\s+","");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals( "id,type,name,status,description,startTime,duration,epic3,EPIC,t1,IN_PROGRESS,Stat,26.02.2011.22:22,30",text,
                "Неправильно сохраняется эпик");
    }
    @Test
    public void HistorySaveTest(){
        taskManager.getTask(1);

        taskManager.deleteAllEpics();
        taskManager.save();
        String text;
        try {
            text = Files.readString(Path.of(file.getPath())).replaceAll("\\s+","");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(text,
                "id,type,name,status,description,startTime,duration,epic1,TASK,t1,IN_PROGRESS,Stat," +
                        "22.02.2010.22:22,302,TASK,t1,IN_PROGRESS,Stat,22.02.2008.22:22,301,"
                ,
                "Неправильно сохраняется история");
    }

    @Test
    public void fromStringTest(){
        Assertions.assertEquals(taskManager.getTask(1).toString(),
                "1,TASK, t1 ,IN_PROGRESS,Stat,22.02.2010.22:22,30", "Неправильно сохраняется история");
    }



    @Test
    public void HistoryFromStringTest(){
        Assertions.assertEquals(FileBackedTaskManager.historyFromString("2,4,1"), List.of(2,4,1),
                "Не верно читается история");
    }

    @Test
    public void defunctLoadFromFileTest(){
        Assertions.assertThrows(ManagerSaveException.class,()-> Managers.loadFromFile(new File("resources/defunctTest")),
                "Не выдается ManagerSaveException");


    }

    @Test
    public void emptyLoadFromFileTest(){

        Assertions.assertAll(
                ()->Assertions.assertEquals(emptyTaskManager.getTasks().toString(),"[]","Не верно читается пустой  файл"),
                ()->Assertions.assertEquals(emptyTaskManager.getEpics().toString(),"[]","Не верно читается пустой  файл"),
                ()->Assertions.assertEquals(emptyTaskManager.getSubtasks().toString(),"[]","Не верно читается пустой  файл"),
                ()->Assertions.assertEquals(emptyTaskManager.getHistory().toString(),"[]","Не верно читается пустой  файл")
        );
    }

    @Test
    public void loadFromFileTest(){
        emptyTaskManager=Managers.loadFromFile(file);
        Assertions.assertEquals(emptyTaskManager.getEpic(3),epic3,"Не верно читается файл");
    }
    @Test
    public void historyLoadFromFileTest(){

        taskManager.getTask(1);
        taskManager.getTask(2);
        emptyTaskManager=Managers.loadFromFile(file);
        Assertions.assertEquals(emptyTaskManager.getHistory(),emptyTaskManager.getTasks(),"Не верно читается история из файла");
    }

}