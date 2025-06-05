package itk_test;

import itk_test.model.Wallet;
import itk_test.wallet.WalletRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class WalletRepositoryTest {
    @Autowired
    WalletRepository walletRepository;

@Test
    void getWalletTest() {
    Wallet newWallet = walletRepository.addNewWallet(1000L);

    Optional<Wallet> requiredWallet = walletRepository.getWalletBalance(newWallet.getUuid());

    assertTrue(requiredWallet.isPresent());
    assertEquals(1000L, requiredWallet.get().getBalance());
}
}
