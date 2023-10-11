package org.example.wallet.infrastructure.in;

import org.example.wallet.service.UserManager;
import org.example.wallet.utils.ConsoleHelper;

import java.io.IOException;

public class UserRegisterCommand extends UserCommand {
    @Override
    public void execute() throws IOException {
        UserManager userManager = new UserManager();

        ConsoleHelper.writeMessage("Регистрация нового пользователя.");
        String login;
        while (true) {
            ConsoleHelper.writeMessage("Придумайте и введите логин :");
            login = ConsoleHelper.readString();
            if (userManager.isLoginExist(login)) {
                ConsoleHelper.writeMessage("Пользователь с таким логином уже существует!");
            } else {
                break;
            }
        }

        ConsoleHelper.writeMessage("Придумайте и введите пароль :");
        String password = ConsoleHelper.readString();

        ConsoleHelper.writeMessage("Введите ваше имя :");
        String name = ConsoleHelper.readString();

        if (userManager.register(login, password, name)) {
            ConsoleHelper.writeMessage("Пользователь создан.");
        }
    }
}