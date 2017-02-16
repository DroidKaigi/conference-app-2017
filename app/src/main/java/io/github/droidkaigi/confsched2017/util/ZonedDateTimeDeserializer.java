package io.github.droidkaigi.confsched2017.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.threeten.bp.ZonedDateTime;

import java.lang.reflect.Type;

public class ZonedDateTimeDeserializer implements JsonDeserializer<ZonedDateTime> {
    @Override
    public ZonedDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws
            JsonParseException {
        return DroidKaigiTypeAdapters.deserializeZonedDateTime(jsonElement.getAsString());
    }
}
