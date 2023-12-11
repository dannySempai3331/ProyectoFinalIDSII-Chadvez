package com.ids2.capanegocioservlets;

import db.UsuarioDaoImp;
import dtos.Usuario;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/*
Elaborado por:
Andy Gerald San Juan Martinez

Valeria Itzel Contreras Miranda

Jose Alejandro Terraza Gonzalez

Brayan Enrique Hernandez Flores

José Daniel Pérez Mejía
*/

@WebServlet(value = "/login")
public class LogIn extends HttpServlet {
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

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection c;
        Usuario usuario1 = new Usuario();
        Usuario usuario2;
        UsuarioDaoImp udi = new UsuarioDaoImp();

        try {
            c = this.ds.getConnection();
            udi.setConnection(c);

            usuario1.setCorreo(req.getParameter("emailLogIn"));
            usuario1.setContrasenna(req.getParameter("passwordLogIn"));

            usuario2 = udi.getDataForLogIn(usuario1);

            //Switch case para verificar si el tipo de usuario es un jugador, admin o juez

            switch (usuario2.getTipoUsuario()) {
                case "jugador":
                    resp.sendRedirect("jugador.html");
                    break;
                case "admin":
                    resp.sendRedirect("admin.html");
                    break;
                case "juez":
                    resp.sendRedirect("juez.html");
                    break;
                default:
                    resp.sendRedirect("index.html");
                    break;
            }

        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
