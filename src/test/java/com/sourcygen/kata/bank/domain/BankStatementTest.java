package com.sourcygen.kata.bank.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BankStatementTest {
	
	private BankStatement statement;

	@BeforeEach
	void init() {
		statement = new BankStatement();
	}
	
	@Test
	void testGettingBalanceOfEmptyStatement() {
		// Arrange

		// Act
		final long balance = statement.getBalance();

		// Assert
		assertEquals(0, balance);
	}

	@Test
	void testMixingValidAndInvalidTransactions() {
		// Arrange
		ITransaction[] inputTransactions = { 
				new DepositTransaction(new Date(), 100),
				new DepositTransaction(new Date(), -200), 
				new DepositTransaction(new Date(), 300) 
		};

		// Act
		for (ITransaction transaction : inputTransactions) {
			try {
				statement.addTransaction(transaction);
			} catch (UnsupportedOperationException e) {
				// The exception must be triggered by invalid amount
				assertTrue(transaction.getAmount() <= 0);
			}
		}
		List<ITransaction> recordedTransactions = statement.getTransactions();

		// Assert
		assertEquals(400, statement.getBalance());
		assertEquals(2, recordedTransactions.size());
		assertEquals(inputTransactions[0], recordedTransactions.get(0));
		assertEquals(inputTransactions[2], recordedTransactions.get(1));
	}

	@Test
	void testGetTransactionsImmutability() {
		// Arrange
		ITransaction transaction = new DepositTransaction(new Date(), 100);

		// Assert
		assertThrows(UnsupportedOperationException.class, () -> {

			// Act
			statement.getTransactions().add(transaction);
		});

	}

}
