package pruevas;

import db.UsuarioDaoImp;
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
        UsuarioDaoImp UsuarioDaoImp = new UsuarioDaoImp();

        String url = "jdbc:postgresql://localhost:5432/torneos";
        String user = "postgres";
        String password = "megumin";


        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url,user,password);

            UsuarioDaoImp.setConnection(connection);
            Usuario u = new Usuario();
            u.setIdUsuario(5);
            System.out.println(UsuarioDaoImp.getFechaNacimiento(2));

            /*
            Usuario u = new Usuario();
            u.setIdUsuario(5);
            u.setCorreo("andy123@mail.com");
            u.setNombreUsuario("farsa123");

            System.out.println(UsuarioDaoImp.modifyUsuario(u));

            Usuario u = new Usuario();
            LocalDate fechaEspecifica = LocalDate.of(2022, 12, 31);
            u.setNoCuenta("12345");
            u.setNombre("Andy Gerald");
            u.setApellido1("San Juan");
            u.setApellido2("Martinez");
            u.setFechaNacimiento(fechaEspecifica);
            u.setCorreo("andy123@mail.com");
            u.setTipoUsuario("admin");
            u.setNombreUsuario("AndyxDiana");
            u.setContrasenna("dianaforever");
            System.out.println(UsuarioDaoImp.createUsuario(u));

            List<Usuario> u = UsuarioDaoImp.getAllUsers();
            //Iterar sobre la lista u e imprimir usuarios
            for (Usuario usuario : u) {
                System.out.println(usuario);
            }*/
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
