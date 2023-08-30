package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHelper {
    private static final String url = System.getProperty("db.url");
    private static final String user = System.getProperty("db.user");
    private static final String password = System.getProperty("db.password");
    private static final QueryRunner runner = new QueryRunner();

    private DBHelper() {
    }

    @SneakyThrows
    private static Connection getConn() {
        return DriverManager.getConnection(url, user, password);
    }

    @SneakyThrows
    public static void clearDB() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM credit_request_entity");
        runner.execute(connection, "DELETE FROM order_entity");
        runner.execute(connection, "DELETE FROM payment_entity");
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        var codesSQL = "SELECT status FROM payment_entity ORDER BY created_date DESC LIMIT 1;";
        return getData(codesSQL);
    }

    @SneakyThrows
    public static String getCreditRequestStatus() {
        var codesSQL = "SELECT status FROM credit_request_entity ORDER BY created_date DESC LIMIT 1;";
        return getData(codesSQL);
    }

    @SneakyThrows
    public static String getOrderCount() {
        var codeSQL = "SELECT COUNT(*) FROM order_entity ORDER BY created_date DESC LIMIT 1;";
        var conn = getConn();
        var runner = new QueryRunner();
        return runner.query(conn, codeSQL, new ScalarHandler<>());
    }

    @SneakyThrows
    private static String getData(String query) {
        var runner = new QueryRunner();
        var conn = getConn();
        return runner.query(conn, query, new ScalarHandler<>());
    }
}