package org.example.wallet.infrastructure.in;

import org.example.wallet.domain.User;
import org.example.wallet.domain.impl.AuditOption;
import org.example.wallet.service.AuditManager;
import org.example.wallet.service.UserManager;
import org.example.wallet.utils.ConsoleHelper;

import java.io.IOException;

public class UserAuthCommand implements Command {

    @Override
    public void execute() throws IOException {
        ConsoleHelper.writeMessage("Авторизация уже зарегистрированного пользователя.");

        UserManager userManager = new UserManager();
        AuditManager auditManager = new AuditManager();

        while (true) {
            ConsoleHelper.writeMessage("Введите логин пользователя :");
            String login = ConsoleHelper.readString();
            ConsoleHelper.writeMessage("Введите пароль пользователя :");
            String password = ConsoleHelper.readString();

            User user = userManager.login(login, password);
            if (user != null) {
                UserManager.setCurrentUser(user);
                auditManager.audit(OperationBasic.AUTH, user.getId(), AuditOption.SUCCESSFUL);
                ConsoleHelper.writeMessage("Вы авторизованы!");
                break;
            } else {
                ConsoleHelper.writeMessage("Неверный логин или пароль пользователя!");
            }
        }
    }
}