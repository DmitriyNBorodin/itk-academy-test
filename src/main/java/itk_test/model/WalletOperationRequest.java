package itk_test.model;


import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class WalletOperationRequest {
    UUID walletUuid;
    @NotNull(message = "Не указан тип операции") OperationType operationType;
    @NotNull(message = "Не указана сумма") Long amount;

    public WalletOperationRequest(UUID walletUuid, OperationType operationType, Long amount) {
        this.walletUuid = walletUuid;
        this.operationType = operationType;
        this.amount = amount;
    }

    public UUID getWalletUuid() {
        return walletUuid;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public Long getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return walletUuid + "---" + operationType + "---" + amount;
    }
}
