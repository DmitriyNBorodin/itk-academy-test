package itk_test.wallet;

import itk_test.model.WalletBalanceResponse;
import itk_test.model.WalletOperationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1")
public class WalletController {
    @Autowired
    WalletService walletService;

    @PostMapping("/wallet")
    public WalletBalanceResponse changeWalletBalance(@Validated @RequestBody WalletOperationRequest walletOperationRequest) {
        return walletService.changeWalletBalance(walletOperationRequest);
    }

    @GetMapping("wallets/{walletUuid}")
    public WalletBalanceResponse getWalletBalance(@PathVariable("walletUuid") UUID walletUuid) {
        return walletService.getWalletBalance(walletUuid);
    }
}
