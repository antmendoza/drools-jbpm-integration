package odsc.com.antmendoza.droolsjbpm.simpletest;

import static org.junit.Assert.assertEquals;

import org.jbpm.process.instance.ProcessInstance;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessTest {

	private static final Logger logger = LoggerFactory.getLogger(ProcessTest.class);
	private KieContainer kContainer;

	public ProcessTest() {
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
	public void testSimpleProcess() {

		final KieSession kSession = kContainer.newKieSession();
		final org.kie.api.runtime.process.ProcessInstance processInstace = kSession.startProcess("simple");

		assertEquals(ProcessInstance.STATE_COMPLETED, processInstace.getState());

	}

}
