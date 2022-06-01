package com.sourcygen.kata.bank.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sourcygen.kata.bank.helpers.DateHelper;

@ExtendWith(MockitoExtension.class)
class BankStatementSerializerTest {

	private BankStatementSerializer serializer;

	@Mock
	private IStatement statement;

	@BeforeEach
	void init() {
		serializer = new BankStatementSerializer();
	}

	@Test
	void testSerializingEmptyStatement() {
		// Arrange
		doReturn(Collections.EMPTY_LIST).when(statement).getTransactions();

		// Act
		final String history = serializer.serialize(statement);

		// Assert
		StringBuilder expected = new StringBuilder();
		expected.append("Operation    | Date                |      Amount |     Balance |\n");
		assertEquals(expected.toString(), history);
		verify(statement, times(1)).getTransactions();
	}

	@Test
	void testSerializingStatementWithSomeTransactions() throws ParseException {
		// Arrange
		List<ITransaction> transactions = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			ITransaction transaction = i % 2 == 0 ? Mockito.mock(DepositTransaction.class)
					: Mockito.mock(WithdrawTransaction.class);
			doReturn(i % 2 == 0 ? "Even" : "Odd").when(transaction).getOperation();
			doReturn(DateHelper.parse(String.format("2022-05-1%d 12:40:00", i))).when(transaction).getDate();
			doReturn(1000l - i * 100).when(transaction).getAmount();
			doReturn(1234l - i * 123).when(transaction).getBalance();
			transactions.add(transaction);
		}

		// Act
		doReturn(transactions).when(statement).getTransactions();
		final String history = serializer.serialize(statement);

		// Assert
		String format = "%-12s | %-19s | %11s | %11s |\n";
		StringBuilder expected = new StringBuilder();
		expected.append(String.format(format, "Operation", "Date", "Amount","Balance"));
		for (int i = 0; i < 10; i++) {
			String operation = i % 2 == 0 ? "Even" : "Odd";
			String date = String.format("2022-05-1%d 12:40:00", i);
			String amount = String.valueOf(1000l - i * 100);
			String balance = String.valueOf(1234l - i * 123);
			expected.append(String.format(format, operation, date, amount, balance));
		}
		assertEquals(expected.toString(), history);
		verify(statement, times(1)).getTransactions();
	}

}
