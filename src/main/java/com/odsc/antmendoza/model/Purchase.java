package com.odsc.antmendoza.model;

import java.math.BigDecimal;
import java.util.Random;

public class Purchase {

	private final Integer id = new Random().nextInt();
	private final String clientId;
	private final BigDecimal amount;

	public Purchase(final String clientId, final BigDecimal amount) {
		this.clientId = clientId;
		this.amount = amount;
	}

	public Integer getId() {
		return id;
	}

	public String getClientId() {
		return clientId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "Purchase { " + " id=[" + id + "];" + " clientId=[" + clientId + "];" + " amount=[" + amount + "]}";
	}
}
