package ru.yandex.practicum.Servers;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import ru.yandex.practicum.Adapters.EpicAdapter;
import ru.yandex.practicum.Adapters.SubtaskAdapter;
import ru.yandex.practicum.Adapters.TaskAdapter;
import ru.yandex.practicum.Managers.Managers;
import ru.yandex.practicum.Managers.TaskManager;
import ru.yandex.practicum.Tasks.Epic;
import ru.yandex.practicum.Tasks.Subtask;
import ru.yandex.practicum.Tasks.Task;

public class HttpTaskServer {
    private static final int PORT = 8080;

    private final TaskManager manager;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final HttpServer server;
    private final Gson gson;

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(Task.class, new TaskAdapter())
                .registerTypeAdapter(Epic.class, new EpicAdapter())
                .registerTypeAdapter(Subtask.class, new SubtaskAdapter())
                .create();
        manager = taskManager;
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", this::handler);

    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop(1);
    }

    private void handler(HttpExchange h) {
        try {
            final String method = h.getRequestMethod();
            final String path = h.getRequestURI().getPath().substring(7);
            final String request = h.getRequestURI().toString();

            switch (method) {
                case "GET":
                    if (path.contains("task") && !path.contains("subtask") && !path.contains("epicSubtask")) {
                        if (request.contains("?")) {
                            getTask(h, gson.toJson(manager.getTask(Integer.parseInt(request.split("=")[1]))));
                            return;
                        }
                        getTasks(h, gson.toJson(manager.getTasks()));
                        return;
                    }
                    if (path.contains("epic") && !path.contains("epicSubtask")) {
                        if (request.contains("?")) {
                            getEpic(h, gson.toJson(manager.getEpic(Integer.parseInt(request.split("=")[1]))));
                            return;
                        }
                        getEpics(h, gson.toJson(manager.getEpics()));
                        return;
                    }
                    if (path.contains("epicSubtask")) {
                        if (request.contains("?")) {
                            getEpicSubtasks(h, gson.toJson(manager.getEpicSubtasks(Integer.parseInt(request.split("=")[1]))));
                            return;
                        }
                    }
                    if (path.contains("subtask") && !path.contains("epicSubtask")) {
                        if (request.contains("?")) {
                            getSubtask(h, gson.toJson(manager.getSubtask(Integer.parseInt(request.split("=")[1]))));
                            return;
                        }
                        getSubtasks(h, gson.toJson(manager.getSubtasks()));
                        return;
                    }
                    if (path.contains("history")) {
                        getHistory(h, gson.toJson(manager.getHistory()));
                        return;
                    }
                    break;

                case "POST":
                    if (path.contains("task") && !path.contains("subtask")) {
                        if (request.contains("?")) {
                            updateTask(h);
                            return;
                        }
                        addTask(h);
                        return;
                    }
                    if (path.contains("epic")) {
                        if (request.contains("?")) {
                            updateEpic(h);
                            return;
                        }
                        addEpic(h);
                        return;
                    }
                    if (path.contains("subtask")) {
                        if (request.contains("?")) {
                            updateSubtask(h);
                            return;
                        }
                        addSubtask(h);
                        return;
                    }
                case "DELETE":
                    if (path.contains("task") && !path.contains("subtask")) {
                        if (request.contains("?")) {
                            deleteTask(h, request);
                            return;
                        }
                        deleteAllTasks(h);
                        return;
                    }
                    if (path.contains("epic")) {
                        if (request.contains("?")) {
                            deleteEpic(h, request);
                            return;
                        }
                        deleteAllEpics(h);
                        return;
                    }
                    if (path.contains("subtask")) {
                        if (request.contains("?")) {
                            deleteSubtask(h, request);
                            return;
                        }
                        deleteAllSubtask(h);
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteAllSubtask(HttpExchange h) throws IOException {
        h.sendResponseHeaders(201, 0);
        manager.deleteAllSubtasks();
        h.close();
    }

    private void deleteSubtask(HttpExchange h, String request) throws IOException {
        h.sendResponseHeaders(201, 0);
        manager.deleteSubtask(Integer.parseInt(request.split("=")[1]));
        h.close();
    }

    private void deleteAllEpics(HttpExchange h) throws IOException {
        h.sendResponseHeaders(201, 0);
        manager.deleteAllEpics();
        h.close();
    }

    private void deleteEpic(HttpExchange h, String request) throws IOException {
        h.sendResponseHeaders(201, 0);
        manager.deleteEpic(Integer.parseInt(request.split("=")[1]));
        h.close();
    }

    private void deleteAllTasks(HttpExchange h) throws IOException {
        h.sendResponseHeaders(201, 0);
        manager.deleteAllTasks();
        h.close();
    }

    private void deleteTask(HttpExchange h, String request) throws IOException {
        h.sendResponseHeaders(201, 0);
        manager.deleteTask(Integer.parseInt(request.split("=")[1]));
        h.close();
    }

    private void addSubtask(HttpExchange h) throws IOException {
        InputStream s = h.getRequestBody();
        String body = new String(s.readAllBytes(), DEFAULT_CHARSET);
        Subtask subtask = gson.fromJson(body, Subtask.class);
        h.sendResponseHeaders(201, 0);
        manager.addSubtask(subtask);
        h.close();
    }

    private void updateSubtask(HttpExchange h) throws IOException {
        InputStream s = h.getRequestBody();
        String body = new String(s.readAllBytes(), DEFAULT_CHARSET);
        Subtask subtask = gson.fromJson(body, Subtask.class);
        h.sendResponseHeaders(201, 0);
        manager.updateSubtask(subtask);
        h.close();
    }

    private void addEpic(HttpExchange h) throws IOException {
        InputStream s = h.getRequestBody();
        String body = new String(s.readAllBytes(), DEFAULT_CHARSET);
        Epic epic = gson.fromJson(body, Epic.class);
        h.sendResponseHeaders(201, 0);
        manager.addEpic(epic);
        h.close();
    }

    private void updateEpic(HttpExchange h) throws IOException {
        InputStream s = h.getRequestBody();
        String body = new String(s.readAllBytes(), DEFAULT_CHARSET);
        Epic epic = gson.fromJson(body, Epic.class);
        h.sendResponseHeaders(201, 0);
        manager.updateEpic(epic);
        h.close();
    }

    private void addTask(HttpExchange h) throws IOException {
        InputStream s = h.getRequestBody();
        String body = new String(s.readAllBytes(), DEFAULT_CHARSET);
        Task task = gson.fromJson(body, Task.class);
        h.sendResponseHeaders(201, 0);
        manager.addTask(task);
        h.close();
    }

    private void updateTask(HttpExchange h) throws IOException {
        InputStream s = h.getRequestBody();
        String body = new String(s.readAllBytes(), DEFAULT_CHARSET);
        Task task = gson.fromJson(body, Task.class);
        h.sendResponseHeaders(201, 0);
        manager.updateTask(task);
        h.close();
    }

    private void getHistory(HttpExchange h, String gson) throws IOException {
        try (OutputStream os = h.getResponseBody()) {
            h.sendResponseHeaders(200, 0);
            os.write(gson.getBytes());
        }
    }

    private void getSubtasks(HttpExchange h, String gson) throws IOException {
        try (OutputStream os = h.getResponseBody()) {
            h.sendResponseHeaders(200, 0);
            os.write(gson.getBytes());
        }
    }

    private void getSubtask(HttpExchange h, String gson) throws IOException {
        try (OutputStream os = h.getResponseBody()) {
            h.sendResponseHeaders(200, 0);
            os.write(gson
                    .getBytes());
        }
    }

    private void getEpicSubtasks(HttpExchange h, String gson) throws IOException {
        try (OutputStream os = h.getResponseBody()) {
            h.sendResponseHeaders(200, 0);
            os.write(gson
                    .getBytes());
        }
    }

    private void getEpics(HttpExchange h, String gson) throws IOException {
        try (OutputStream os = h.getResponseBody()) {
            h.sendResponseHeaders(200, 0);
            os.write(gson.getBytes());
        }
    }

    private void getEpic(HttpExchange h, String gson) throws IOException {
        try (OutputStream os = h.getResponseBody()) {
            h.sendResponseHeaders(200, 0);
            os.write(gson.getBytes());
        }
    }

    private void getTasks(HttpExchange h, String gson) throws IOException {
        try (OutputStream os = h.getResponseBody()) {
            h.sendResponseHeaders(200, 0);
            os.write(gson.getBytes());
        }
    }

    private void getTask(HttpExchange h, String gson) throws IOException {
        try (OutputStream os = h.getResponseBody()) {
            h.sendResponseHeaders(200, 0);
            os.write(gson.getBytes());
        }
    }
}


