package org.example.wallet.infrastructure.in;

import org.example.wallet.domain.Account;
import org.example.wallet.domain.impl.AuditOption;
import org.example.wallet.service.AuditManager;
import org.example.wallet.service.TransactionManager;
import org.example.wallet.service.UserManager;
import org.example.wallet.utils.ConsoleHelper;

import java.io.IOException;
import java.util.UUID;

public class UserCreditCommand extends UserCommand {
    @Override
    public void execute() throws IOException {
        ConsoleHelper.writeMessage("Зачисление средств.");

        Account account = getCurretUserAccount();
        TransactionManager transactionManager = new TransactionManager();
        AuditManager auditManager = new AuditManager();

        long value = makeTransaction();

        UUID uuid = UUID.fromString(ConsoleHelper.readString());

        if (transactionManager.credit(uuid, value, account.getUserId())) {
            ConsoleHelper.writeMessage("Операция завершена успешно.");
            auditManager.audit(OperationUser.CREDIT, UserManager.getCurrentUser().getId(), AuditOption.SUCCESSFUL);
        } else {
            ConsoleHelper.writeMessage("Операция завершена с ошибкой.");
            auditManager.audit(OperationUser.CREDIT, UserManager.getCurrentUser().getId(), AuditOption.UNSUCCESSFUL);
        }
    }
}