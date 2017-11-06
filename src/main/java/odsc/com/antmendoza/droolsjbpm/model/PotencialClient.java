package odsc.com.antmendoza.droolsjbpm.model;

public class PotencialClient {

	public enum LEVEL {
		BRONZE, SILVER, GOLD;
	}

	private final String id;
	private final Person person;
	private final LEVEL level;

	public PotencialClient(final Person person, final LEVEL level) {
		this.person = person;
		this.level = level;
		id = "CID" + person.getId();
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

	@Override
	public String toString() {

		return "PotencialClient { " + " person=[" + person + "];" + " level=[" + level + "]" + "}";
	}
}
