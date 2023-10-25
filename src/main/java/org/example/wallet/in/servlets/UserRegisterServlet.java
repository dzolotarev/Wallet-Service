package org.example.wallet.in.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.wallet.repository.db.AccountRepositoryDBImpl;
import org.example.wallet.repository.db.AuditRepositoryDBImpl;
import org.example.wallet.repository.db.TransactionRepositoryDBImpl;
import org.example.wallet.repository.db.UserRepositoryDBImpl;
import org.example.wallet.service.UserManager;
import org.example.wallet.service.db.DbManager;
import org.example.wallet.service.db.impl.DbManagerPostgresImpl;
import org.example.wallet.utils.ObjectMap;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class UserRegisterServlet extends HttpServlet {
    private UserManager userManager;
    private DbManager dbManager;

    @Override
    public void init(ServletConfig config) throws ServletException {
        dbManager = new DbManagerPostgresImpl();
        UserRepositoryDBImpl.init(dbManager);
        AccountRepositoryDBImpl.init(dbManager);
        TransactionRepositoryDBImpl.init(dbManager);
        AuditRepositoryDBImpl.init(dbManager);
        userManager = UserManager.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        JsonNode jsonNode = ObjectMap.get().readTree(req.getInputStream());
        String login = jsonNode.get("login").asText(null);
        String password = jsonNode.get("password").asText(null);
        String name = jsonNode.get("name").asText(null);

        if (login == null || password == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
            try {
                resp.getOutputStream().write(ObjectMap.get().writeValueAsBytes(userManager.register(login, password, name)));
            } catch (SQLException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {
        dbManager.closeConnection();
    }
}
