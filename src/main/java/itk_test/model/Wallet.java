package itk_test.model;

import java.util.UUID;

public class Wallet {
    public Wallet(UUID uuid, Long balance) {
        this.uuid = uuid;
        this.balance = balance;
    }
    private final UUID uuid;
    private long balance;

    public UUID getUuid() {
        return uuid;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long newBalance) {
        this.balance = newBalance;
    }
}
