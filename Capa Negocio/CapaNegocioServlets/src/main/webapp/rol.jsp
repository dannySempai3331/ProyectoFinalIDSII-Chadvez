<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="java.util.List"%>
  <%@page import="db.GrupoDaoImp"%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viweport" content="width=device-widht, initial">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://kit.fontawesome.com/852d701f33.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="style.css">
    <title> Rol de equipos</title>

    <div class="header-content container">
        <div class="content">
            <h1></h1>
            <p>

            </p>

        </div>
        <img src="images/fondo.png" alt="">
    </div>
</head>

<body>

    <header>
        <h2 class="logo">Venationes</h2>
		<h2 class="logo"> Lista de Grupos </h2>
		<%
			//List<GrupoDaoImp> listGrupo = (List)request.getSession().getAttribute("listGrupo");
			GrupoDaoImp g = new GrupoDaoImp();		
		%>
				<p><b> Grupo: <% g.getAllGroups(); %></b></p>


		<% %>
        <nav class="navegacion">
            <a href="indexP.html">Inicio</a>
            <a href="servicios.html">Informacion</a>
            <a href="rol.jsp">Rol de juego</a>
            <a href="equipos.html">Equipos</a>


        </nav>
    </header>
    <section>

    </section>

</body>

</html>