package by.mrrockka.service.currency;

import by.mrrockka.client.ExchangeRatesClient;
import by.mrrockka.mapper.CurrencyMapper;
import by.mrrockka.repository.currency.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyService {

  private final CurrencyRepository currencyRepository;
  private final CurrencyMapper currencyMapper;
  private final ExchangeRatesClient exchangeRatesClient;
  private final Map<String, Currency> currencies = new HashMap<>();

  @Transactional
  public void storeCurrency(String currencyCode) {
    currencyRepository.insert(currencyCode);
    cacheCurrency(Currency.builder()
                    .code(currencyCode)
                    .build());
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void updateCurrency(Currency currency) {
    currencyRepository.upsert(currency.code(), currency.rates());
    cacheCurrency(currency);
  }

  @Transactional
  public void updateBatch(List<Currency> currencies) {
    currencies.forEach(this::updateCurrency);
  }

  public List<String> getAllCurrencyCodes() {
    return getAllCachedCurrencyCodes()
      .orElse(
        cacheCurrencies(currencyRepository.selectAll()
                          .stream()
                          .map(currencyMapper::toDomain)
                          .toList()).stream()
          .map(Currency::code)
          .toList());
  }

  public Currency getCurrencyExchangeRate(String currencyCode) {
    return getCachedCurrency(currencyCode)
      .map(currency -> currency.toBuilder()
        .rates(currency.optRates()
                 .orElse(retrieveAndStoreRates(currencyCode)))
        .build())
      .map(this::cacheCurrency)
      .orElse(cacheCurrency(currencyMapper.toDomain(currencyRepository.select(currencyCode))));
  }

  private Map<String, BigDecimal> retrieveAndStoreRates(String currencyCode) {
    final var rates = exchangeRatesClient.retrieveExchangeRates(currencyCode);
    currencyRepository.upsert(currencyCode, rates);
    return rates;
  }

  //  todo: move to cache provider or configure via framework
  private Optional<Currency> getCachedCurrency(String currencyCode) {
    return Optional.ofNullable(currencies.get(currencyCode));
  }

  private Optional<List<String>> getAllCachedCurrencyCodes() {
    return currencies.isEmpty() ?
      Optional.empty() :
      Optional.of(currencies.keySet()
                    .stream()
                    .toList());
  }

  private Currency cacheCurrency(Currency currency) {
    currencies.put(currency.code(), currency);
    return currency;
  }

  private List<Currency> cacheCurrencies(List<Currency> currencies) {
    return currencies.stream()
      .map(this::cacheCurrency)
      .toList();
  }
}
