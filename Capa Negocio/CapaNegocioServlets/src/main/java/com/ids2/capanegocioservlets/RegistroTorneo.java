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
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
@WebServlet(value = "/registroTorneo")
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
        String fechaInicio;
        String fechaFin;


        Torneo torneo;
        try {
            con = this.ds.getConnection();
            dao = new TorneoDaoImp();
            dao.setConnection(con);

            nombre = request.getParameter("nombre");
            disciplina = request.getParameter("disciplina");
            noEquipos = request.getParameter("noequipos");
            noGrupos = request.getParameter("nogrupos");

            fechaInicio = request.getParameter("fechaInicio");
            Date fechaDate = sdf.parse(fechaInicio);
            Instant instant = fechaDate.toInstant();
            LocalDate fechaInc = instant.atZone(ZoneId.systemDefault()).toLocalDate();

            fechaFin = request.getParameter("fechaFin");
            Date fechaD = sdf.parse(fechaFin);
            Instant instant1 = fechaD.toInstant();
            LocalDate fechaF = instant1.atZone(ZoneId.systemDefault()).toLocalDate();

            torneo = new Torneo();
<<<<<<< HEAD


=======
>>>>>>> fc2f754d44af2414f1d6ad8603e0ba6e53372090
            torneo.setNombre(nombre);
            torneo.setDisciplina(disciplina);
            torneo.setNoEquipos(Integer.parseInt(noEquipos));
            torneo.setNoGrupos(Integer.parseInt(noGrupos));
            torneo.setPorcentajeAvance(0.0);
            torneo.setFechaInicio(fechaInc);
            torneo.setFechaFin(fechaF);

<<<<<<< HEAD

=======
>>>>>>> fc2f754d44af2414f1d6ad8603e0ba6e53372090
            //torneo.setIdTorneo(Integer.parseInt(idTorneo));
            torneo.setNombre(nombre);
            //torneo.set

            dao.createTorneo(torneo);
            response.sendRedirect("admin.html");
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

}
