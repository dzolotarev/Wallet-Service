package org.example.wallet.infrastructure.in;

import org.example.wallet.domain.Account;
import org.example.wallet.domain.User;
import org.example.wallet.repository.impl.AccountRepositoryImpl;
import org.example.wallet.service.UserManager;
import org.example.wallet.utils.ConsoleHelper;

import java.io.IOException;
import java.util.UUID;

public abstract class UserCommand implements Command {

    public Account getCurretUserAccount() {
        User user = UserManager.getCurrentUser();
        return AccountRepositoryImpl.getInstance().findUserAccount(user.getId());
    }

    public long makeTransaction() throws IOException {
        ConsoleHelper.writeMessage("Введите сумму :");
        long value = ConsoleHelper.readInt();
        ConsoleHelper.writeMessage("Введите уникальный код транзакции.");
        ConsoleHelper.writeMessage("Примеры уникальных кодов транзакции : ");
        ConsoleHelper.writeMessage(String.format("\t 1. %s\n\t 2. %s\n\t 3. %s", UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()));
        return value;
    }
}