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
  
	
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection con;
		GrupoDaoImp dao;
		PreparedStatement statment;
		String query ="SELECT *FROM GRUPOS;";
		String query2 = "SELECT *FROM EQUIPOS WHERE IDGRUPO =1 ORDER BY PUNTAJE DESC ; ";
    	
		try {
			con = this.ds.getConnection();
			dao = new GrupoDaoImp();
			dao.setConnection(con);
			dao.getAllGroups();
			List<GrupoDaoImp> getAllG = new ArrayList();
			
			HttpSession sesion = req.getSession();
			sesion.setAttribute("Lista de Grupos", getAllG);
			resp.sendRedirect("rol.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		PrintWriter salida;
    	salida=resp.getWriter();
    	
    	
		//super.doGet(req, resp);
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
