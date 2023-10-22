package org.example.wallet.infrastructure.in;

import org.example.wallet.domain.impl.AuditOption;
import org.example.wallet.service.AuditManager;
import org.example.wallet.service.UserManager;
import org.example.wallet.utils.ConsoleHelper;

import java.io.IOException;
import java.sql.SQLException;

public class UserLogoutCommand implements Command {
    @Override
    public void execute() throws SQLException, IOException {
        AuditManager.getInstance().audit(OperationUser.LOGOUT, UserManager.getCurrentUser().getId(), AuditOption.UNKNOWN);
        UserManager.setCurrentUser(null);
        ConsoleHelper.writeMessage("Возвращайтесь!");
    }
}
