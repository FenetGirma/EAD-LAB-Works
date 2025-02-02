package com.event.attendee;

import com.event.event.Event;
import com.event.ticket.Ticket;
import jakarta.persistence.*;

@Entity
@Table(name = "attendees")
public class Attendee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    
    @Column(name = "qr_code", columnDefinition = "TEXT")
    private String qrCode;

    public String getQrCode() { return qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;     

    @Column(name = "purchaser_email")
    private String purchaserEmail;     

       
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }

    public Ticket getTicket() { return ticket; }
    public void setTicket(Ticket ticket) { this.ticket = ticket; }

    public String getPurchaserEmail() { return purchaserEmail; }
    public void setPurchaserEmail(String purchaserEmail) { this.purchaserEmail = purchaserEmail; }
}
