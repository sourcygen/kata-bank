package com.sourcygen.kata.bank.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BankAccountTest {

	private BankAccount account;

	@Mock
	private IStatement statement;

	@BeforeEach
	void init() {
		account = new BankAccount(statement);
	}

	@Test	
	void testCreatingAccountWithNoStatement() {
		// Arrange
		
		// Assert
		assertThrows(UnsupportedOperationException.class, () -> {

			// Act
			account = new BankAccount(null);
		});
	}
	
	@Test
	void testGettingBalance() {
		// Arrange
		long expectedBalance = Math.round(Math.random() * 1000);
		when(statement.getBalance()).thenReturn(expectedBalance);

		// Act
		final long actualBalance = account.getBalance();
		
		// Assert
		assertEquals(expectedBalance, actualBalance);
		verify(statement, times(1)).getBalance();
	}
	
	private static Stream<Arguments> testMakingDepositArguments() {
		return Stream.of(
				Arguments.of(new Date(), 0, 0, true),
				Arguments.of(new Date(), -200, 0, true),
				Arguments.of(new Date(), 120, 120, false),
				Arguments.of(new Date(), 50, 50, false)
		);
	}
	
	@ParameterizedTest(name = "{index} : given a initial deposit of {1}, the new balance should be {2}")
	@MethodSource("testMakingDepositArguments")
	void testMakingDeposit(Date date, long amount, long expectedBalance, boolean isUnsupported) {
		// Arrange 
		if(isUnsupported) {
			doThrow(UnsupportedOperationException.class).when(statement).addTransaction(any(DepositTransaction.class));
		} else {
			doNothing().when(statement).addTransaction(any(DepositTransaction.class));
		}
		doReturn(expectedBalance).when(statement).getBalance();

		try {
			// Act
			account.makeDeposit(date, amount);

			// Assert
			if (isUnsupported) {
				fail("Previous action should trigger an exception");
			}
		} catch (Exception e) {
			// Assert
			if (!isUnsupported) {
				fail("Previous action should not trigger any exception");
			}
		}
		
		// Assert
		assertEquals(expectedBalance, account.getBalance());
		verify(statement, times(1)).getBalance();
		verify(statement, times(1)).addTransaction(any(DepositTransaction.class));
	}

}
