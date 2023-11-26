package db;

import dao.UsuarioDao;
import dtos.Usuario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDaoImp implements UsuarioDao {

    private Connection connection;

    public UsuarioDaoImp() {
    }

    @Override
    public List<Usuario> getAllUsers() {
        Statement statement;
        ResultSet rs;
        Usuario usuario;
        List<Usuario> todos = new ArrayList<>();


        return null;
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
