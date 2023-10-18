package org.example.wallet.infrastructure.in;

import java.io.IOException;
import java.sql.SQLException;

public interface Command {
    void execute() throws IOException, SQLException;
}
