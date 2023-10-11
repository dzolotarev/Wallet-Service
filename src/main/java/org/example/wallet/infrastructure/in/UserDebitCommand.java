package org.example.wallet.infrastructure.in;

import org.example.wallet.domain.Account;
import org.example.wallet.domain.impl.AuditOption;
import org.example.wallet.service.AuditManager;
import org.example.wallet.service.TransactionManager;
import org.example.wallet.service.UserManager;
import org.example.wallet.utils.ConsoleHelper;

import java.io.IOException;
import java.util.UUID;

public class UserDebitCommand extends UserCommand {
    @Override
    public void execute() throws IOException {
        ConsoleHelper.writeMessage("Снятие средств.");

        Account account = getCurretUserAccount();
        TransactionManager transactionManager = new TransactionManager();
        AuditManager auditManager = new AuditManager();

        long value = makeTransaction();

        UUID uuid = UUID.fromString(ConsoleHelper.readString());

        if (transactionManager.debit(uuid, value, account.getUserId())) {
            auditManager.audit(OperationUser.DEBIT, UserManager.getCurrentUser().getId(), AuditOption.SUCCESSFUL);
            ConsoleHelper.writeMessage("Операция завершена успешно.");
        } else {
            auditManager.audit(OperationUser.DEBIT, UserManager.getCurrentUser().getId(), AuditOption.UNSUCCESSFUL);
            ConsoleHelper.writeMessage("Операция завершена с ошибкой.");
        }
    }
}
