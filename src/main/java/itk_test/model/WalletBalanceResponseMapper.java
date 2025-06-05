package itk_test.model;

import org.springframework.stereotype.Component;

@Component
public class WalletBalanceResponseMapper {
    public WalletBalanceResponse convertWalletToResponse(Wallet wallet) {
        return new WalletBalanceResponse(
                wallet.getUuid(),
                wallet.getBalance()
        );
    }
}
