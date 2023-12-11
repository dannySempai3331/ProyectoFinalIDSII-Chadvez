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
        TorneoDaoImp dao;
        Connection con;
        String idTorneo;
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



            torneo = new Torneo();

            //torneo.setIdTorneo(Integer.parseInt(idTorneo));
            torneo.setNombre(nombre);
            //torneo.set
            dao.createTorneo(torneo);
            response.sendRedirect("altaTorneo.html");
        }catch(Exception e) {
            e.printStackTrace();
        }

        //doGet(request, response);
    }

}
