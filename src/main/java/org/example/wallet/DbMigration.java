package org.example.wallet;

import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.example.wallet.service.db.DbManager;
import org.example.wallet.service.db.impl.DbManagerPostgresImpl;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DbMigration {
    public static void main(String[] args) {
        DbManager dbManager = new DbManagerPostgresImpl();
        liquibaseMigration(dbManager);
    }

    public static void liquibaseMigration(DbManager dbManager) {
        Map<String, Object> config = new HashMap<>();
        config.put("liquibase.liquibaseSchemaName", "wallet_liquibase");
        try {
            Scope.child(config, () -> {
                try (Connection connection = dbManager.getConnection(); Statement statement = connection.createStatement()) {
                    statement.execute("CREATE SCHEMA IF NOT EXISTS wallet_liquibase");
                    statement.execute("CREATE EXTENSION IF NOT EXISTS \"uuid-ossp\"");

                    Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
                    liquibase.Liquibase liquibase = new liquibase.Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
                    liquibase.update();
                    System.out.println("Миграции успешно выполнены!");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            dbManager.closeConnection();
        }
    }
}
