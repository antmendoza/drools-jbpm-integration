package com.odsc.antmendoza.listener;

import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebugSessionEventListener implements RuleRuntimeEventListener{
	private static final Logger logger = LoggerFactory.getLogger("");

	@Override
	public void objectInserted(ObjectInsertedEvent event) {
		logger.info("Object Inserted: " + event.getObject());
	}

	@Override
	public void objectUpdated(ObjectUpdatedEvent event) {
		logger.info("Object Updated: ");
		logger.info("	Object Updated: " + event.getObject());

	}

	@Override
	public void objectDeleted(ObjectDeletedEvent event) {
		logger.info("Object Deleted: " + event.getOldObject());
		
	}

}
