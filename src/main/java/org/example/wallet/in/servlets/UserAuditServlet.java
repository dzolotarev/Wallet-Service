package org.example.wallet.in.servlets;

import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.wallet.domain.Audit;
import org.example.wallet.repository.db.AuditRepositoryDBImpl;
import org.example.wallet.service.AuditManager;
import org.example.wallet.service.db.DbManager;
import org.example.wallet.service.db.impl.DbManagerPostgresImpl;
import org.example.wallet.utils.JwtHelper;
import org.example.wallet.utils.ObjectMap;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/audit")
public class UserAuditServlet extends HttpServlet {
    private DbManager dbManager;
    private List<Audit> auditList;

    @Override
    public void init() throws ServletException {
        dbManager = new DbManagerPostgresImpl();
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
            Claims claims = null;
            try {
                claims = JwtHelper.decodeJWT(jwt);
                auditList = AuditManager.getInstance().getAll();
                if (auditList == null) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                } else {
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
                resp.getOutputStream().write(ObjectMap.get().writeValueAsBytes(auditList));
            } catch (SQLException | IOException e) {
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
