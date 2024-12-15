package by.mrrockka.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.PreparedStatement;

import static by.mrrockka.config.TestPSQLContainer.container;

@Slf4j
public class PSQLExtension implements BeforeAllCallback, AfterEachCallback {
  @Override
  public void beforeAll(final ExtensionContext context) {
    container.start();
  }

  @Override
  public void afterEach(final ExtensionContext context) {
    final var jdbcTemplate = SpringExtension.getApplicationContext(context)
      .getBean(NamedParameterJdbcTemplate.class);
    jdbcTemplate.execute("truncate currency;", PreparedStatement::execute);
  }

}
