package ua.nure.baranov.entity;

public class Request extends Entity {

	public Request(Integer id) {
		super(id);
	}

	private User operator;
	private RequestStatus status;
	private String text;
	
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
		return "Request [id=" + id + ", operator=" + operator + ", status=" + status + ", text=" + text + "]";
	}
	
}
