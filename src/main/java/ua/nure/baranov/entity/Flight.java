package ua.nure.baranov.entity;

import java.util.Date;

@SuppressWarnings("serial")
public class Flight extends Entity{
	
	public Flight(Integer id) {
		super(id);
	}
	public Flight() {
		super(null);
	}
	private String name;
	private City destination;
	private City depart;
	private Date flightDate;
	private Plane plane;
	private Team flightTeam;


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public City getDestination() {
		return destination;
	}
	public void setDestination(City destination) {
		this.destination = destination;
	}
	public City getDepart() {
		return depart;
	}
	public void setDepart(City depart) {
		this.depart = depart;
	}
	public Date getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}
	public Plane getPlane() {
		return plane;
	}
	public void setPlane(Plane plane) {
		this.plane = plane;
	}
	public Team getFlightTeam() {
		return flightTeam;
	}
	public void setFlightTeam(Team flightTeam) {
		this.flightTeam = flightTeam;
	}
	
	@Override
	public String toString() {
		return "Voyage [id=" + id + ", destination=" + destination + ", depart=" + depart + ", flightDate=" + flightDate
				+ ", plane=" + plane + ", flightTeam=" + flightTeam + "]";
	}
	

}
