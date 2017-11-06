package odsc.com.antmendoza.droolsjbpm.model;

import java.util.Random;

public class Person {

	private final Integer id = new Random().nextInt();
	private final String name;
	private final Integer age;
	private final Long annualIncome;

	public Person(final String name, final Integer age, final Long annualIncome) {
		this.name = name;
		this.age = age;
		this.annualIncome = annualIncome;
	}

	public Integer getId() {
		return id;
	}
	public Integer getAge() {
		return age;
	}

	public String getName() {
		return name;
	}

	public Long getAnnualIncome() {
		return annualIncome;
	}

	@Override
	public String toString() {

		return "Person { " + " name=[" + name + "];" + " age=[" + age + "];" + " annualIncome=[" + annualIncome + "];"
				+ "}";
	}
}
