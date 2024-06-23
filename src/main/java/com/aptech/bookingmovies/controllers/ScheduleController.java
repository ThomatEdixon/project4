package com.aptech.bookingmovies.controllers;

import com.aptech.bookingmovies.dtos.ScheduleDTO;
import com.aptech.bookingmovies.models.Schedule;
import com.aptech.bookingmovies.services.schedule.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("${api.prefix}/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    @GetMapping("/findScheduleByMovieName")
    public ResponseEntity<?> findScheduleByMovieName(@RequestParam String name) throws Exception{
        List<Schedule> schedule = scheduleService.findByMovieName(name);
        return ResponseEntity.ok(schedule);
    }
    @GetMapping("/findScheduleId")
    public ResponseEntity<?> findScheduleId(@RequestParam int id) throws Exception{
        Schedule schedule = scheduleService.findById(id);
        return ResponseEntity.ok(schedule);
    }
    @GetMapping("")
    public ResponseEntity<?> listSchedule(){
        return ResponseEntity.ok(scheduleService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSchedule(@Valid @RequestBody ScheduleDTO scheduleDTO, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Schedule schedule = scheduleService.createSchedule(scheduleDTO);
            return ResponseEntity.ok(schedule);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSchedule(@Valid @RequestBody ScheduleDTO scheduleDTO, @PathVariable("id") int id, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Schedule schedule = scheduleService.updateSchedule(id,scheduleDTO);
            return ResponseEntity.ok(schedule);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteSchedule/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable("id" )int id) throws Exception{
        return ResponseEntity.ok(scheduleService.deleteSchedule(id));
    }
}
