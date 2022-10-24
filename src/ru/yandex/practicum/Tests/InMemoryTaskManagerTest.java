package ru.yandex.practicum.Tests;

import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.Managers.InMemoryTaskManager;


class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void setUp(){
        taskManager= new InMemoryTaskManager();
        emptyTaskManager = new InMemoryTaskManager();
        super.setUp();
    }

}