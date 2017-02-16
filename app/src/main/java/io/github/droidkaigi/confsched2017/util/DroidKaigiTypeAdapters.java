package io.github.droidkaigi.confsched2017.util;

import com.github.gfx.android.orma.annotation.StaticTypeAdapter;
import com.github.gfx.android.orma.annotation.StaticTypeAdapters;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import io.github.droidkaigi.confsched2017.BuildConfig;

@StaticTypeAdapters({
        @StaticTypeAdapter(
                targetType = ZonedDateTime.class,
                serializedType = String.class,
                serializer = "serializeZonedDateTime",
                deserializer = "deserializeZonedDateTime"
        )
})
public class DroidKaigiTypeAdapters {

    private static final ZoneId CONFERENCE_TIMEZONE = ZoneId.of(BuildConfig.CONFERENCE_TIMEZONE);
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static String serializeZonedDateTime(ZonedDateTime time) {
        return time.format(formatter);
    }

    public static ZonedDateTime deserializeZonedDateTime(String serialized) {
        return LocalDateTime.parse(serialized, formatter).atZone(CONFERENCE_TIMEZONE);
    }
}
