package ru.yandex.practicum.Servers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.yandex.practicum.Tasks.Subtask;
import ru.yandex.practicum.Tasks.Task;
import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;

public class SubtaskSerializer implements JsonSerializer<Subtask> {
@Override
public JsonElement serialize (Subtask src, Type typeOfSrc, JsonSerializationContext context)
        {
        JsonObject result = new JsonObject();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.HH:mm");
        result.addProperty("identificationNumber", src.getIdentificationNumber());
        result.addProperty("name", src.getName());
        result.addProperty("status", src.getStatus().toString());
        result.addProperty("description", src.getDescription());
        result.addProperty("startTime", src.getStartTime().format(formatter));
        result.addProperty("duration", src.getDuration());
        result.addProperty("epic", src.getEpic());
        return result;
        }
        }