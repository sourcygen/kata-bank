package com.sourcygen.kata.bank.domain;

import java.util.Date;

public class WithdrawTransaction extends BankTransaction {

	public WithdrawTransaction(Date date, long amount) {
		super(date, amount);
	}

	@Override
	public void execute(long refBalance) {
		// Prevent transaction from being executed more than once or with invalid values
		if (newBalance != null || amount <= 0 || amount > refBalance) {
			throw new UnsupportedOperationException();
		}
		this.newBalance = refBalance - this.amount;
	}

	@Override
	public String getOperation() {
		return "Withdraw";
	}

}
