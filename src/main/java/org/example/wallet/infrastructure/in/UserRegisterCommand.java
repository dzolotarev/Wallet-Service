package org.example.wallet.infrastructure.in;

import org.example.wallet.service.UserManager;
import org.example.wallet.utils.ConsoleHelper;

import java.io.IOException;
import java.sql.SQLException;

public class UserRegisterCommand extends UserCommand {
    @Override
    public void execute() throws IOException, SQLException {

        ConsoleHelper.writeMessage("Регистрация нового пользователя.");
        String login;
        while (true) {
            ConsoleHelper.writeMessage("Придумайте и введите логин :");
            login = ConsoleHelper.readString();
            if (UserManager.getInstance().isLoginExist(login)) {
                ConsoleHelper.writeMessage("Пользователь с таким логином уже существует!");
            } else {
                break;
            }
        }

        ConsoleHelper.writeMessage("Придумайте и введите пароль :");
        String password = ConsoleHelper.readString();

        ConsoleHelper.writeMessage("Введите ваше имя :");
        String name = ConsoleHelper.readString();

        if (UserManager.getInstance().register(login, password, name)) {
            ConsoleHelper.writeMessage("Пользователь создан.");
        }
    }
}