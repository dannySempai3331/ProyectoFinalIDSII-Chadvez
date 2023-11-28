package pruevas;

import db.UsuarioDaoImp;
import dtos.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
            u.setCorreo("andy123@mail.com");
            u.setNombreUsuario("farsa123");

            System.out.println(UsuarioDaoImp.modifyUsuario(u));


            /*
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
