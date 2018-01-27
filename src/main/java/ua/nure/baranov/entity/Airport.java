package ua.nure.baranov.entity;

@SuppressWarnings("serial")
public class Airport extends Entity {

	public Airport(Integer id) {
		super(id);
	}

	private String city;
	private String name;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Airport [id=" + id + ", city=" + city + ", name=" + name + "]";
	}	
	
}
