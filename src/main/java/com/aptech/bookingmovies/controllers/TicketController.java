package com.aptech.bookingmovies.controllers;

import com.aptech.bookingmovies.dtos.TicketDTO;
import com.aptech.bookingmovies.models.Ticket;
import com.aptech.bookingmovies.services.ticket.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/ticket")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/findTicketByCode(")
    public ResponseEntity<?> findTicketByCode(@RequestParam String code){
        Ticket ticket = ticketService.findByCode(code);
        return ResponseEntity.ok(ticket);
    }
    @GetMapping("/findTicketId")
    public ResponseEntity<?> findTicketId(@RequestParam int id) throws Exception{
        Ticket ticket = ticketService.findById(id);
        return ResponseEntity.ok(ticket);
    }
    @PostMapping("/createTicket")
    public ResponseEntity<?> createTicket(@Valid @RequestBody TicketDTO ticketDTO, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Ticket newTicket = ticketService.createTicket(ticketDTO);
            return ResponseEntity.ok(newTicket);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateTicket/{id}")
    public ResponseEntity<?> updateTicket(@Valid @RequestBody TicketDTO ticketDTO, @PathVariable("id") int id, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Ticket newTicket = ticketService.updateTicket(id,ticketDTO);
            return ResponseEntity.ok(newTicket);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteTicket/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable("id" )int id) throws Exception{
        return ResponseEntity.ok(ticketService.deleteTicket(id));
    }
}
