package org.example.wallet.infrastructure.in;

import org.example.wallet.utils.ConsoleHelper;

public class ExitCommand implements Command {
    @Override
    public void execute() {
        ConsoleHelper.writeMessage("До встречи!");
    }
}
