package ru.yandex.practicum.Servers;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ru.yandex.practicum.Managers.FileBackedTaskManager;
import ru.yandex.practicum.Managers.Managers;
import ru.yandex.practicum.Managers.TaskManager;
import ru.yandex.practicum.Tasks.Epic;
import ru.yandex.practicum.Tasks.Status;
import ru.yandex.practicum.Tasks.Subtask;
import ru.yandex.practicum.Tasks.Task;

public class HttpTaskServer {
    private static final int PORT = 8080;

    static TaskManager manager;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    HttpServer server;
    private static Gson gson;

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        manager = taskManager;

        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", this::handler);

    }

    public static void main(String[] args) throws IOException {
        final HttpTaskServer httpTaskServer = new HttpTaskServer(Managers.getDefault(" http://localhost:8078/"));
        httpTaskServer.server.start();

    }


    private void handler(HttpExchange h) {

        try {
            final String method = h.getRequestMethod();
            final String path = h.getRequestURI().getPath().substring(7);
            final String request = h.getRequestURI().toString();

            switch (method) {
                case "GET":
                    gson = new GsonBuilder().setPrettyPrinting()
                            .registerTypeAdapter(Task.class, new TaskSerializer())
                            .registerTypeAdapter(Epic.class, new EpicSerializer())
                            .registerTypeAdapter(Subtask.class, new SubtaskSerializer())
                            .create();

                    if (path.contains("task") && !path.contains("subtask")) {

                        if (request.contains("?")) {
                            try (OutputStream os = h.getResponseBody()) {
                                h.sendResponseHeaders(200, 0);
                                os.write(gson.toJson(manager.getTask(Integer.parseInt(request.split("=")[1])))
                                        .getBytes());
                                return;
                            }
                        }
                        try (OutputStream os = h.getResponseBody()) {
                            h.sendResponseHeaders(200, 0);
                            os.write(gson.toJson(manager.getTasks()).getBytes());
                            return;
                        }
                    }
                    if (path.contains("epic") && !path.contains("epicSubtask")) {
                        if (request.contains("?")) {
                            try (OutputStream os = h.getResponseBody()) {
                                h.sendResponseHeaders(200, 0);
                                os.write(gson.toJson(manager.getEpic(Integer.parseInt(request.split("=")[1])))
                                        .getBytes());
                                return;
                            }
                        }
                        try (OutputStream os = h.getResponseBody()) {
                            h.sendResponseHeaders(200, 0);
                            os.write(gson.toJson(manager.getEpics()).getBytes());
                            return;
                        }
                    }
                    if (path.contains("epicSubtask")) {
                        if (request.contains("?")) {
                            try (OutputStream os = h.getResponseBody()) {
                                h.sendResponseHeaders(200, 0);
                                os.write(gson.toJson(manager.getEpicSubtasks(Integer.parseInt(request.split("=")[1])))
                                        .getBytes());
                                return;
                            }
                        }
                    }
                    if (path.contains("subtask") && !path.contains("epicSubtask")) {
                        if (request.contains("?")) {
                            try (OutputStream os = h.getResponseBody()) {
                                h.sendResponseHeaders(200, 0);
                                os.write(gson.toJson(manager.getSubtask(Integer.parseInt(request.split("=")[1])))
                                        .getBytes());
                                return;
                            }
                        }
                        try (OutputStream os = h.getResponseBody()) {
                            h.sendResponseHeaders(200, 0);
                            os.write(gson.toJson(manager.getSubtasks()).getBytes());
                            return;
                        }
                    }
                    if (path.contains("history")) {
                        try (OutputStream os = h.getResponseBody()) {
                            h.sendResponseHeaders(200, 0);
                            os.write(gson.toJson(manager.getHistory()).getBytes());
                            return;
                        }
                    }
                    break;

                case "POST":
                    gson = new GsonBuilder().setPrettyPrinting()
                            .registerTypeAdapter(Task.class, new TaskDeserializer())
                            .registerTypeAdapter(Epic.class, new EpicDeserializer())
                            .registerTypeAdapter(Subtask.class, new SubtaskDeserializer())
                            .create();

                    if (path.contains("task") && !path.contains("subtask")) {
                        if (request.contains("?")) {
                            InputStream s = h.getRequestBody();
                            String body = new String(s.readAllBytes(), DEFAULT_CHARSET);
                            Task task = gson.fromJson(body, Task.class);
                            h.sendResponseHeaders(201, 0);
                            manager.updateTask(task);
                            h.close();
                            return;
                        }

                        InputStream s = h.getRequestBody();
                        String body = new String(s.readAllBytes(), DEFAULT_CHARSET);
                        Task task = gson.fromJson(body, Task.class);
                        h.sendResponseHeaders(201, 0);
                        manager.addTask(task);
                        h.close();
                        return;
                    }
                    if (path.contains("epic")) {
                        if (request.contains("?")) {
                            InputStream s = h.getRequestBody();
                            String body = new String(s.readAllBytes(), DEFAULT_CHARSET);
                            Epic epic = gson.fromJson(body, Epic.class);
                            h.sendResponseHeaders(201, 0);
                            manager.updateTask(epic);
                            h.close();
                            return;
                        }
                        InputStream s = h.getRequestBody();
                        String body = new String(s.readAllBytes(), DEFAULT_CHARSET);
                        Epic epic = gson.fromJson(body, Epic.class);
                        h.sendResponseHeaders(201, 0);
                        manager.addEpic(epic);
                        h.close();
                        return;
                    }
                    if (path.contains("subtask")) {
                        if (request.contains("?")) {
                            InputStream s = h.getRequestBody();
                            String body = new String(s.readAllBytes(), DEFAULT_CHARSET);
                            Subtask subtask = gson.fromJson(body, Subtask.class);
                            h.sendResponseHeaders(201, 0);
                            manager.updateSubtask(subtask);
                            h.close();
                            return;
                        }
                        InputStream s = h.getRequestBody();
                        String body = new String(s.readAllBytes(), DEFAULT_CHARSET);
                        Subtask subtask = gson.fromJson(body, Subtask.class);
                        h.sendResponseHeaders(201, 0);
                        manager.addSubtask(subtask);
                        h.close();
                        return;
                    }
                case "DELETE":
                    if (path.contains("task") && !path.contains("subtask")) {
                        if (request.contains("?")) {
                            h.sendResponseHeaders(201, 0);
                            manager.deleteTask(Integer.parseInt(request.split("=")[1]));
                            h.close();
                            return;
                        }
                        h.sendResponseHeaders(201, 0);
                        manager.deleteAllTasks();
                        h.close();
                        return;
                    }
                    if (path.contains("epic")) {
                        if (request.contains("?")) {
                            h.sendResponseHeaders(201, 0);
                            manager.deleteEpic(Integer.parseInt(request.split("=")[1]));
                            h.close();
                            return;
                        }
                        h.sendResponseHeaders(201, 0);
                        manager.deleteAllEpics();
                        h.close();
                        return;
                    }
                    if (path.contains("subtask")) {
                        if (request.contains("?")) {
                            h.sendResponseHeaders(201, 0);
                            manager.deleteSubtask(Integer.parseInt(request.split("=")[1]));
                            h.close();
                            return;
                        }
                        h.sendResponseHeaders(201, 0);
                        manager.deleteAllSubtasks();
                        h.close();
                        return;
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


