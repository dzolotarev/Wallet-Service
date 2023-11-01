package org.example.wallet.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.wallet.domain.User;
import org.example.wallet.enums.AuditOption;
import org.example.wallet.enums.Operation;
import org.example.wallet.repository.db.UserRepositoryDBImpl;
import org.example.wallet.service.AuditManager;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Denis Zolotarev on 28.10.2023.
 */
@Aspect
public class UserAspects {
    private final AuditManager auditManager = AuditManager.getInstance();

    @Pointcut("execution(* org.example.wallet.service.UserManager.login(..))")
    public void loginOperation() {

    }

    @Pointcut("execution(* org.example.wallet.service.UserManager.register(..))")
    public void registerOperation() {
    }

    @AfterReturning(value = "loginOperation()", returning = "user")
    public void afterLogin(User user) throws SQLException, IOException {
        if (user != null) {
            auditManager.audit(Operation.AUTH, user.getId(), AuditOption.SUCCESSFUL);
        }
    }

    @AfterReturning(value = "registerOperation()", returning = "retValue")
    public void afterRegister(JoinPoint joinPoint, boolean retValue) throws SQLException, IOException {
        String login = (String) (joinPoint.getArgs())[0];
        long userId = UserRepositoryDBImpl.getInstance().findUserByLogin(login).getId();
        if (retValue) {
            auditManager.audit(Operation.REGISTER, userId, AuditOption.SUCCESSFUL);
        }
    }
}

