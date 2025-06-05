package itk_test.model;

import java.util.UUID;

public class WalletBalanceResponse {
    public WalletBalanceResponse(UUID uuid, Long balance) {
        this.walletUuid = uuid;
        this.balance = balance;
    }
    private final UUID walletUuid;
    private final long balance;

    public UUID getWalletUuid() {
        return walletUuid;
    }
    public long getBalance() {
        return balance;
    }
}
