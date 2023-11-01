package org.example.wallet.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.wallet.enums.AuditOption;
import org.example.wallet.enums.Operation;
import org.example.wallet.service.AuditManager;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Denis Zolotarev on 28.10.2023.
 */
@Aspect
public class TransactionAspects {
    private final AuditManager auditManager = AuditManager.getInstance();

    @Pointcut("execution(* org.example.wallet.service.TransactionManager.debit(..))")
    public void debitOperation() {

    }

    @Pointcut("execution(* org.example.wallet.service.TransactionManager.credit(..))")
    public void creditOperation() {
    }

    @AfterReturning(value = "debitOperation()", returning = "retValue")
    public void afterDebit(JoinPoint joinPoint, boolean retValue) throws SQLException, IOException {
        Long userId = (long) (joinPoint.getArgs())[1];
        if (retValue) {
            auditManager.audit(Operation.DEBIT, userId, AuditOption.SUCCESSFUL);
        } else {
            auditManager.audit(Operation.DEBIT, userId, AuditOption.UNSUCCESSFUL);
        }
    }


    @AfterReturning(value = "creditOperation()", returning = "retValue")
    public void afterCredit(JoinPoint joinPoint, boolean retValue) throws SQLException, IOException {
        Long userId = (long) (joinPoint.getArgs())[1];
        if (retValue) {
            auditManager.audit(Operation.CREDIT, userId, AuditOption.SUCCESSFUL);
        } else {
            auditManager.audit(Operation.CREDIT, userId, AuditOption.UNSUCCESSFUL);
        }
    }
}
