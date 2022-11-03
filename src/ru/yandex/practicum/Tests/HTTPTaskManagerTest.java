package ru.yandex.practicum.Tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.Managers.FileBackedTaskManager;
import ru.yandex.practicum.Managers.HTTPTaskManager;
import ru.yandex.practicum.Managers.ManagerSaveException;
import ru.yandex.practicum.Managers.Managers;
import ru.yandex.practicum.Servers.KVServer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;

public class HTTPTaskManagerTest extends TaskManagerTest<HTTPTaskManager> {
    KVServer kvServer;

    @BeforeEach
    public void setUp() {

        try {
            kvServer = new KVServer();
            kvServer.start();
            taskManager = Managers.getDefault("http://localhost:8078/");
            emptyTaskManager = Managers.getDefault("http://localhost:8078/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.setUp();
    }

    @AfterEach
    public void cleanUp() {
        kvServer.stop();
    }

}
