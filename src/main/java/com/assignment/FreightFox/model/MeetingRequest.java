package com.assignment.FreightFox.model;


import java.time.LocalDateTime;
import java.util.List;

public class MeetingRequest {
    private Long ownerId;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Long> participantIds;
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	public List<Long> getParticipantIds() {
		return participantIds;
	}
	public void setParticipantIds(List<Long> participantIds) {
		this.participantIds = participantIds;
	}

    
}