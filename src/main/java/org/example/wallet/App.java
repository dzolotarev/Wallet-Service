package org.example.wallet;


import org.example.wallet.domain.User;
import org.example.wallet.infrastructure.in.CommandExecutor;
import org.example.wallet.infrastructure.in.Operation;
import org.example.wallet.infrastructure.in.OperationBasic;
import org.example.wallet.infrastructure.in.OperationUser;
import org.example.wallet.repository.db.AccountRepositoryDBImpl;
import org.example.wallet.repository.db.AuditRepositoryDBImpl;
import org.example.wallet.repository.db.TransactionRepositoryDBImpl;
import org.example.wallet.repository.db.UserRepositoryDBImpl;
import org.example.wallet.service.UserManager;
import org.example.wallet.service.db.DbManager;
import org.example.wallet.service.db.impl.DbManagerPostgresImpl;
import org.example.wallet.utils.ConsoleHelper;

import java.io.IOException;

/**
 * Main app
 */
public class App {
    public static void main(String[] args) {
        DbManager dbManager = new DbManagerPostgresImpl();
//        liquibaseMigration(dbManager);
        UserRepositoryDBImpl.init(dbManager);
        AccountRepositoryDBImpl.init(dbManager);
        TransactionRepositoryDBImpl.init(dbManager);
        AuditRepositoryDBImpl.init(dbManager);

        Operation operation = null;
        do {
            try {
                operation = askOperation(UserManager.getCurrentUser());
                CommandExecutor.execute(operation);
            } catch (Exception e) {
                ConsoleHelper.writeMessage("Произошла ошибка. Проверьте введенные данные.");
            }

        } while (operation != OperationBasic.EXIT);
        dbManager.closeConnection();
    }

    public static Operation askOperation(User user) throws IOException {
        ConsoleHelper.writeMessage("");
        ConsoleHelper.writeMessage("Выберите операцию:");

        if (user == null) {
            ConsoleHelper.writeMessage(String.format("\t %d - Авторизовать пользователя", OperationBasic.AUTH.ordinal()));
            ConsoleHelper.writeMessage(String.format("\t %d - Создать нового пользователя", OperationBasic.REGISTER.ordinal()));
            ConsoleHelper.writeMessage(String.format("\t %d - Аудит всех пользователей", OperationBasic.AUDIT.ordinal()));
            ConsoleHelper.writeMessage(String.format("\t %d - Выход", OperationBasic.EXIT.ordinal()));
            return OperationBasic.values()[ConsoleHelper.readInt()];
        } else {
            ConsoleHelper.writeMessage(String.format("\t %d - Кредит операция", OperationUser.CREDIT.ordinal()));
            ConsoleHelper.writeMessage(String.format("\t %d - Дебит операция", OperationUser.DEBIT.ordinal()));
            ConsoleHelper.writeMessage(String.format("\t %d - Показать балланс", OperationUser.BALANCE.ordinal()));
            ConsoleHelper.writeMessage(String.format("\t %d - Показать историю операций", OperationUser.HISTORY.ordinal()));
            ConsoleHelper.writeMessage(String.format("\t %d - Выйти из аккаунта", OperationUser.LOGOUT.ordinal()));
            return OperationUser.values()[ConsoleHelper.readInt()];
        }
    }
}
