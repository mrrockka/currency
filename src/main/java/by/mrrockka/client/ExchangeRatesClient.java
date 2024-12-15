package by.mrrockka.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Component
public class ExchangeRatesClient {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${client.exchangeRatesProvider.url}")
  private String exchangeRatesProviderUrl;

  @Value("${client.exchangeRatesProvider.apiKey}")
  private String apiKey;

  public Map<String, BigDecimal> retrieveExchangeRates(String currency) {
    return Optional.ofNullable(
        restTemplate.getForObject(STR."\{exchangeRatesProviderUrl}/v1/latest?access_key=\{apiKey}&base=\{currency}",
                                  ExchangeRatesResponse.class)
      )
      .map(ExchangeRatesResponse::rates)
      .orElseThrow(() -> new RuntimeException("Rate are nullable"));
  }

}
