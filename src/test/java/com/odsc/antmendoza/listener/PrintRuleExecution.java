package com.odsc.antmendoza.listener;

import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintRuleExecution implements AgendaFilter {

	private static final Logger logger = LoggerFactory.getLogger("");

	public boolean accept(Match match) {
		logger.info("");
		logger.info("Executing: " + match.getRule().getName());
		match.getObjects().forEach(object -> logger.info(object.toString()));
		return true;
	}
}