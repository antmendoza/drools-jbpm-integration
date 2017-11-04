package odsc.com.antmendoza.droolsjbpm.simpletest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.ObjectFilter;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import odsc.com.antmendoza.droolsjbpm.model.Person;
import odsc.com.antmendoza.droolsjbpm.model.PotencialClient;
import odsc.com.antmendoza.droolsjbpm.model.PotencialClient.LEVEL;

public class SimpleTest {

	private static final Logger logger = LoggerFactory.getLogger(SimpleTest.class);
	private KieContainer kContainer;

	public SimpleTest() {
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
	public void testPersonWithAge50IsLevel1() {

		final KieSession kSession = kContainer.newKieSession();
		final Person person = new Person("Person1", AGE._50.getValue(), ANNUAL_INCOME._38k.getValue());
		// insert an object in the engine
		kSession.insert(person);
		kSession.fireAllRules(new PrintBeforeExecution());

		final Collection<?> potencialClients = kSession.getObjects(new PotencialClientObjectFilter());
		assertThat(potencialClients.size(), equalTo(1));

		final PotencialClient potencialClient = (PotencialClient) potencialClients.iterator().next();
		assertThat(potencialClient.getPerson(), equalTo(person));
		assertThat(potencialClient.getLevel(), equalTo(LEVEL.BRONZE));
	}

	@Test
	public void testPersonWithAge35IsLevel2() {

		final KieSession kSession = kContainer.newKieSession();
		final Person person = new Person("Person1", AGE._25.getValue(), ANNUAL_INCOME._38k.getValue());
		kSession.insert(person);
		kSession.fireAllRules(new PrintBeforeExecution());

		final Collection<?> potencialClients = kSession.getObjects(new PotencialClientObjectFilter());
		assertThat(potencialClients.size(), equalTo(1));

		final PotencialClient potencialClient = (PotencialClient) potencialClients.iterator().next();
		assertThat(potencialClient.getPerson(), equalTo(person));
		assertThat(potencialClient.getLevel(), equalTo(LEVEL.SILVER));
	}

	@Test
	public void testPersonWithAnnulIncomeGreater100k() {

		final KieSession kSession = kContainer.newKieSession();
		final Person person = new Person("Person1", AGE._25.getValue(), ANNUAL_INCOME._110K.getValue());
		// insert an object in the engine

		kSession.insert(person);
		kSession.fireAllRules(new PrintBeforeExecution());

		final Collection<?> potencialClients = kSession.getObjects(new PotencialClientObjectFilter());
		assertThat(potencialClients.size(), equalTo(1));

		final PotencialClient potencialClient = (PotencialClient) potencialClients.iterator().next();
		assertThat(potencialClient.getPerson(), equalTo(person));
		assertThat(potencialClient.getLevel(), equalTo(LEVEL.GOLD));
	}

	private static class PrintBeforeExecution implements AgendaFilter {
		public boolean accept(Match match) {
			logger.info(match.getRule().getName());
			match.getObjects().forEach(object -> logger.info("   " + object.toString()));
			logger.info("");
			return true;
		}
	}

	private static class PotencialClientObjectFilter implements ObjectFilter {
		public boolean accept(Object object) {
			return object instanceof PotencialClient;
		}
	}

	private static enum AGE {
		_50(50), _25(25);

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
