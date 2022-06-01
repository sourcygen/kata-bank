package com.sourcygen.kata.bank.domain;

import java.util.Date;

public class BankAccount implements IAccount {

	private final IStatement statement;

	public BankAccount(IStatement statement) {
		// Prevent account from being created with invalid statement
		if (statement == null) {
			throw new UnsupportedOperationException();
		}
		this.statement = statement;
	}

	@Override
	public long getBalance() {
		return this.statement.getBalance();
	}

	@Override
	public void makeDeposit(Date date, long amount) {
		this.statement.addTransaction(new DepositTransaction(date, amount));
	}

}
