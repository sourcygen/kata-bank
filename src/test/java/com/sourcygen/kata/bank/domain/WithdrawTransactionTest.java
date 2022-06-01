package com.sourcygen.kata.bank.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WithdrawTransactionTest {
	
	private static final long REF_BALANCE = 1000;
	
	private Date date;
	private long amount;
	private WithdrawTransaction transaction;
	

	@BeforeEach
	void init() {
		date = new Date();
		amount = Math.round(Math.random() * 1000);
		transaction = new WithdrawTransaction(date, amount);
	}
	
	@Test
	void testSuccessfulWithdrawExecution() {
		// Arrange

		// Act
		transaction.execute(REF_BALANCE);

		// Assert
		assertEquals(date, transaction.getDate());
		assertEquals(amount, transaction.getAmount());
		assertEquals(REF_BALANCE - amount, transaction.getBalance());
	}
	
	@Test
	void testMultipleWithdrawExecution() {
		// Arrange

		// Assert
		assertThrows(UnsupportedOperationException.class, () -> {

			// Act
			transaction.execute(REF_BALANCE);
			transaction.execute(REF_BALANCE);
		});
	}

	@Test
	void testInvalidWithdrawExecution() {
		// Arrange
		transaction = new WithdrawTransaction(date, -amount);

		// Assert
		assertThrows(UnsupportedOperationException.class, () -> {

			// Act
			transaction.execute(REF_BALANCE);
		});
	}
	
	@Test
	void testInsufficientRessourceWithdrawExecution() {
		// Arrange
		transaction = new WithdrawTransaction(date, REF_BALANCE+1);

		// Assert
		assertThrows(UnsupportedOperationException.class, () -> {

			// Act
			transaction.execute(REF_BALANCE);
		});
	}
	
	@Test
	void testWithdrawingWholeSavingExecution() {
		// Arrange
		transaction = new WithdrawTransaction(date, REF_BALANCE);
		
		// Act
		transaction.execute(REF_BALANCE);

		// Assert
		assertEquals(date, transaction.getDate());
		assertEquals(REF_BALANCE, transaction.getAmount());
		assertEquals(0, transaction.getBalance());
	}

	@Test
	void testDepositOperation() {
		// Arrange
		ITransaction transaction = new WithdrawTransaction(new Date(), 0);

		// Act
		final String operation = transaction.getOperation();
		
		// Assert
		assertEquals("Withdraw", operation);
	}
}
