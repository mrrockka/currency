package by.mrrockka.api.currency;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Tag(name = "Currency", description = "currencies API")
interface CurrencyApi {

  @Operation(summary = "Add new currencies to process", description = "Add new currencies to process")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Successful operation"),
    @ApiResponse(responseCode = "400", description = "Bad request")
  })
  @PostMapping(
    value = "/currencies",
    consumes = {MediaType.TEXT_PLAIN_VALUE}
  )
//  todo: add enum or list with all the currency codes
  default ResponseEntity<Void> addCurrency(
    @Parameter(description = "Create a new currency", required = true) @RequestBody String currency) {
    throw new RuntimeException("Not implemented yet");
  }

  @Operation(summary = "Get all currencies in app", description = "Get all currencies in app")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Successful operation")
  })
  @GetMapping(
    value = "/currencies",
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  default ResponseEntity<List<String>> getAllCurrencies() {
    throw new RuntimeException("Not implemented yet");
  }

  @Operation(summary = "Get exchange rates for currency", description = "Get exchange rates for currency")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Successful operation"),
    @ApiResponse(responseCode = "404", description = "Currency not found"),
  })
  @GetMapping(
    value = "/currencies/{currencyCode}/rates",
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  default ResponseEntity<Map<String, BigDecimal>> getExchangeRates(
    @Parameter(description = "Currency code to get exchange rates for", required = true) @PathVariable String currencyCode) {
    throw new RuntimeException("Not implemented yet");
  }
}
