package com.sourcygen.kata.bank.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DepositTransactionTest {
	
	private static final long REF_BALANCE = 1000;
	
	private Date date;
	private long amount;
	private DepositTransaction transaction;
	

	@BeforeEach
	void init() {
		date = new Date();
		amount = Math.round(Math.random() * 1000);
		transaction = new DepositTransaction(date, amount);
	}
	
	@Test
	void testSuccessfulDepositExecution() {
		// Arrange

		// Act
		transaction.execute(REF_BALANCE);

		// Assert
		assertEquals(date, transaction.getDate());
		assertEquals(amount, transaction.getAmount());
		assertEquals(REF_BALANCE + amount, transaction.getBalance());
	}
	
	@Test
	void testMultipleDepositExecution() {
		// Arrange

		// Assert
		assertThrows(UnsupportedOperationException.class, () -> {

			// Act
			transaction.execute(REF_BALANCE);
			transaction.execute(REF_BALANCE);
		});
	}

	@Test
	void testInvalidDepositExecution() {
		// Arrange
		transaction = new DepositTransaction(date, -amount);

		// Assert
		assertThrows(UnsupportedOperationException.class, () -> {

			// Act
			transaction.execute(REF_BALANCE);
		});
	}

}
