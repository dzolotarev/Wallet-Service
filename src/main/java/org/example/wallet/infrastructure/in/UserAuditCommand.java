package org.example.wallet.infrastructure.in;

import org.example.wallet.domain.Audit;
import org.example.wallet.service.AuditManager;
import org.example.wallet.utils.ConsoleHelper;

import java.io.IOException;

public class UserAuditCommand implements Command {
    @Override
    public void execute() throws IOException {
        ConsoleHelper.writeMessage("Все действия всех пользователей.");
        while (true) {
            ConsoleHelper.writeMessage("Необходимо авторизоваться под администратором!\n(Логин: admin ; пароль: admin )");
            ConsoleHelper.writeMessage("Введите логин пользователя :");
            String login = ConsoleHelper.readString();
            ConsoleHelper.writeMessage("Введите пароль пользователя :");
            String password = ConsoleHelper.readString();
            if (login.equals("admin") && password.equals("admin")) {
                ConsoleHelper.writeMessage("Вы администратор!");

                AuditManager auditManager = new AuditManager();
                if (auditManager.getAll().isEmpty()) {
                    ConsoleHelper.writeMessage("\tЖурнал действий пользователей пуст");
                } else ConsoleHelper.writeMessage("\tДата операции\tИД пользователя\tТип операции\tРезультат");
                for (Audit audit : auditManager.getAll()) {
                    ConsoleHelper.writeMessage(String.format("\t%s\t%d\t%s\t%s",
                            audit.getCreatedAt(), audit.getUserId(), audit.getOperation(), audit.getAuditOption()));
                }
                break;
            } else {
                ConsoleHelper.writeMessage("Неверный логин или пароль администратора!");
            }
        }
    }
}
