package ua.nure.baranov.entity;

import java.util.List;

@SuppressWarnings("serial")
public class Team extends Entity {

	public Team(Integer id) {
		super(id);
	}

	public Team() {
	}

	User firstPilot;
	User secondPilot;
	User aeronavigator;
	List<User> attendants;
	
	@Override
	public String toString() {
		return "Team [id=" + id + ", firstPilot=" + firstPilot + ", secondPilot=" + secondPilot + ", aeronavigator="
				+ aeronavigator + ", attendants=" + attendants + "]";
	}

	public User getFirstPilot() {
		return firstPilot;
	}

	public void setFirstPilot(User firstPilot) {
		this.firstPilot = firstPilot;
	}

	public User getSecondPilot() {
		return secondPilot;
	}

	public void setSecondPilot(User secondPilot) {
		this.secondPilot = secondPilot;
	}

	public User getAeronavigator() {
		return aeronavigator;
	}

	public void setAeronavigator(User aeronavigator) {
		this.aeronavigator = aeronavigator;
	}

	public List<User> getAttendants() {
		return attendants;
	}

	public void setAttendants(List<User> attendants) {
		this.attendants = attendants;
	}
	
	
	/**
	 * Method that indicates readiness and completeness of the flight team. Flight team that is not complete cannot ...TODO: rewrite this 
	 * TODO: Notifications if the team is not yet ready on the flight date.
	 * @return
	 */
	public boolean getReadiness() {
		if(firstPilot!=null && secondPilot != null && aeronavigator != null && attendants != null && attendants.size() >=2) {
			return true;
		}
		return false;
	}
	
}
