package org.example.wallet.infrastructure.in;

import org.example.wallet.domain.impl.AuditOption;
import org.example.wallet.service.AuditManager;
import org.example.wallet.service.UserManager;
import org.example.wallet.utils.ConsoleHelper;

import java.io.IOException;
import java.sql.SQLException;

public class UserShowBalanceCommand extends UserCommand {
    @Override
    public void execute() throws SQLException, IOException {
        ConsoleHelper.writeMessage("Просмотр баланса.");
        AuditManager.getInstance().audit(OperationUser.BALANCE, UserManager.getCurrentUser().getId(), AuditOption.SUCCESSFUL);
        ConsoleHelper.writeMessage(String.format("\tВаш текущий баланс : %d UNIT(s): ", getCurretUserAccount().getBalance()));
        ConsoleHelper.writeMessage("Операция завершена.");
    }
}
