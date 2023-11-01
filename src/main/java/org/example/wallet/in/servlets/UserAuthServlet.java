package org.example.wallet.in.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.wallet.domain.User;
import org.example.wallet.repository.db.AccountRepositoryDBImpl;
import org.example.wallet.repository.db.AuditRepositoryDBImpl;
import org.example.wallet.repository.db.TransactionRepositoryDBImpl;
import org.example.wallet.repository.db.UserRepositoryDBImpl;
import org.example.wallet.service.UserManager;
import org.example.wallet.service.db.DbManager;
import org.example.wallet.service.db.impl.DbManagerPostgresImpl;
import org.example.wallet.utils.JwtHelper;
import org.example.wallet.utils.ObjectMap;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/auth")
public class UserAuthServlet extends HttpServlet {
    private UserManager userManager;
    private DbManager dbManager;
    private User user;

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

        if (login == null || password == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            try {
                user = UserManager.getInstance().login(login, password);
                if (user != null) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getOutputStream().write(ObjectMap.get().writeValueAsBytes(JwtHelper.createJWT(String.valueOf(user.getId()))));
                } else {
                    resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    resp.getOutputStream().write(ObjectMap.get().writeValueAsBytes("Unauthorized"));
                }
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
