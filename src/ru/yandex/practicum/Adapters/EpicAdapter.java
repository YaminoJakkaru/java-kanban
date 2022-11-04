package ru.yandex.practicum.Adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import ru.yandex.practicum.Tasks.Epic;
import ru.yandex.practicum.Tasks.Status;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class EpicAdapter extends TypeAdapter<Epic> {
    @Override
    public void write(JsonWriter out, Epic epic) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.HH:mm");

        out.beginObject();
        out.name("identificationNumber").value(epic.getIdentificationNumber());
        out.name("name").value(epic.getName());
        out.name("status").value(epic.getStatus().toString());
        out.name("description").value(epic.getDescription());
        out.name("startTime").value(epic.getStartTime().format(formatter));
        out.name("duration").value(epic.getDuration());
        out.endObject();
    }

    @Override
    public Epic read(JsonReader in) throws IOException {
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
        return new Epic(id, name, status, description, startTime, duration);
    }
}
