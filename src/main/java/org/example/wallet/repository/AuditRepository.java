package org.example.wallet.repository;

import org.example.wallet.domain.Audit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface AuditRepository {
    /**
     * The method creates and adds the Audit object to the repository
     *
     * @param audit Audit.class object, not null
     * @return true/false: depending on the result of the operation
     */
    boolean add(Audit audit) throws SQLException, IOException;

    /**
     * The method returns the entire list of Audit objects from the repository
     *
     * @return List collection of Audit's objects or empty list
     */
    List<Audit> findAll() throws SQLException, IOException;
}
