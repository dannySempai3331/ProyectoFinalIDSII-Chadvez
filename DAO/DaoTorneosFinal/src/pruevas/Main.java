package pruevas;

import db.UsuarioDaoImp;
import dtos.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Connection connection;
        String url = "jdbc:postgresql://localhost:5432/torneos";
        String user = "postgres";
        String password = "megumin";

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url,user,password);

            UsuarioDaoImp UsuarioDaoImp = new UsuarioDaoImp();
            UsuarioDaoImp.setConnection(connection);

            List<Usuario> u = UsuarioDaoImp.getAllUsers();

            //Iterar sobre la lista u e imprimir usuarios
            for (Usuario usuario : u) {
                System.out.println(usuario);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
