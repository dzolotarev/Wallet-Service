package org.example.wallet.infrastructure.in;

import java.io.IOException;

public interface Command {
    void execute() throws IOException;
}
