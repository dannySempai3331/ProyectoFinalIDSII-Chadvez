package com.ids2.capanegocioservlets;

import db.TorneoDaoImp;
import dtos.Torneo;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/*
Elaborado por:
Andy Gerald San Juan Martinez

Valeria Itzel Contreras Miranda

Jose Alejandro Terraza Gonzalez

Brayan Enrique Hernandez Flores

José Daniel Pérez Mejía
*/

/**
 * Servlet implementation class RegistroTorneo
 */
public class RegistroTorneo extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource ds;
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        InitialContext cxt;
        try {
            cxt=new InitialContext();
            if(cxt !=null) {
                this.ds=(DataSource) cxt.lookup("java:/comp/env/jdbc/ds");
                if(this.ds==null) {
                    throw new ServletException("Data source not found!");
                }
            }
        }catch(NamingException e) {
            throw new ServletException("No hay contexto inicial :(");

        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        TorneoDaoImp dao;
        Connection con;
        String nombre;
        String disciplina;
        String noEquipos;
        String noGrupos;
        String porcentajeavance;


        Torneo torneo;
        try {
            con=this.ds.getConnection();
            dao = new TorneoDaoImp();
            dao.setConnection(con);

            nombre = request.getParameter("nombre");
            disciplina = request.getParameter("disciplina");
            noEquipos = request.getParameter("noequipos");
            noGrupos = request.getParameter("nogrupos");
            porcentajeavance = request.getParameter("porcentajeavance");
            fechaInicio = request.getParameter("fechainicio");
            Date fechaDate = sdf.parse(fechaInicio);
            Instant instant = fechaDate.toInstant();
            LocalDate fechaInicio = instant.atZone(ZoneId.systemDefault()).toLocalDate();

            fechaFin = request.getParameter("fechafin");
            Date fechaDate = sdf.parse(fechaFin);
            Instant instant = fechaDate.toInstant();
            LocalDate fechaFin = instant.atZone(ZoneId.systemDefault()).toLocalDate();





            torneo = new Torneo();

            torneo.setNombre(nombre);
            torneo.setDisciplina(disciplina);
            torneo.setNoEquipos(Integer.parseInt(noEquipos));
            torneo.setNoGrupos(Integer.parseInt(noGrupos));
            torneo.setPorcentajeAvance(Double.parseDouble(porcentajeavance));
            torneo.setFechaInicio(fechaInicio);
            torneo.setFechaFin(fechaFin);

            dao.createTorneo(torneo);
            response.sendRedirect("torneosregistro.html");
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

}
