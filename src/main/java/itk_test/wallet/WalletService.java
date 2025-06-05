package itk_test.wallet;

import itk_test.model.OperationType;
import itk_test.model.Wallet;
import itk_test.model.WalletBalanceResponse;
import itk_test.model.WalletBalanceResponseMapper;
import itk_test.model.WalletOperationRequest;
import itk_test.util.NotEnoughMoneyException;
import itk_test.util.UnknownOperationException;
import itk_test.util.WalletNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletBalanceResponseMapper walletBalanceResponseMapper;

    @Autowired
    public WalletService(WalletRepository walletRepository, WalletBalanceResponseMapper walletBalanceResponseMapper) {
        this.walletRepository = walletRepository;
        this.walletBalanceResponseMapper = walletBalanceResponseMapper;
    }

    public WalletBalanceResponse changeWalletBalance(WalletOperationRequest walletOperationRequest) {
        if (walletOperationRequest.getOperationType().equals(OperationType.WITHDRAW)) {
            return withdrewFromWallet(walletOperationRequest);
        } else if (walletOperationRequest.getOperationType().equals(OperationType.DEPOSIT)) {
            if (walletOperationRequest.getWalletUuid() == null) {
                return createNewWallet(walletOperationRequest.getAmount());
            }
            Wallet currentWallet = checkWallet(walletOperationRequest.getWalletUuid());
            return depositOnWallet(currentWallet, walletOperationRequest.getAmount());
        } else {
            throw new UnknownOperationException("Неизвестная операция");
        }
    }

    public WalletBalanceResponse getWalletBalance(UUID walletUuid) {
        Wallet currentWallet = checkWallet(walletUuid);
        return walletBalanceResponseMapper.convertWalletToResponse(currentWallet);
    }

    private Wallet checkWallet(UUID walletUuid) {
        return walletRepository.getWalletBalance(walletUuid)
                .orElseThrow(() -> new WalletNotFoundException("Некорректный UUID кошелька"));
    }

    private WalletBalanceResponse withdrewFromWallet(WalletOperationRequest walletOperationRequest) {
        Wallet currentWallet = checkWallet(walletOperationRequest.getWalletUuid());
        if (currentWallet.getBalance() - walletOperationRequest.getAmount() < 0) {
                throw new NotEnoughMoneyException("На счету недостаточно средств");
        }
        long newBalance = currentWallet.getBalance() - walletOperationRequest.getAmount();
        walletRepository.decreaseWalletBalance(currentWallet.getUuid(), walletOperationRequest.getAmount());
        return new WalletBalanceResponse(currentWallet.getUuid(), newBalance);
    }

    private WalletBalanceResponse createNewWallet(long amount) {
        Wallet currentWallet = walletRepository.addNewWallet(amount);
        return walletBalanceResponseMapper.convertWalletToResponse(currentWallet);
    }

    private WalletBalanceResponse depositOnWallet(Wallet wallet, long amount) {
        long newBalance = wallet.getBalance() + amount;
        walletRepository.increaseWalletBalance(wallet.getUuid(), amount);
        return new WalletBalanceResponse(wallet.getUuid(), newBalance);
    }
}
