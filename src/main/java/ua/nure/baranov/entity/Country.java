package ua.nure.baranov.entity;

@SuppressWarnings("serial")
public class Country extends Entity{
	public Country(Integer id) {
		super(id);
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
