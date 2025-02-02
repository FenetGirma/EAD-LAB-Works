package com.event.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    @Query("SELECT e.category, COUNT(e) FROM Event e GROUP BY e.category")
    List<Object[]> countEventsByCategory();
}
