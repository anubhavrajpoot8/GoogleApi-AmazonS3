package com.assignment.FreightFox.model;

public class Pincode {
	private String code;

	private Double latitude;
	private Double longitude;

	private String polygon;

	// Constructors
	public Pincode() {
	}

	public Pincode(String code, Double latitude, Double longitude, String polygon) {
		this.code = code;
		this.latitude = latitude;
		this.longitude = longitude;
		this.polygon = polygon;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getPolygon() {
		return polygon;
	}

	public void setPolygon(String polygon) {
		this.polygon = polygon;
	}

	@Override
	public String toString() {
		return "Pincode [code=" + code + ", latitude=" + latitude + ", longitude=" + longitude + ", polygon=" + polygon
				+ "]";
	}
	
	
}
