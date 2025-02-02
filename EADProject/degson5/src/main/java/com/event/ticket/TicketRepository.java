package com.event.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByEventId(Long eventId);

    @Query("SELECT t.salesStartDate, SUM(t.price * t.quantitySold) " +
           "FROM Ticket t " +
           "WHERE t.salesStartDate BETWEEN :startDate AND :endDate " +
           "GROUP BY t.salesStartDate " +
           "ORDER BY t.salesStartDate ASC")
    List<Object[]> findSalesRevenueOverTime(
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate
    );
}
