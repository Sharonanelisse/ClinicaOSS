package com.smarroquin.clinicaoss.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("*.xhtml")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String reqURI = req.getRequestURI();
        Object usuario = req.getSession().getAttribute("usuarioLogeado");

        boolean esRecursoEstatico =
                reqURI.startsWith(req.getContextPath() + "/javax.faces.resource") ||
                        reqURI.startsWith(req.getContextPath() + "/jakarta.faces.resource");
        boolean esLogin = reqURI.endsWith("login.xhtml");

        if (esLogin || esRecursoEstatico) {
            chain.doFilter(request, response);
        } else {
            if (usuario == null) {
                res.sendRedirect(req.getContextPath() + "/login.xhtml");
            } else {
                chain.doFilter(request, response);
            }
        }
    }
}
