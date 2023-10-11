package org.example.wallet.infrastructure.in;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {
    private static final Map<Operation, Command> allKnownCommandsMap = new HashMap<>();

    static {
        allKnownCommandsMap.put(OperationBasic.AUTH, new UserAuthCommand());
        allKnownCommandsMap.put(OperationBasic.REGISTER, new UserRegisterCommand());
        allKnownCommandsMap.put(OperationBasic.AUDIT, new UserAuditCommand());
        allKnownCommandsMap.put(OperationBasic.EXIT, new ExitCommand());
        allKnownCommandsMap.put(OperationUser.CREDIT, new UserCreditCommand());
        allKnownCommandsMap.put(OperationUser.DEBIT, new UserDebitCommand());
        allKnownCommandsMap.put(OperationUser.BALANCE, new UserShowBalanceCommand());
        allKnownCommandsMap.put(OperationUser.HISTORY, new UserShowHistoryCommand());
        allKnownCommandsMap.put(OperationUser.LOGOUT, new UserLogoutCommand());
    }

    public static void execute(Operation operation) throws Exception {
        allKnownCommandsMap.get(operation).execute();
    }
}
