package com.sourcygen.kata.bank.domain;

import java.util.List;

public interface IStatement {
	
	long getBalance();

	void addTransaction(ITransaction transaction);
	
	List<ITransaction> getTransactions();
}
