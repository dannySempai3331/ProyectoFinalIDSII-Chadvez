package db;

import dao.UsuarioDao;
import dtos.Usuario;
import errores.PersistenciaException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDaoImp implements UsuarioDao {

    private Connection connection;

    public UsuarioDaoImp() {
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Usuario> getAllUsers() {
        Statement statement;
        ResultSet rs;
        Usuario usuario;
        List<Usuario> todos = new ArrayList<>();

        try {
            statement = this.connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM usuarios");

            while (rs.next()){
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idusuario"));
                usuario.setNoCuenta(rs.getString("nocuenta"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido1(rs.getString("apellido1"));

                if(rs.getString("apellido2")!= null) {
                    usuario.setApellido2(rs.getString("apellido2"));
                }
                else {
                    usuario.setApellido2("-");
                }

                usuario.setFechaNacimiento(rs.getDate("fechanacimiento"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setTipoUsuario(rs.getString("tipousuario"));
                usuario.setNombreUsuario(rs.getString("nombreusuario"));
                usuario.setContrasenna(rs.getString("contrasenna"));
                todos.add(usuario);
            }
            statement.close();
            connection.close();

            return todos;

        } catch (SQLException e) {
            throw new PersistenciaException(e);
        }

    }

    @Override
    public Usuario createUsuario(Usuario usuario) {
        return null;
    }

    @Override
    public Usuario modifyUsuario(Usuario usuario) {
        return null;
    }

    @Override
    public void deleteUsuario(Usuario usuario) {

    }

    @Override
    public Usuario getById(int id) {
        return null;
    }

    @Override
    public Usuario getByNoCuenta(String noCuenta) {
        return null;
    }

    @Override
    public Usuario getPersonalData(int id) {
        return null;
    }

    @Override
    public int getIdByNoCuenta(String noCuenta) {
        return 0;
    }
}
