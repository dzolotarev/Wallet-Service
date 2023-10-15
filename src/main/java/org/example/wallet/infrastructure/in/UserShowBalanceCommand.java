package org.example.wallet.infrastructure.in;

import org.example.wallet.domain.Account;
import org.example.wallet.domain.impl.AuditOption;
import org.example.wallet.service.AuditManager;
import org.example.wallet.service.UserManager;
import org.example.wallet.utils.ConsoleHelper;

public class UserShowBalanceCommand extends UserCommand {
    @Override
    public void execute() {

        Account account = getCurretUserAccount();
        AuditManager auditManager = new AuditManager();

        ConsoleHelper.writeMessage("Просмотр баланса.");
        auditManager.audit(OperationUser.BALANCE, UserManager.getCurrentUser().getId(), AuditOption.SUCCESSFUL);
        ConsoleHelper.writeMessage(String.format("\tВаш текущий баланс : %d UNIT(s): ", account.getBalance()));
        ConsoleHelper.writeMessage("Операция завершена.");
    }
}
