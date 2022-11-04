package ru.yandex.practicum.Tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.Adapters.EpicAdapter;
import ru.yandex.practicum.Adapters.SubtaskAdapter;
import ru.yandex.practicum.Adapters.TaskAdapter;
import ru.yandex.practicum.Managers.FileBackedTaskManager;
import ru.yandex.practicum.Managers.HTTPTaskManager;
import ru.yandex.practicum.Managers.Managers;
import ru.yandex.practicum.Servers.*;
import ru.yandex.practicum.Tasks.Epic;
import ru.yandex.practicum.Tasks.Status;
import ru.yandex.practicum.Tasks.Subtask;
import ru.yandex.practicum.Tasks.Task;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpTaskServerTest {
    HttpTaskServer taskServer;
    KVServer kvServer;
    HTTPTaskManager taskManager;
    Task task1 = new Task(1, " t1 ", Status.IN_PROGRESS, "Stat", "22.02.2022.22:22", 30);
    Task task2 = new Task(2, " t2 ", Status.IN_PROGRESS, "Stat", "24.02.2022.22:22", 30);
    Epic epic3 = new Epic(3, " t1 ", Status.IN_PROGRESS, "Stat", "26.02.2022.22:22", 30);
    Epic epic4 = new Epic(4, " t2 ", Status.IN_PROGRESS, "Stat", "25.02.2022.22:22", 30);
    Subtask subtask5 = new Subtask(5, " t1 ", Status.IN_PROGRESS, "Stat", 4, "25.02.2022.22:27", 3);
    Subtask subtask6 = new Subtask(6, " t2 ", Status.IN_PROGRESS, "Stat", 4, "25.02.2022.22:40", 30);
    Task task7 = new Task(1, " t1 ", Status.IN_PROGRESS, "New", "22.02.2023.22:22", 30);
    Epic epic8 = new Epic(3, " t1 ", Status.IN_PROGRESS, "New", "26.02.2023.22:22", 30);
    Subtask subtask9 = new Subtask(6, " t1 ", Status.IN_PROGRESS, "New", 3, "25.02.2022.23:27", 3);

    private static Gson gson;

    @BeforeEach
    public void setUp() {
        try {
            gson = new GsonBuilder().setPrettyPrinting()
                    .registerTypeAdapter(Task.class, new TaskAdapter())
                    .registerTypeAdapter(Epic.class, new EpicAdapter())
                    .registerTypeAdapter(Subtask.class, new SubtaskAdapter())
                    .create();
            kvServer = new KVServer();
            kvServer.start();
            taskManager = Managers.getDefault("http://localhost:8078/");
            taskServer = new HttpTaskServer(taskManager);
            taskManager.addTask(task1);
            taskManager.addTask(task2);
            taskManager.addEpic(epic3);
            taskManager.addEpic(epic4);
            taskManager.addSubtask(subtask5);
            taskManager.addSubtask(subtask6);
            taskServer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void cleanUp() {
        taskServer.stop();
        kvServer.stop();
    }

    @Test
    public void getTasksTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(gson.toJson(taskManager.getTasks()), response.body(),
                "не верно выводятся задачи");
    }

    @Test
    public void getEpicsTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(gson.toJson(taskManager.getEpics()), response.body(),
                "не верно выводятся эпики");
    }

    @Test
    public void getSubtasksTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(gson.toJson(taskManager.getSubtasks()), response.body(),
                "не верно выводятся подзадачи");
    }

    @Test
    public void getHistoryTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/history/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(gson.toJson(taskManager.getHistory()), response.body(),
                "не верно выводится история");
    }

    @Test
    public void getEpicSubtasksTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epicSubtasks/?id=4");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(gson.toJson(taskManager.getEpicSubtasks(4)), response.body(),
                "не верно выводятся подзадачи эпика");
    }

    @Test
    public void getTaskTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(gson.toJson(taskManager.getTask(1)), response.body(),
                "не верно выводится задача");
    }

    @Test
    public void getSubtaskTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask/?id=5");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(gson.toJson(taskManager.getSubtask(5)), response.body(),
                "не верно выводится подзадача");
    }

    @Test
    public void getEpicTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic/?id=3");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(gson.toJson(taskManager.getEpic(3)), response.body(),
                "не верно выводится эпик");
    }

    @Test
    public void deleteTaskTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/?id=2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertThrows(NullPointerException.class, () -> taskManager.getTask(2),
                "задача не удалилась");
    }

    @Test
    public void deleteEpicTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic/?id=3");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertThrows(NullPointerException.class, () -> taskManager.getEpic(3),
                "эпик не удалился");
    }

    @Test
    public void deleteSubtaskTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask/?id=5");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertThrows(NullPointerException.class, () -> taskManager.getSubtask(5),
                "подзадача не удалилась");
    }

    @Test
    public void deleteTasksTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertThrows(NullPointerException.class, () -> taskManager.getTask(1),
                "задача не удалилась");
        Assertions.assertThrows(NullPointerException.class, () -> taskManager.getTask(2),
                "задача не удалилась");
    }

    @Test
    public void deleteEpicsTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertThrows(NullPointerException.class, () -> taskManager.getEpic(3),
                "эпик  не удалился");
        Assertions.assertThrows(NullPointerException.class, () -> taskManager.getEpic(4),
                "эпик  не удалился");
    }

    @Test
    public void deleteSubtasksTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertThrows(NullPointerException.class, () -> taskManager.getSubtask(5),
                "эпик  не удалился");
        Assertions.assertThrows(NullPointerException.class, () -> taskManager.getSubtask(6),
                "эпик  не удалился");
    }

    @Test
    public void addTaskTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers
                .ofString(gson.toJson(task7))).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals("7,TASK, t1 ,IN_PROGRESS,New,22.02.2023.22:22,30",
                taskManager.getTask(7).toString(), "задача не добавлена");
    }

    @Test
    public void addEpicTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers
                .ofString(gson.toJson(epic8))).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals("7,EPIC, t1 ,IN_PROGRESS,New,26.02.2023.22:22,30",
                taskManager.getEpic(7).toString(), "эпик не добавлен");
    }

    @Test
    public void addSubtaskTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers
                .ofString(gson.toJson(subtask9))).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals("7,SUBTASK, t1 ,IN_PROGRESS,New,25.02.2022.23:27,3,3",
                taskManager.getSubtask(7).toString(), "подзадача не добавлена");
    }

    @Test
    public void updateTaskTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers
                .ofString(gson.toJson(task7))).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals("1,TASK, t1 ,IN_PROGRESS,New,22.02.2023.22:22,30",
                taskManager.getTask(1).toString(), "задача не обновлена");
    }

    @Test
    public void updateEpicTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic/?id=3");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers
                .ofString(gson.toJson(epic8))).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals("3,EPIC, t1 ,IN_PROGRESS,New,26.02.2022.22:22,30",
                taskManager.getEpic(3).toString(), "эпик не добавлен");
    }

    @Test
    public void updateSubtaskTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask/?id=6");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers
                .ofString(gson.toJson(subtask9))).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals("6,SUBTASK, t1 ,IN_PROGRESS,New,25.02.2022.23:27,3,3",
                taskManager.getSubtask(6).toString(), "подзадача не добавлена");
    }
}
