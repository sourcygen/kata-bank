package com.sourcygen.kata.bank.application;

import java.text.ParseException;

import com.sourcygen.kata.bank.domain.BankAccount;
import com.sourcygen.kata.bank.domain.BankStatement;
import com.sourcygen.kata.bank.domain.BankStatementSerializer;
import com.sourcygen.kata.bank.domain.IAccount;
import com.sourcygen.kata.bank.domain.IStatement;
import com.sourcygen.kata.bank.domain.IStatementSerializer;
import com.sourcygen.kata.bank.helpers.DateHelper;

public class App {

	public static void main(String[] args) throws ParseException {
		final IStatement statement = new BankStatement();
		final IStatementSerializer serializer = new BankStatementSerializer();
		final IAccount account = new BankAccount(statement);
		
		account.makeDeposit(DateHelper.parse("2022-05-01 09:40:02"), 1500);
		account.makeDeposit(DateHelper.parse("2022-05-02 09:40:02"), 1500);
		account.makeWithdraw(DateHelper.parse("2022-05-03 09:40:02"), 1000);
		account.makeDeposit(DateHelper.parse("2022-05-04 09:40:02"), 3000);
		account.makeDeposit(DateHelper.parse("2022-05-05 09:40:02"), 2500);
		account.makeWithdraw(DateHelper.parse("2022-05-06 09:40:02"), 7500);
		
		System.out.println(account.getHistory(serializer));
	}

}
