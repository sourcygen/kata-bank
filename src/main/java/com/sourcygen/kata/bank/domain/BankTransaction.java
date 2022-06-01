package com.sourcygen.kata.bank.domain;

import java.util.Date;

public abstract class BankTransaction implements ITransaction {

	protected final Date date;

	protected final long amount;

	protected Long newBalance = null;

	public BankTransaction(Date date, long amount) {
		this.date = date;
		this.amount = amount;
	}

	@Override
	public Date getDate() {
		return this.date;
	}

	@Override
	public long getAmount() {
		return this.amount;
	}

	@Override
	public long getBalance() {
		// Prevent balance from being requested from an orphan transaction
		if (newBalance == null) {
			throw new UnsupportedOperationException();
		}
		return this.newBalance;
	}

}
