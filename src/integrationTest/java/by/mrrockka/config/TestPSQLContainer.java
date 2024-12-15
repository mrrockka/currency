package by.mrrockka.config;

import org.testcontainers.containers.PostgreSQLContainer;

class TestPSQLContainer extends PostgreSQLContainer<TestPSQLContainer> {

  public static final String VERSION = "16.1";
  public static final String IMAGE_AND_VERSION = "postgres:%s".formatted(VERSION);
  public static final String DB_NAME = "currency";
  private static final String USERNAME = "itest";
  private static final String PASSWORD = "itest123";

  static final TestPSQLContainer container = new TestPSQLContainer()
    .withDatabaseName(DB_NAME)
    .withUsername(USERNAME)
    .withPassword(PASSWORD);

  TestPSQLContainer() {
    super(IMAGE_AND_VERSION);
  }

}
