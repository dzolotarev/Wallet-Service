package org.example.wallet.in.servlets;

import io.jsonwebtoken.Claims;
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
import org.example.wallet.service.TransactionManager;
import org.example.wallet.service.db.DbManager;
import org.example.wallet.service.db.impl.DbManagerPostgresImpl;
import org.example.wallet.utils.JwtHelper;
import org.example.wallet.utils.ObjectMap;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/history")
public class UserShowHistoryServlet extends HttpServlet {
    private DbManager dbManager;

    @Override
    public void init(ServletConfig config) throws ServletException {
        dbManager = new DbManagerPostgresImpl();
        UserRepositoryDBImpl.init(dbManager);
        AccountRepositoryDBImpl.init(dbManager);
        TransactionRepositoryDBImpl.init(dbManager);
        AuditRepositoryDBImpl.init(dbManager);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String jwt = ObjectMap.get().readTree(req.getInputStream()).get("token").asText(null);
        if (jwt == null) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getOutputStream().write(ObjectMap.get().writeValueAsBytes("Unauthorized"));
        } else {
            try {
                Claims claims = JwtHelper.decodeJWT(jwt);
                long id = Long.parseLong(claims != null ? claims.getId() : "-1");
                if (id < 0) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                } else {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getOutputStream().write(ObjectMap.get().writeValueAsBytes(TransactionManager.getInstance().transactions(id)));
                }
            } catch (SQLException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                e.printStackTrace();
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resp.getOutputStream().write(ObjectMap.get().writeValueAsBytes("Unauthorized"));
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {
        dbManager.closeConnection();
    }
}
