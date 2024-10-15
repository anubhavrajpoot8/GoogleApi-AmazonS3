package com.assignment.FreightFox.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.assignment.FreightFox.model.Employee;
import com.assignment.FreightFox.model.Meeting;
import com.assignment.FreightFox.model.MeetingRequest;
import com.assignment.FreightFox.service.CalendarService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @PostMapping("/book")
    public ResponseEntity<Meeting> bookMeeting(@RequestBody MeetingRequest request) {
        Meeting meeting = calendarService.bookMeeting(
            request.getOwnerId(),
            request.getTitle(),
            request.getStartTime(),
            request.getEndTime(),
            request.getParticipantIds()
        );
        return ResponseEntity.ok(meeting);
    }

    @GetMapping("/free-slots")
    public ResponseEntity<List<LocalDateTime>> findFreeSlots(
            @RequestParam Long employee1Id,
            @RequestParam Long employee2Id,
            @RequestParam int duration) {
        List<LocalDateTime> freeSlots = calendarService.findFreeSlots(employee1Id, employee2Id, duration);
        return ResponseEntity.ok(freeSlots);
    }

    @PostMapping("/check-conflicts")
    public ResponseEntity<List<Employee>> checkConflicts(@RequestBody MeetingRequest request) {
        List<Employee> conflictingParticipants = calendarService.checkConflicts(
            request.getOwnerId(),
            request.getStartTime(),
            request.getEndTime(),
            request.getParticipantIds()
        );
        return ResponseEntity.ok(conflictingParticipants);
    }
}