package com.aptech.bookingmovies.services.ticket;

import com.aptech.bookingmovies.dtos.TicketDTO;
import com.aptech.bookingmovies.exceptions.DataNotFoundException;
import com.aptech.bookingmovies.models.Schedule;
import com.aptech.bookingmovies.models.Seat;
import com.aptech.bookingmovies.models.Ticket;
import com.aptech.bookingmovies.repositories.ScheduleRepository;
import com.aptech.bookingmovies.repositories.SeatRepository;
import com.aptech.bookingmovies.repositories.TicketRepository;
import com.aptech.bookingmovies.services.ticket.ITicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

@Service
@RequestMapping("${api.prefix}/ticket")
@RequiredArgsConstructor
public class TicketService implements ITicketService {
    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public Ticket findByCode(String code) {
        return ticketRepository.findByCode(code);
    }

    @Override
    public Ticket createTicket(TicketDTO ticketDTO) throws Exception{
        Schedule existingSchedule = scheduleRepository.findById(ticketDTO.getScheduleId())
                .orElseThrow(()-> new DataNotFoundException("Can not found schedule"));
        Seat existingSeat = seatRepository.findById(ticketDTO.getSeatId())
                .orElseThrow(()-> new DataNotFoundException("Can not found seat"));
        Ticket newTicket = Ticket.builder()
                .priceTicket(ticketDTO.getPriceTicket())
                .schedule(existingSchedule)
                .seat(existingSeat)
                .code(generateTicketCode())
                .isActive(true)
                .build();
        return ticketRepository.save(newTicket);
    }

    @Override
    public Ticket updateTicket(int id, TicketDTO ticketDTO) throws Exception{
        Schedule existingSchedule = scheduleRepository.findById(ticketDTO.getScheduleId())
                .orElseThrow(()-> new DataNotFoundException("Can not found schedule"));
        Seat existingSeat = seatRepository.findById(ticketDTO.getSeatId())
                .orElseThrow(()-> new DataNotFoundException("Can not found seat"));
        Ticket existingTicket = ticketRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Can not found ticket"));
        existingTicket.setPriceTicket(ticketDTO.getPriceTicket());
        existingTicket.setSeat(existingSeat);
        existingTicket.setSchedule(existingSchedule);
        return ticketRepository.save(existingTicket);
    }

    @Override
    public String deleteTicket(int id) throws Exception{
        Ticket existingTicket = ticketRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Can not found ticket"));
        ticketRepository.delete(existingTicket);
        return "Delete Successfully";
    }

    @Override
    public Ticket findById(int id) throws Exception {
        Ticket existingTicket = ticketRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Can not found ticket"));
        return existingTicket;
    }

    public String generateTicketCode(){
        String ticketCode="TK";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 13; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        ticketCode +=sb.toString();
        return ticketCode;
    }
}
