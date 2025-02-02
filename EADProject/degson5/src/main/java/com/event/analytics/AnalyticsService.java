package com.event.analytics;

import com.event.event.EventRepository;
import com.event.ticket.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalyticsService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public List<Map<String, Object>> getSalesRevenueOverTime(Date startDate, Date endDate) {
        List<Object[]> results = ticketRepository.findSalesRevenueOverTime(startDate, endDate);
        System.out.println("Results from DB: " + results);   
        List<Map<String, Object>> revenueData = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("date", result[0]);
            map.put("revenue", result[1]);
            revenueData.add(map);
        }

        return revenueData;
    }

      
    public List<Map<String, Object>> getEventCountByCategory() {
        List<Object[]> results = eventRepository.countEventsByCategory();
        List<Map<String, Object>> categoryData = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("category", row[0]);   
            map.put("eventCount", row[1]);   
            categoryData.add(map);
        }

        return categoryData;
    }
}
