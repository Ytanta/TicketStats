package org.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Ticket;
import org.example.util.TimeUtils;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class TicketService {
    private final List<Ticket> tickets;

    public TicketService(String jsonPath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(jsonPath));
        Ticket[] ticketArray = mapper.treeToValue(root.get("tickets"), Ticket[].class);
        this.tickets = Arrays.asList(ticketArray);
    }

    public Map<String, Long> getMinFlightTimes(String from, String to) {
        Map<String, Long> result = new HashMap<>();
        for (Ticket t : tickets) {
            if (!t.origin.equals(from) || !t.destination.equals(to)) continue;

            long duration = TimeUtils.calculateFlightDuration(
                    t.departure_date, t.departure_time, t.arrival_date, t.arrival_time
            );

            result.merge(t.carrier, duration, Math::min);
        }
        return result;
    }

    public double getAveragePrice(String from, String to) {
        List<Integer> prices = getPrices(from, to);
        return prices.stream().mapToInt(Integer::intValue).average().orElse(0);
    }

    public double getMedianPrice(String from, String to) {
        List<Integer> prices = getPrices(from, to);
        Collections.sort(prices);
        int n = prices.size();
        if (n == 0) return 0;
        return n % 2 == 1
                ? prices.get(n / 2)
                : (prices.get(n / 2 - 1) + prices.get(n / 2)) / 2.0;
    }

    private List<Integer> getPrices(String from, String to) {
        return tickets.stream()
                .filter(t -> t.origin.equals(from) && t.destination.equals(to))
                .map(t -> t.price)
                .collect(Collectors.toList());
    }
}