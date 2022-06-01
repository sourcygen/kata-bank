package com.sourcygen.kata.bank.domain;

import java.util.Date;

public interface ITransaction {

	void execute(long refBalance);
	
	String getOperation();
	
	Date getDate();

	long getAmount();
	
	long getBalance();
	
}
