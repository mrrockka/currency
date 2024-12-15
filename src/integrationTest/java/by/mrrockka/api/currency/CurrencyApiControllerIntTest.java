package by.mrrockka.api.currency;

import by.mrrockka.config.PSQLExtension;
import by.mrrockka.service.currency.Currency;
import by.mrrockka.service.currency.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(PSQLExtension.class)
@AutoConfigureMockMvc
public class CurrencyApiControllerIntTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
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
    final var currencies = List.of("AUD", "GBP");
    currencies.forEach(currencyService::storeCurrency);

    this.mockMvc.perform(get("/currencies"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(content().json(new ObjectMapper().writeValueAsString(currencies)));
  }

  @Test
  void givenExchangeRates_whenRequestToGetRates_thenShouldReturnData() throws Exception {
    final var currency = Currency.builder()
      .code("EUR")
      .rates(Map.of("AUD", BigDecimal.valueOf(1.123), "GPB", BigDecimal.valueOf(2.543)))
      .build();

    currencyService.updateCurrency(currency);
    this.mockMvc.perform(get(STR."/currencies/\{currency.code()}/rates"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(content().json(new ObjectMapper().writeValueAsString(currency.rates())));
  }
}
