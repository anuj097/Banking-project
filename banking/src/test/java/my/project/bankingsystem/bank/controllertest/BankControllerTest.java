package my.project.bankingsystem.bank.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import my.project.bankingsystem.bank.controller.BankingController;
import my.project.bankingsystem.bank.dtos.AccountResponse;
import my.project.bankingsystem.bank.dtos.AccountType;
import my.project.bankingsystem.bank.service.BankingService;

@ExtendWith(MockitoExtension.class)
public class BankControllerTest {

	@InjectMocks
	private BankingController accountController;
	
	@Mock
	private BankingService bankService;
	
	@Test
    void testGetAccountDetails_Success() {
        // Arrange
        String accountNumber = "1234567890";
        AccountResponse mockResponse = new AccountResponse();
        mockResponse.setAccountNumber(accountNumber);
        mockResponse.setAccountStatus("ACTIVE");
        mockResponse.setAccountType(AccountType.SAVING_ACCOUNT);
        mockResponse.setAvailableBalance(new BigDecimal(1000));
        mockResponse.setUserId(122345l);

        Mockito.when(bankService.getAccountDetails(accountNumber)).thenReturn(mockResponse);

        // Act
        ResponseEntity<AccountResponse> response = accountController.getAccountDetails(accountNumber);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockResponse, response.getBody());
    }
	
	@Test
    void testGetAccountDetails_NoContent() {
        // Arrange
        String accountNumber = "0000000000";
        Mockito.when(bankService.getAccountDetails(accountNumber)).thenReturn(null);

        // Act
        ResponseEntity<AccountResponse> response = accountController.getAccountDetails(accountNumber);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}
