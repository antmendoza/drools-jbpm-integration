package com.odsc.antmendoza.simpletest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.ObjectFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odsc.antmendoza.model.Client;
import com.odsc.antmendoza.model.Person;
import com.odsc.antmendoza.model.Client.LEVEL;
import com.odsc.antmendoza.util.PrintRuleExecution;

public class DetermineClientTypeTest {

	private static final Logger logger = LoggerFactory.getLogger(DetermineClientTypeTest.class);
	private KieContainer kContainer;

	public DetermineClientTypeTest() {
		final KieServices ks = KieServices.Factory.get();
		kContainer = ks.getKieClasspathContainer();
	}

	@Rule
	public TestName testName = new TestName();

	@Before
	public void printTestName() {

		logger.info("");
		logger.info("");
		logger.info("******************************************");
		logger.info(testName.getMethodName());

	}

	@Test
	// between 18 and 25
	public void testPersonBetween18And25IsClientBRONZE() {

		// Entry point
		final KieSession kSession = kContainer.newKieSession();

		// Prepare test
		final Person person = new Person("Person1", AGE._20.getValue(), ANNUAL_INCOME._38k.getValue());
		kSession.insert(person);

		// Execute
		kSession.fireAllRules(new PrintRuleExecution());

		// assert that there is one only client
		final Collection<?> Clients = kSession.getObjects(new ClientObjectFilter());
		assertThat(Clients.size(), equalTo(1));

		// and the level is BRONZE
		final Client client = (Client) Clients.iterator().next();
		assertThat(client.getPerson(), equalTo(person));
		assertThat(client.getLevel(), equalTo(LEVEL.BRONZE));

	}

	@Test
	public void testPersonWithAge35IsClientSILVER() {

		final KieSession kSession = kContainer.newKieSession();
		final Person person = new Person("Person1", AGE._25.getValue(), ANNUAL_INCOME._38k.getValue());
		kSession.insert(person);
		kSession.fireAllRules(new PrintRuleExecution());

		final Collection<?> Clients = kSession.getObjects(new ClientObjectFilter());
		assertThat(Clients.size(), equalTo(1));

		final Client client = (Client) Clients.iterator().next();
		assertThat(client.getPerson(), equalTo(person));
		assertThat(client.getLevel(), equalTo(LEVEL.SILVER));
	}

	@Test
	public void testPersonWithAnnulIncomeGreater100kIsClientGOLD() {

		final KieSession kSession = kContainer.newKieSession();
		final Person person = new Person("Person1", AGE._25.getValue(), ANNUAL_INCOME._110K.getValue());
		// insert an object in the engine

		kSession.insert(person);
		kSession.fireAllRules(new PrintRuleExecution());

		final Collection<?> Clients = kSession.getObjects(new ClientObjectFilter());
		assertThat(Clients.size(), equalTo(1));

		final Client client = (Client) Clients.iterator().next();
		assertThat(client.getPerson(), equalTo(person));
		assertThat(client.getLevel(), equalTo(LEVEL.GOLD));
	}

	private static class ClientObjectFilter implements ObjectFilter {
		public boolean accept(Object object) {
			return object instanceof Client;
		}
	}

	private static enum AGE {
		_20(20), _25(25);

		private final int value;

		private AGE(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	private static enum ANNUAL_INCOME {
		_110K(110000L), _38k(38000L);

		private final long value;

		private ANNUAL_INCOME(long value) {
			this.value = value;
		}

		public long getValue() {
			return value;
		}
	}
}
