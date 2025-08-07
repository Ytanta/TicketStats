package org.example.util;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy H:mm");

    public static long calculateFlightDuration(String departureDate, String departureTime,
                                               String arrivalDate, String arrivalTime) {
        LocalDateTime departure = LocalDateTime.parse(departureDate + " " + departureTime, formatter);
        LocalDateTime arrival = LocalDateTime.parse(arrivalDate + " " + arrivalTime, formatter);
        return Duration.between(departure, arrival).toMinutes();
    }
}