FROM openjdk:22

WORKDIR /app

COPY build/libs/currency-*-SNAPSHOT.jar currency.jar

ENTRYPOINT ["java", "--enable-preview", "-jar", "/app/currency.jar"]