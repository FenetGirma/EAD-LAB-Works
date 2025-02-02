package com.event.analytics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/sales-revenue")
    public List<Map<String, Object>> getSalesRevenue() {
        return analyticsService.getSalesRevenueOverTime(null, null);  
    }

     
    @GetMapping("/events-by-category")
    public List<Map<String, Object>> getEventsByCategory() {
        return analyticsService.getEventCountByCategory();
    }
}
