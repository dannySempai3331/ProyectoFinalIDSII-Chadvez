package db;

import dao.UsuarioDao;
import dtos.Usuario;
import errores.PersistenciaException;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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

                usuario.setFechaNacimiento(rs.getDate("fechanacimiento").toLocalDate());
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
    public boolean createUsuario(Usuario usuario) {

        PreparedStatement ps;
        String queryCreate ="INSERT INTO usuarios ";
        String chain1 = "(";
        String chain2 = "";
        Map<String, Object> datosUsuario = getAtributosFromUsuario(usuario);

        for(Map.Entry<String,Object> map : datosUsuario.entrySet()){
            chain1 += map.getKey() + ", ";
        }

        chain1 = chain1.substring(0,chain1.length()-2) + ")";
        System.out.println(chain1);
        queryCreate = queryCreate + chain1 + " VALUES(";
        System.out.println(queryCreate);

        for(Map.Entry<String,Object> map : datosUsuario.entrySet()){
            Object value = map.getValue();

            if (value instanceof String){
                chain2 += "'"+value.toString()+"', ";
            } else if (value instanceof LocalDate) {
                chain2 += "'" + value.toString() + "', ";
            } else {
                chain2 += value.toString()+", ";
            }
        }

        queryCreate = queryCreate + chain2.substring(0,chain2.length()-2) + ")";
        System.out.println(queryCreate);

        try {
            ps = this.connection.prepareStatement(queryCreate);
            ps.executeUpdate();
            ps.close();
            this.connection.close();
            return true;

        } catch (SQLException e) {
            throw new PersistenciaException(e);
        }
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

    private Map<String,Object> getAtributosFromUsuario(Usuario u){

        Map<String, Object> resultado = new HashMap<String, Object>();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        LocalDate date = u.getFechaNacimiento();
        String val = u.getNoCuenta();

        /*
        try {
            date = formato.parse(u.getFechaNacimiento().toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }*/

        if (val != null){
            resultado.put("nocuenta",val);
        }

        val = u.getNombre();

        if (val != null){
            resultado.put("nombre",val);
        }

        val = u.getApellido1();

        if (val != null){
            resultado.put("apellido1",val);
        }

        val = u.getApellido2();

        if (val != null){
            resultado.put("apellido2",val);
        }

        if (date != null){
            resultado.put("fechanacimiento",date);
        }

        val = u.getCorreo();

        if (val != null){
            resultado.put("correo",val);
        }

        val = u.getTipoUsuario();

        if (val != null){
            resultado.put("tipousuario",val);
        }

        val = u.getNombreUsuario();

        if (val != null){
            resultado.put("nombreusuario",val);
        }

        val = u.getContrasenna();

        if (val != null){
            resultado.put("contrasenna",val);
        }

        return resultado;
    }
}
