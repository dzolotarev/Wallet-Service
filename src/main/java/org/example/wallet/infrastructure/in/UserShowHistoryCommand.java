package org.example.wallet.infrastructure.in;

import org.example.wallet.domain.Account;
import org.example.wallet.domain.Transaction;
import org.example.wallet.domain.impl.AuditOption;
import org.example.wallet.service.AuditManager;
import org.example.wallet.service.TransactionManager;
import org.example.wallet.service.UserManager;
import org.example.wallet.utils.ConsoleHelper;

import java.util.List;

public class UserShowHistoryCommand extends UserCommand {

    @Override
    public void execute() {
        ConsoleHelper.writeMessage("Просмотр истории операций.");
        AuditManager auditManager = new AuditManager();
        Account account = getCurretUserAccount();

        long userId = account.getUserId();
        List<Transaction> currentUserTransactions = new TransactionManager().transactions(userId);
        auditManager.audit(OperationUser.HISTORY, UserManager.getCurrentUser().getId(), AuditOption.SUCCESSFUL);
        if (currentUserTransactions.isEmpty()) {
            ConsoleHelper.writeMessage("\tУ вас не было еще ни одной операции.");
        } else {
            ConsoleHelper.writeMessage("\tДата транзакции\t\t\tТип операции\tСумма (UNIT(s))\tИдентификатор операции");
            for (Transaction transaction : currentUserTransactions) {
                ConsoleHelper.writeMessage(String.format("\t%s\t%s\t%d\t%s",
                        transaction.getDate(), transaction.getType(), transaction.getValue(), transaction.getId()));
            }
            ConsoleHelper.writeMessage("Операция завершена успешно.");
        }
    }
}
