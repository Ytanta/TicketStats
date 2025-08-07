package org.example;

import org.example.service.TicketService;

import java.util.Map;

public class App {
    public static void main(String[] args) {
        try {
            TicketService service = new TicketService("tickets.json");

            System.out.println("Минимальное время полета между Владивостоком и Тель-Авивом для каждого авиаперевозчика:");
            Map<String, Long> minTimes = service.getMinFlightTimes("VVO", "TLV");
            for (var entry : minTimes.entrySet()) {
                long mins = entry.getValue();
                System.out.printf("- %s: %d ч %d мин%n", entry.getKey(), mins / 60, mins % 60);
            }

            double avg = service.getAveragePrice("VVO", "TLV");
            double median = service.getMedianPrice("VVO", "TLV");

            System.out.printf("%nСредняя цена: %.2f%n", avg);
            System.out.printf("Медианная цена: %.2f%n", median);
            System.out.printf("Разница между средней ценой и медианной: %.2f%n", Math.abs(avg - median));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}