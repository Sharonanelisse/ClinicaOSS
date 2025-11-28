package com.smarroquin.clinicaoss.controllers;

import com.smarroquin.clinicaoss.models.Usuario;
import com.smarroquin.clinicaoss.models.Usuario;
import com.smarroquin.clinicaoss.service.IUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login", "/logout"})
public class LoginBean extends HttpServlet {
    private final IUserService userService = new IUserService() {
        @Override
        public List<Usuario> list(int page, int size) {
            return List.of();
        }

        @Override
        public int totalPages(int size) {
            return 0;
        }

        @Override
        public Optional<Usuario> getById(int id) {
            return Optional.empty();
        }

        @Override
        public Optional<Usuario> getByUsername(String username) {
            return Optional.empty();
        }

        @Override
        public void save(Usuario user) {

        }

        @Override
        public void delete(int id, int currentUserId) {

        }

        @Override
        public boolean validateLogin(String username, String password) {
            return false;
        }
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("/logout".equals(req.getServletPath())) {
            HttpSession s = req.getSession(false);
            if (s != null) s.invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.getRequestDispatcher("/login.xhtml").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (userService.validateLogin(username, password)) {
            Optional<Usuario> u = userService.getByUsername(username);
            HttpSession s = req.getSession(true);
            s.setAttribute("user", u.get());
            resp.sendRedirect(req.getContextPath() + "/home");

        } else {
            req.setAttribute("error", "Credenciales inválidas o usuario inactivo.");
            req.getRequestDispatcher("/login.xhtml").forward(req, resp);
        }
    }
}