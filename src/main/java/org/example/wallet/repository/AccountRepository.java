package org.example.wallet.repository;

import org.example.wallet.domain.Account;

public interface AccountRepository {

    /**
     * The method creates an Account given userId and add it to repository
     *
     * @param userId user ID from User, not null
     * @return true/false: depending on the result of account creation
     */
    boolean createAccount(long userId);

    /**
     * The method credits the value to the user's Account in repository
     *
     * @param userId user ID from User, not null
     * @param value  the amount of funds credited to the Account
     * @return true/false: depending on the result of the operation
     */
    boolean credit(long userId, long value);

    /**
     * The method removes funds from the user's Account in repository
     *
     * @param userId user ID from User, not null
     * @param value  the amount of funds debited from the Account
     * @return true/false: depending on the result of the operation
     */
    boolean debit(long userId, long value);

    /**
     * The method returns the Account object by userId from repository
     *
     * @param userId user ID from User, not null
     * @return returns the Account object by userId from repository
     */
    Account findUserAccount(long userId);
}
