package ru.yandex.practicum.Servers;

import com.google.gson.*;
import ru.yandex.practicum.Tasks.Epic;
import ru.yandex.practicum.Tasks.Status;

import java.lang.reflect.Type;


public class EpicDeserializer implements JsonDeserializer<Epic>
{
    public Epic deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int id= jsonObject.get("identificationNumber").getAsInt();
        String name=jsonObject.get("name").getAsString();
        Status status= Status.valueOf(jsonObject.get("status").getAsString());
        String description=jsonObject.get("description").getAsString();
        String startTime= jsonObject.get("startTime").getAsString();
        long duration=jsonObject.get("duration").getAsInt();
        return new Epic(id,name,status,description,startTime,duration);
    }


}