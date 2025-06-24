package ru.job4j.todo.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

public final class TimeZoneUtils {

    private TimeZoneUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static List<String> getAvailableTimeZoneIds() {
        return Arrays.asList(TimeZone.getAvailableIDs());
    }

    public static String formatZonedDateTimeToUserZone(ZonedDateTime utcDateTime, String userTimeZone) {
        String zoneId = (userTimeZone != null)
                ? userTimeZone
                : TimeZone.getDefault().getID();

        return utcDateTime
                .withZoneSameInstant(ZoneId.of(zoneId))
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

}