package ua.nure.baranov.entity;

@SuppressWarnings("serial")
public class City extends Entity{

	public City(Integer id) {
		super(id);
	}
	
	public City() {
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
		return name;
	}

}
