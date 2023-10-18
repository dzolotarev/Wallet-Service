package org.example.wallet.infrastructure.in;

import org.example.wallet.domain.impl.AuditOption;
import org.example.wallet.service.AuditManager;
import org.example.wallet.service.TransactionManager;
import org.example.wallet.service.UserManager;
import org.example.wallet.utils.ConsoleHelper;

import java.io.IOException;
import java.sql.SQLException;

public class UserCreditCommand extends UserCommand {
    @Override
    public void execute() throws IOException, SQLException {
        ConsoleHelper.writeMessage("Зачисление средств.");

        if (TransactionManager.getInstance().credit(makeTransaction(), getCurretUserAccount().getUserId())) {
            ConsoleHelper.writeMessage("Операция завершена успешно.");
            AuditManager.getInstance().audit(OperationUser.CREDIT, UserManager.getCurrentUser().getId(), AuditOption.SUCCESSFUL);
        } else {
            ConsoleHelper.writeMessage("Операция завершена с ошибкой.");
            AuditManager.getInstance().audit(OperationUser.CREDIT, UserManager.getCurrentUser().getId(), AuditOption.UNSUCCESSFUL);
        }
    }
}
