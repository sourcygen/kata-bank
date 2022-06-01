package com.sourcygen.kata.bank.domain;

import com.sourcygen.kata.bank.helpers.DateHelper;

public class BankStatementSerializer implements IStatementSerializer {

	private static final String FORMAT = "%-12s | %-19s | %11s | %11s |\n";
	
	private static final String HEADERS = String.format(FORMAT, "Operation", "Date", "Amount","Balance");
	
	@Override
	public String serialize(IStatement statement) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(HEADERS);
		for (ITransaction transaction : statement.getTransactions()) {
			String operation = transaction.getOperation();
			String date = DateHelper.format(transaction.getDate());
			String amount = String.valueOf(transaction.getAmount());
			String balance = String.valueOf(transaction.getBalance());
			stringBuilder.append(String.format(FORMAT, operation, date, amount, balance));
		}
		return stringBuilder.toString();
	}

}