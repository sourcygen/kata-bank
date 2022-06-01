package com.sourcygen.kata.bank.domain;

import java.util.Date;

public interface IAccount {

	long getBalance();
	
	void makeDeposit(Date date, long amount);
	
	void makeWithdraw(Date date, long amount);
	
	String getHistory(IStatementSerializer serializer);
}
