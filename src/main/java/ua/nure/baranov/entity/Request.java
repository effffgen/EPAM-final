package ua.nure.baranov.entity;

import java.util.Date;

@SuppressWarnings("serial")
public class Request extends Entity {

	private User operator;
	private RequestStatus status;
	private String text;
	private Flight flight;
	private Date creationDate;
	
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public User getOperator() {
		return operator;
	}
	public void setOperator(User operator) {
		this.operator = operator;
	}
	public RequestStatus getStatus() {
		return status;
	}
	public void setStatus(RequestStatus status) {
		this.status = status;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "Request [id=" + id + ", operator=" + operator + ", status=" + status + ", text=" + text + ", flight="
				+ flight + ", creationDate=" + creationDate + "]";
	}
	

	
}
