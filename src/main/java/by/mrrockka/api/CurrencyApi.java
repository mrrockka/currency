package by.mrrockka.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Currency", description = "currencies API")
public interface CurrencyApi {

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
}
