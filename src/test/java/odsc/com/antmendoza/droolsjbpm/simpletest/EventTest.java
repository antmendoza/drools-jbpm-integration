package odsc.com.antmendoza.droolsjbpm.simpletest;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.SessionPseudoClock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import odsc.com.antmendoza.droolsjbpm.model.Client;
import odsc.com.antmendoza.droolsjbpm.model.Client.LEVEL;
import odsc.com.antmendoza.droolsjbpm.model.Person;
import odsc.com.antmendoza.droolsjbpm.model.Purchase;
import odsc.com.antmendoza.droolsjbpm.service.EmailService;
import odsc.com.antmendoza.droolsjbpm.util.PrintBeforeExecution;

public class EventTest {

	private static final Logger logger = LoggerFactory.getLogger(EventTest.class);
	private KieContainer kContainer;

	public EventTest() {
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
	public void testSendEmailTwoPurchasesInLessThan24h() {
		final KieSession kSession = kContainer.newKieSession();

		final EmailService emailService = Mockito.spy(EmailService.class);
		kSession.setGlobal("emailService", emailService);
		
		final Client clientGlod = new Client(new Person("", 50, 200L), LEVEL.GOLD);
		final Purchase purchase200 = new Purchase(clientGlod.getId(), new BigDecimal(200));

		
		kSession.insert(clientGlod);
		kSession.insert(purchase200);
		kSession.fireAllRules(new PrintBeforeExecution());

		// Mock the time,moving the time 10h forward
		final SessionPseudoClock pseudoClock = kSession.getSessionClock();
		pseudoClock.advanceTime(10, TimeUnit.HOURS);

		final Purchase purchase400 = new Purchase(clientGlod.getId(), new BigDecimal(400));
		kSession.insert(purchase400);
		kSession.fireAllRules(new PrintBeforeExecution());

		Mockito.verify(emailService, VerificationModeFactory.atLeastOnce()).sendEmail(clientGlod.getId());

	}

	

	@Test
	public void testSendEmailTwoPurchasesInLessThan24hDifferentClients() {
		final KieSession kSession = kContainer.newKieSession();

		final EmailService emailService = Mockito.spy(EmailService.class);
		kSession.setGlobal("emailService", emailService);
		
		final Client clientGlod = new Client(new Person("", 50, 200L), LEVEL.GOLD);
		final Purchase purchase200 = new Purchase(clientGlod.getId(), new BigDecimal(200));

		
		kSession.insert(clientGlod);
		kSession.insert(purchase200);
		kSession.fireAllRules(new PrintBeforeExecution());

		// Mock the time,moving the time 10h forward
		final SessionPseudoClock pseudoClock = kSession.getSessionClock();
		pseudoClock.advanceTime(10, TimeUnit.HOURS);

		
		final Client clientGlodB = new Client(new Person("", 50, 200L), LEVEL.GOLD);
		final Purchase purchase400 = new Purchase(clientGlodB.getId(), new BigDecimal(400));
		kSession.insert(purchase400);
		kSession.fireAllRules(new PrintBeforeExecution());

		Mockito.verify(emailService, VerificationModeFactory.times(0)).sendEmail(clientGlod.getId());

	}

	
	
	
	@Test
	public void testNotSendEmailTwoPurchasesInMoreThan24h() {
		final KieSession kSession = kContainer.newKieSession();

		final EmailService emailService = Mockito.spy(EmailService.class);
		kSession.setGlobal("emailService", emailService);
		
		final Client clientGlod = new Client(new Person("", 50, 200L), LEVEL.GOLD);
		final Purchase purchase200 = new Purchase(clientGlod.getId(), new BigDecimal(200));

		
		kSession.insert(clientGlod);
		kSession.insert(purchase200);
		kSession.fireAllRules(new PrintBeforeExecution());

		// Mock the time,moving the time 25h forward
		final SessionPseudoClock pseudoClock = kSession.getSessionClock();
		pseudoClock.advanceTime(25, TimeUnit.HOURS);

		final Purchase purchase400 = new Purchase(clientGlod.getId(), new BigDecimal(400));
		kSession.insert(purchase400);
		kSession.fireAllRules(new PrintBeforeExecution());

		Mockito.verify(emailService, VerificationModeFactory.times(0)).sendEmail(clientGlod.getId());
	}
	
	
	@Test
	public void testSendEmailTwoPurchasesInLessThan24hClientBRONZE() {
		final KieSession kSession = kContainer.newKieSession();

		final EmailService emailService = Mockito.spy(EmailService.class);
		kSession.setGlobal("emailService", emailService);
		
		final Person richPerson = new Person("", 50, 200000L);
		final Client clientGlod = new Client(richPerson, LEVEL.BRONZE);
		final Purchase purchase200 = new Purchase(clientGlod.getId(), new BigDecimal(200));

		kSession.insert(richPerson);
		kSession.insert(purchase200);
		kSession.fireAllRules(new PrintBeforeExecution());

		// Mock the time,moving the time 10h forward
		final SessionPseudoClock pseudoClock = kSession.getSessionClock();
		pseudoClock.advanceTime(10, TimeUnit.HOURS);

		final Purchase purchase400 = new Purchase(clientGlod.getId(), new BigDecimal(400));
		kSession.insert(purchase400);
		kSession.fireAllRules(new PrintBeforeExecution());

		Mockito.verify(emailService, VerificationModeFactory.atLeastOnce()).sendEmail(clientGlod.getId());

	}

	
	
	

}
