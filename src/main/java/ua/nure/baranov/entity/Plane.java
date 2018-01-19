package ua.nure.baranov.entity;

public class Plane extends Entity{
	public Plane(Integer id) {
		super(id);
	}
	
	public Plane() {
		
	}
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Plane [name=" + name + ", id=" + id + "]";
	}
	
	
	
}
