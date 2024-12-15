package by.mrrockka.api;

import by.mrrockka.service.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyApiControllerIntTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private CurrencyService currencyService;

  @Test
  void givenCurrency_whenRequestToAdd_thenShouldReturnCreatedCode() throws Exception {
    this.mockMvc.perform(
        post("/currencies")
          .content("GBP")
          .contentType(MediaType.TEXT_PLAIN_VALUE)
      )
      .andExpect(status().isCreated());
  }

  @Test
  void givenStoredCurrencies_whenRequestToGetAll_thenShouldReturnData() throws Exception {
    final var currencies = List.of("AUD", "GPB");
    when(currencyService.getAllCurrencies()).thenReturn(currencies);

    this.mockMvc.perform(get("/currencies"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(content().json(new ObjectMapper().writeValueAsString(currencies)));
  }

  @Test
  void givenExchangeRates_whenRequestToGetRates_thenShouldReturnData() throws Exception {
    final var currency = "EUR";
    final var rates = Map.of("AUD", BigDecimal.valueOf(1.123), "GPB", BigDecimal.valueOf(2.543));
    when(currencyService.getCurrencyExchangeRate(currency)).thenReturn(rates);

    this.mockMvc.perform(get(STR."/currencies/\{currency}/rates"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(content().json(new ObjectMapper().writeValueAsString(rates)));
  }
}
