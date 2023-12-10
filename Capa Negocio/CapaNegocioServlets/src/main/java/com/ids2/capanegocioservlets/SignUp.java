package com.ids2.capanegocioservlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(value = "/signup")
public class SignUp extends HttpServlet {
    private DataSource ds;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        InitialContext initialContext;

        try {

            initialContext = new InitialContext();

            if (initialContext != null) {
                this.ds = (DataSource) initialContext.lookup("java:comp/env/jdbc/ds");

                if (this.ds == null) {
                    throw new ServletException("Data source no encontrado!");
                }
            }

        } catch (NamingException e) {
            throw new ServletException("No hay contexto inicial!");
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection c;

        try {
            c = this.ds.getConnection();
            response.getWriter().append("Served at: ").append(c.toString());
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //doGet(request,response);

    }
}
