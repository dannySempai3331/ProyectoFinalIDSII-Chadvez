package com.ids2.capanegocioservlets;

import db.GrupoDaoImp;
import dtos.Grupo;
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
 * Servlet implementation class RegistroGrupo
 */
public class RegistroGrupo extends HttpServlet {
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
		GrupoDaoImp dao;
		Connection con;
		String id;
		String nombre;
		Grupo grupo;
		try {
			con=this.ds.getConnection();
			dao = new GrupoDaoImp();
			dao.setConnection(con);
			 id=request.getParameter("idgrupo");
			 nombre = request.getParameter("nombregrupo");
		
		grupo = new Grupo();
		grupo.setIdGrupo(Integer.parseInt(id));
		grupo.setNombre(nombre);
		dao.createGrupo(grupo);
		response.sendRedirect("altaGrupo.html");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//doGet(request, response);
	}

}
