package org.example.wallet.infrastructure.in;

import org.example.wallet.domain.impl.AuditOption;
import org.example.wallet.service.AuditManager;
import org.example.wallet.service.UserManager;
import org.example.wallet.utils.ConsoleHelper;

public class UserLogoutCommand implements Command {
    @Override
    public void execute() {
        AuditManager auditManager = new AuditManager();
        auditManager.audit(OperationUser.LOGOUT, UserManager.getCurrentUser().getId(), AuditOption.UNKNOWN);
        UserManager.setCurrentUser(null);
        ConsoleHelper.writeMessage("Возвращайтесь!");
    }
}
