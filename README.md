# Процедура запуска автотестов

Предусловие - запущен Docker Desktop

1. Запускаем проект Diplom в IntelliJ IDEA
2. В 1-ом терминале запускаем контейнеры с образами Баз Данных (MySql, PostgresQl) и симулятором gate-simulator командой - Docker-compose up
3. Во 2-ом терминале запускаем наше приложение командой - java -jar aqa-shop.jar
4. Запуск тестов
