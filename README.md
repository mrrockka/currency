## Task description

### User requirements: 
1. Should be able to add new currency for exchange rates
2. Should be able to get all the currencies previously added by user 
3. Should be able to get exchange rates (from public source) for currency provided


### Non Functional Requirements:
1. Integrate with any public currencies source to get rates
2. Exchange rates should be stored to DB 
3. Exchange rates should be retrieved in scheduled manner (every hour)
4. API should get data from the Map not from DB
5. Should use Postgres DB 
6. Should manage schema changes using Liquibase in any format
7. Data management should be performed using Spring Data JPA or JDBC
8. Application could be dockerizable. PostgresDB should be started in container via docker compose file
9. Should be covered bu unit and functional test using JUnit5 and Spring Test Framework
10. Project should have API docs, preferable Swagger/OpenAPI spec dynamic/static)


## Implementation steps:
1. App skeleton with two endpoints, one to add currency, second to get all currencies (without any integrations, without db and liquibase) using OpenAPI doc
2. Add get exchange endpoint with mocked data
3. Integrate with db to store currencies and add migration scripts
4. Integrate with exchange provider and add scheduler
5. Add docker files for app and docker compose to start all services together


## Assumptions:
1. `get a list of currencies used in the project` means that it is a list of previously added currencies by user
2. On adding new currencies there should be a call to exchange rates provider to populate storage with rates
3. On application start if there are no currencies stored in db there should be no prepopulation of rates (no currencies - no rates)
4. When there are number of currencies stored every scheduled call to exchange provider should be performed for all the currencies to update them in db 