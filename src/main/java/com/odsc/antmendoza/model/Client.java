package com.odsc.antmendoza.model;

public class Client {

	public enum LEVEL {
		BRONZE, SILVER, GOLD;
	}

	private final String id;
	private final Person person;
	private LEVEL level;

	public Client(final Person person, final LEVEL level) {
		this.person = person;
		this.level = level;
		this.id = "CID" + person.getId();
	}

	public Person getPerson() {
		return person;
	}

	public LEVEL getLevel() {
		return level;
	}

	public String getId() {
		return id;
	}

	public void setLevel(LEVEL level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "Client { person=[" + person + "]; level=[" + level + "]; id=[" + id + "]}";
	}
}
