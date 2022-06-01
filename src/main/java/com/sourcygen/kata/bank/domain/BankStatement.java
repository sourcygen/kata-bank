package com.sourcygen.kata.bank.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BankStatement implements IStatement {

	private final List<ITransaction> transactions = new ArrayList<>();

	@Override
	public long getBalance() {
		long balance = 0;
		if(transactions.size() > 0) {
			balance = transactions.get(transactions.size()-1).getBalance();
		}
		return balance;
	}
	
	@Override
	public void addTransaction(ITransaction transaction) {
		transaction.execute(this.getBalance());
		transactions.add(transaction);
	}

	@Override
	public List<ITransaction> getTransactions() {
		return Collections.unmodifiableList(this.transactions);
	}

	

}
