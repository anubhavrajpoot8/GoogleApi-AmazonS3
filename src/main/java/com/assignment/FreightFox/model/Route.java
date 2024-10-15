package com.assignment.FreightFox.model;

public class Route {
	private Long id;

	private String fromPincode;
	private String toPincode;
	private Double distance;
	private Integer duration;

	private String routeInfo;

	// Constructors
	public Route() {
	}

	public Route(String fromPincode, String toPincode, Double distance, Integer duration, String routeInfo) {
		this.fromPincode = fromPincode;
		this.toPincode = toPincode;
		this.distance = distance;
		this.duration = duration;
		this.routeInfo = routeInfo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFromPincode() {
		return fromPincode;
	}

	public void setFromPincode(String fromPincode) {
		this.fromPincode = fromPincode;
	}

	public String getToPincode() {
		return toPincode;
	}

	public void setToPincode(String toPincode) {
		this.toPincode = toPincode;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getRouteInfo() {
		return routeInfo;
	}

	public void setRouteInfo(String routeInfo) {
		this.routeInfo = routeInfo;
	}

	@Override
	public String toString() {
		return "Route [id=" + id + ", fromPincode=" + fromPincode + ", toPincode=" + toPincode + ", distance="
				+ distance + ", duration=" + duration + ", routeInfo=" + routeInfo + "]";
	}

}
