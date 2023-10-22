package org.example.wallet.infrastructure.in;

import org.example.wallet.domain.Account;
import org.example.wallet.repository.db.AccountRepositoryDBImpl;
import org.example.wallet.service.UserManager;
import org.example.wallet.utils.ConsoleHelper;

import java.io.IOException;
import java.sql.SQLException;

public abstract class UserCommand implements Command {
    public Account getCurretUserAccount() throws SQLException, IOException {
        return AccountRepositoryDBImpl.getInstance().findUserAccount(UserManager.getCurrentUser().getId());
    }

    public long makeTransaction() throws IOException {
        ConsoleHelper.writeMessage("Введите сумму :");
        return ConsoleHelper.readInt();
    }
}
