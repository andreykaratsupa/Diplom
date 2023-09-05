# Процедура запуска автотестов

Предусловие - запущен Docker Desktop

1. Запускаем проект Diplom в IntelliJ IDEA

2. В 1-ом терминале запускаем контейнеры с образами Баз Данных (MySql, PostgresQl) и симулятором gate-simulator командой - Docker-compose up

3.1. Во 2-ом терминале запускаем наше приложение с базой MySQL командой:
      java -
      Dspring.datasource.url=jdbc:mysql://localhost:3306/app -
      Dspring.datasource.username=app -
      Dspring.datasource.password=pass -jar aqa-shop.jar

3.2. Или во 2-ом терминале запускаем наше приложение с базой PostgresSQL командой:
      java -
      Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -
      Dspring.datasource.username=app -
      Dspring.datasource.password=pass -jar aqa-shop.jar

4. Запускаем тесты:
   Команда для MySQL - ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"
   Команда для Postgres - ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"

5. Отчет о тестировании:
    Отчет формируется через Allure.
    Для этого нужно ввести команду - ./gradlew allureServe 