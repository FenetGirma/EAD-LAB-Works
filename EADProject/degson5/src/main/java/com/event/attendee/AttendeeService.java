package com.event.attendee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.event.QRUtility;
import com.event.event.Event;
import com.event.event.EventRepository;
import com.event.ticket.Ticket;
import com.event.ticket.TicketRepository;

import java.util.List;

@Service
public class AttendeeService {

    @Autowired
    private AttendeeRepository attendeeRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private EventRepository eventRepository;

    public List<Attendee> getAllAttendeesForEvent(Long eventId) {
        return attendeeRepository.findByEventId(eventId);
    }

    public List<Attendee> getAllAttendees() {
        return attendeeRepository.findAll();
    }

    public Attendee saveAttendee(Attendee attendee) {
        return attendeeRepository.save(attendee);
    }

    public void deleteAttendee(Long attendeeId) {
        attendeeRepository.deleteById(attendeeId);
    }

    public List<Attendee> getAttendeesByPurchaserEmail(String email) {
        return attendeeRepository.findByPurchaserEmail(email);
    }

    public Attendee saveAttendeeWithQRCode(Attendee attendee) {
           
        Event event = eventRepository.findById(attendee.getEvent().getId())
                .orElseThrow(() -> new RuntimeException("Event not found"));
        Ticket ticket = ticketRepository.findById(attendee.getTicket().getId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

           
        attendee.setEvent(event);
        attendee.setTicket(ticket);

           
        String qrData = String.format("Name: %s, Email: %s, Event: %s, Ticket: %s",
                attendee.getName(),
                attendee.getEmail(),
                event.getName(),
                ticket.getTicketType());

           
        String qrCode = QRUtility.generateQRCode(qrData);

           
        attendee.setQrCode(qrCode);

           
        return attendeeRepository.save(attendee);
    }
}