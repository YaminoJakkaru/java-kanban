package ru.yandex.practicum.Adapters;

import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import ru.yandex.practicum.Tasks.Status;
import ru.yandex.practicum.Tasks.Task;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class TaskAdapter extends TypeAdapter<Task> {
    @Override
    public void write(JsonWriter out, Task task) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.HH:mm");

        out.beginObject();
        out.name("identificationNumber").value(task.getIdentificationNumber());
        out.name("name").value(task.getName());
        out.name("status").value(task.getStatus().toString());
        out.name("description").value(task.getDescription());
        out.name("startTime").value(task.getStartTime().format(formatter));
        out.name("duration").value(task.getDuration());
        out.endObject();
    }

    @Override
    public Task read(JsonReader in) throws IOException {
        int id = 0;
        String name = "";
        Status status = null;
        String description = "";
        String startTime = "";
        long duration = 0;

        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "identificationNumber":
                    id = in.nextInt();
                    break;
                case "name":
                    name = in.nextString();
                    break;
                case "status":
                    status = Status.valueOf(in.nextString());
                    break;
                case "description":
                    description = in.nextString();
                    break;
                case "startTime":
                    startTime = in.nextString();
                    break;
                case "duration":
                    duration = in.nextInt();
                    break;
                default:
                    in.skipValue();
            }
        }
        in.endObject();
        return new Task(id, name, status, description, startTime, duration);
    }
}