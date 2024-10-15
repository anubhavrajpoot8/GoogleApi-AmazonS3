package com.assignment.FreightFox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.FreightFox.model.Employee;
import com.assignment.FreightFox.model.Meeting;
import com.assignment.FreightFox.repo.EmployeeRepository;
import com.assignment.FreightFox.repo.MeetingRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class CalendarService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Transactional
    public Meeting bookMeeting(Long ownerId, String title, LocalDateTime startTime, LocalDateTime endTime, List<Long> participantIds) {
        Employee owner = employeeRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        List<Employee> participants = employeeRepository.findAllById(participantIds);

        // Check for conflicts
        List<Meeting> conflicts = meetingRepository.findOverlappingMeetings(owner, startTime, endTime);
        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Meeting conflicts with existing meetings");
        }

        Meeting meeting = new Meeting();
        meeting.setTitle(title);
        meeting.setStartTime(startTime);
        meeting.setEndTime(endTime);
        meeting.setOwner(owner);
        meeting.setParticipants(participants);

        return meetingRepository.save(meeting);
    }

    public List<LocalDateTime> findFreeSlots(Long employee1Id, Long employee2Id, int durationMinutes) {
        Employee employee1 = employeeRepository.findById(employee1Id)
                .orElseThrow(() -> new RuntimeException("Employee 1 not found"));
        Employee employee2 = employeeRepository.findById(employee2Id)
                .orElseThrow(() -> new RuntimeException("Employee 2 not found"));

        LocalDateTime start = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        LocalDateTime end = start.plusDays(7); // Look for free slots in the next 7 days

        List<Meeting> meetings1 = meetingRepository.findMeetingsInRange(employee1, start, end);
        List<Meeting> meetings2 = meetingRepository.findMeetingsInRange(employee2, start, end);

        Set<LocalDateTime> busySlots = new HashSet<>();
        addBusySlots(busySlots, meetings1);
        addBusySlots(busySlots, meetings2);

        List<LocalDateTime> freeSlots = new ArrayList<>();
        LocalDateTime current = start;

        while (current.isBefore(end)) {
            if (!busySlots.contains(current)) {
                LocalDateTime slotEnd = current.plusMinutes(durationMinutes);
                boolean slotIsFree = true;
                for (LocalDateTime time = current; time.isBefore(slotEnd); time = time.plusMinutes(30)) {
                    if (busySlots.contains(time)) {
                        slotIsFree = false;
                        break;
                    }
                }
                if (slotIsFree) {
                    freeSlots.add(current);
                }
            }
            current = current.plusMinutes(30);
        }

        return freeSlots;
    }
    private void addBusySlots(Set<LocalDateTime> busySlots, List<Meeting> meetings) {
        for (Meeting meeting : meetings) {
            LocalDateTime time = meeting.getStartTime();
            while (time.isBefore(meeting.getEndTime())) {
                busySlots.add(time);
                time = time.plusMinutes(30);
            }
        }
    }
    public List<Employee> checkConflicts(Long ownerId, LocalDateTime startTime, LocalDateTime endTime, List<Long> participantIds) {
        List<Employee> participants = employeeRepository.findAllById(participantIds);
        List<Employee> conflictingParticipants = new ArrayList<>();

        for (Employee participant : participants) {
            List<Meeting> conflicts = meetingRepository.findOverlappingMeetings(participant, startTime, endTime);
            if (!conflicts.isEmpty()) {
                conflictingParticipants.add(participant);
            }
        }

        return conflictingParticipants;
    }
}