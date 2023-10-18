package org.example.wallet.infrastructure.in;

import org.example.wallet.domain.impl.AuditOption;
import org.example.wallet.service.AuditManager;
import org.example.wallet.service.TransactionManager;
import org.example.wallet.service.UserManager;
import org.example.wallet.utils.ConsoleHelper;

import java.io.IOException;
import java.sql.SQLException;

public class UserDebitCommand extends UserCommand {
    @Override
    public void execute() throws IOException, SQLException {
        ConsoleHelper.writeMessage("Снятие средств.");
        if (TransactionManager.getInstance().debit(makeTransaction(), getCurretUserAccount().getUserId())) {
            AuditManager.getInstance().audit(OperationUser.DEBIT, UserManager.getCurrentUser().getId(), AuditOption.SUCCESSFUL);
            ConsoleHelper.writeMessage("Операция завершена успешно.");
        } else {
            AuditManager.getInstance().audit(OperationUser.DEBIT, UserManager.getCurrentUser().getId(), AuditOption.UNSUCCESSFUL);
            ConsoleHelper.writeMessage("Операция завершена с ошибкой.");
        }
    }
}
