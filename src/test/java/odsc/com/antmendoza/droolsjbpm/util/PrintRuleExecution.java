package odsc.com.antmendoza.droolsjbpm.util;

import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintRuleExecution implements AgendaFilter {

	private static final Logger logger = LoggerFactory.getLogger(PrintRuleExecution.class);

	public boolean accept(Match match) {
		logger.info(match.getRule().getName());
		match.getObjects().forEach(object -> logger.info("   " + object.toString()));
		logger.info("");
		return true;
	}
}