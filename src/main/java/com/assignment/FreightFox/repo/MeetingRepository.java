package com.assignment.FreightFox.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assignment.FreightFox.model.Employee;
import com.assignment.FreightFox.model.Meeting;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
 @Query("SELECT m FROM Meeting m WHERE m.owner = :employee AND ((m.startTime <= :endTime AND m.endTime >= :startTime) OR (m.startTime >= :startTime AND m.startTime < :endTime))")
 List<Meeting> findOverlappingMeetings(@Param("employee") Employee employee, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

 @Query("SELECT m FROM Meeting m WHERE m.owner = :employee AND m.startTime >= :start AND m.endTime <= :end")
 List<Meeting> findMeetingsInRange(@Param("employee") Employee employee, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}