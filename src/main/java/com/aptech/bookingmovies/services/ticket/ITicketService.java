package com.aptech.bookingmovies.services.ticket;

import com.aptech.bookingmovies.dtos.TicketDTO;
import com.aptech.bookingmovies.models.Ticket;

public interface ITicketService {
    Ticket findByCode(String code);
    Ticket createTicket(TicketDTO ticketDTO)throws Exception;
    Ticket updateTicket(int id, TicketDTO ticketDTO) throws Exception;;

    String deleteTicket(int id)throws Exception;
    Ticket findById(int id) throws Exception;
}
