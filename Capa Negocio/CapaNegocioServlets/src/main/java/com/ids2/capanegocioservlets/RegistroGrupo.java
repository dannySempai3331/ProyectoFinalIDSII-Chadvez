package com.ids2.capanegocioservlets;

import db.GrupoDaoImp;
import dtos.Grupo;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import dao.GrupoDao;

/*
Elaborado por:
Andy Gerald San Juan Martinez

Valeria Itzel Contreras Miranda

Jose Alejandro Terraza Gonzalez

Brayan Enrique Hernandez Flores

José Daniel Pérez Mejía
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
  
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection con;
		GrupoDao dao;
		GrupoDaoImp daoImp;
		List<Grupo> grupos;
		PrintWriter salida;
    	salida=resp.getWriter();
    	//HttpSession sesion = req.getSession();
		PreparedStatement statment;
		String query ="SELECT *FROM GRUPOS;";
		String query2 = "SELECT *FROM EQUIPOS WHERE IDGRUPO =1 ORDER BY PUNTAJE DESC ; ";
    	
		try {
			con = this.ds.getConnection();
			daoImp = new GrupoDaoImp();
			daoImp.setConnection(con);
			dao=daoImp;
			
			grupos =dao.getAllGroups();
			for(Grupo g:grupos) {
				salida.print(g.toString());
				//sesion.setAttribute("Lista de Grupos",g);
			}
			//List<GrupoDaoImp> getAllG = new ArrayList();
		
			resp.sendRedirect("rol.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
    	
    	
		//super.doGet(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*GrupoDaoImp dao;
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
			response.sendRedirect("rol.jsp");
			}catch(Exception e) {
				e.printStackTrace();
			}*/
		
		doGet(request, response);
	}

}
