package com.event.attendee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.event.ticket.Ticket;

import java.util.List;

public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
    List<Attendee> findByEventId(Long eventId);
    List<Attendee> findByPurchaserEmail(String purchaserEmail);

    @Query("SELECT a.ticket FROM Attendee a WHERE a.purchaserEmail = :email")
    List<Ticket> findTicketsByPurchaserEmail(@Param("email") String email);
}



