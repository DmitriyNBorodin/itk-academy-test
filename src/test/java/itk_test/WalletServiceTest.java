package itk_test;

import itk_test.model.OperationType;
import itk_test.model.Wallet;
import itk_test.model.WalletBalanceResponse;
import itk_test.model.WalletBalanceResponseMapper;
import itk_test.model.WalletOperationRequest;
import itk_test.wallet.WalletRepository;
import itk_test.wallet.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;
    @Captor
    private ArgumentCaptor<Wallet> walletArgumentCaptor;

    private WalletService walletService;

    @BeforeEach
    public void setUp() {
        walletService = new WalletService(walletRepository, new WalletBalanceResponseMapper());
    }

    @Test
    void getWalletBalanceTest() {
        Wallet requiredWallet = new Wallet(UUID.fromString("33b34b35-aee1-4727-a851-463c1efe5534"), 1000L);
        when(walletRepository.getWalletBalance(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(requiredWallet));

        WalletBalanceResponse walletBalanceResponse = walletService.getWalletBalance(UUID.fromString("33b34b35-aee1-4727-a851-463c1efe5534"));

        assertNotNull(walletBalanceResponse);
        verify(walletRepository).getWalletBalance(ArgumentMatchers.any(UUID.class));
        assertEquals(1000L, walletBalanceResponse.getBalance());
    }

    @Test
    void depositTest() {
        Wallet requiredWallet = new Wallet(UUID.fromString("33b34b35-aee1-4727-a851-463c1efe5534"), 1000L);
        WalletOperationRequest walletOperationRequest = new WalletOperationRequest(UUID.fromString("33b34b35-aee1-4727-a851-463c1efe5534")
        , OperationType.DEPOSIT, 1000L);
        when(walletRepository.getWalletBalance(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(requiredWallet));

        WalletBalanceResponse walletBalanceResponse = walletService.changeWalletBalance(walletOperationRequest);

        assertNotNull(walletBalanceResponse);
        verify(walletRepository).getWalletBalance(ArgumentMatchers.any(UUID.class));
        verify(walletRepository).increaseWalletBalance(ArgumentMatchers.any(UUID.class), ArgumentMatchers.anyLong());
    }
}
