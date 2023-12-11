package pruevas;

import db.*;
import dtos.Equipo;
import dtos.Grupo;
import dtos.Torneo;
import dtos.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/*
* Proyecto entrega dia del ordinario

Examen teorico viernes de 07 a 23 hrs

Los nombres de los integrantes deben estar en todas las clases

Lo puede subir solo uno (recomendacion subirlo todos).

Mandar los queries de la BD.

* Fecha del ordinario 11 de diciembre a las 11 am
*
Documentacion en la rubrica.
*
* java -jar ruta/lib/derbyrun.jar server start
*
oracle
bd2 (ibm)

* */

public class Main {
    public static void main(String[] args) {

        Connection connection;
        UsuarioDaoImp usuarioDaoImp = new UsuarioDaoImp();
        JugadoresEquipoImp jei = new JugadoresEquipoImp();

        String url = "jdbc:postgresql://localhost:5432/torneos";
        //Derby
        //String url = "jdbc:derby://localhost:1527/TORNEODB;create=false";
        String user = "postgres";
        String password = "megumin";

        try {
           Class.forName("org.postgresql.Driver");
           connection = DriverManager.getConnection(url,user,password);
            //Derby
            //connection=DriverManager.getConnection(url)
           usuarioDaoImp.setConnection(connection);
           Usuario u = new Usuario();
           u.setCorreo("maria.lopez@example.com");
           u.setContrasenna("654321");

            System.out.println(usuarioDaoImp.getDataForLogIn(u));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
