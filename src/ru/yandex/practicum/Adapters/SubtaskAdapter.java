package ru.yandex.practicum.Adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import ru.yandex.practicum.Tasks.Status;
import ru.yandex.practicum.Tasks.Subtask;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class SubtaskAdapter  extends TypeAdapter<Subtask> {
    @Override
    public void write(JsonWriter out, Subtask subtask) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.HH:mm");

        out.beginObject();
        out.name("identificationNumber").value(subtask.getIdentificationNumber());
        out.name("name").value(subtask.getName());
        out.name("status").value(subtask.getStatus().toString());
        out.name("description").value(subtask.getDescription());
        out.name("epicId").value(subtask.getEpic());
        out.name("startTime").value(subtask.getStartTime().format(formatter));
        out.name("duration").value(subtask.getDuration());
        out.endObject();
    }

    @Override
    public Subtask read(JsonReader in) throws IOException {
        int id = 0;
        String name = "";
        Status status = null;
        String description = "";
        int epicId = 0;
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
                case "epicId":
                    epicId = in.nextInt();
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
        return new Subtask(id, name, status, description,epicId, startTime, duration);
    }
}

