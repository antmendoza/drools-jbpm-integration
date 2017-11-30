package com.odsc.antmendoza.model;

public class Client {

	public enum LEVEL {
		BRONZE, SILVER, GOLD;
	}

	private final String id;
	private final Integer personId;
	private LEVEL level;

	public Client(final Integer personId, final LEVEL level) {
		this.personId = personId;
		this.level = level;
		this.id = "CID" + personId;
	}


	public Integer getPersonId() {
		return personId;
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
		return "Client { personId=[" + personId + "]; level=[" + level + "]; id=[" + id + "]}";
	}
}
