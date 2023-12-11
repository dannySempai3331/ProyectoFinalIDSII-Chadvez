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
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/*
Elaborado por:
Andy Gerald San Juan Martinez

Valeria Itzel Contreras Miranda

Jose Alejandro Terraza Gonzalez

Brayan Enrique Hernandez Flores

José Daniel Pérez Mejía
*/

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

        Usuario usuario = new Usuario();
        UsuarioDaoImp udi = new UsuarioDaoImp();
        String fechaString;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            c = this.ds.getConnection();
            udi.setConnection(c);
            usuario.setNoCuenta(request.getParameter("noCuenta"));
            usuario.setNombre(request.getParameter("nombre"));
            usuario.setApellido1(request.getParameter("ap1"));
            usuario.setApellido2(request.getParameter("ap2"));

            fechaString = request.getParameter("fechaNacimiento");
            Date fechaDate = sdf.parse(fechaString);
            Instant instant = fechaDate.toInstant();
            LocalDate fechaNacimiento = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            usuario.setTipoUsuario("jugador");
            usuario.setFechaNacimiento(fechaNacimiento);
            usuario.setCorreo(request.getParameter("email"));
            usuario.setNombreUsuario(request.getParameter("nombreUsuario"));
            usuario.setContrasenna(request.getParameter("contrasenna"));

            System.out.println(usuario);
            udi.createUsuario(usuario);

            response.sendRedirect("index.html");

        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
