package itk_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import itk_test.model.OperationType;
import itk_test.model.WalletBalanceResponse;
import itk_test.model.WalletOperationRequest;
import itk_test.wallet.WalletController;
import itk_test.wallet.WalletService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WalletController.class)
public class WalletControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private WalletService walletService;

    @Test
    void addNewWallet() throws Exception {
        WalletOperationRequest newRequest = new WalletOperationRequest(null, OperationType.DEPOSIT, 1000L);
        when(walletService.changeWalletBalance(Mockito.any(WalletOperationRequest.class))).thenAnswer(
                invocationOnMock -> {
                    WalletOperationRequest newWalletRequest = invocationOnMock.getArgument(0, WalletOperationRequest.class);
                    return new WalletBalanceResponse(UUID.fromString("33b34b35-aee1-4727-a851-463c1efe5534"), newWalletRequest.getAmount());
                }
        );

        mvc.perform(post("/api/v1/wallet").content(mapper.writeValueAsString(newRequest)).characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletUuid", is("33b34b35-aee1-4727-a851-463c1efe5534"), String.class))
                .andExpect(jsonPath("$.balance", is(1000L), Long.class));
    }

    @Test
    void getWalletBalanceTest() throws Exception {
        when(walletService.getWalletBalance(Mockito.any(UUID.class)))
                .thenReturn(new WalletBalanceResponse(UUID.fromString("33b34b35-aee1-4727-a851-463c1efe5534"),1000L));

        mvc.perform(get("/api/v1/wallets/33b34b35-aee1-4727-a851-463c1efe5534").characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletUuid", is("33b34b35-aee1-4727-a851-463c1efe5534"), String.class))
                .andExpect(jsonPath("$.balance", is(1000L), Long.class));
    }
}
