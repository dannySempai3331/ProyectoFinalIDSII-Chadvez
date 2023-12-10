package db;

import dao.UsuarioDao;
import dtos.Usuario;
import errores.PersistenciaException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class UsuarioDaoImp implements UsuarioDao {

    private Connection connection;
    private JugadoresEquipoImp jei = new JugadoresEquipoImp();

    public UsuarioDaoImp() {
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
        jei.setConnection(connection);
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
        boolean wasCreated = false;
        Map<String, Object> datosUsuario = getAtributosFromUsuario(usuario);

        for(Map.Entry<String,Object> map : datosUsuario.entrySet()){
            chain1 += map.getKey() + ", ";
        }

        chain1 = chain1.substring(0,chain1.length()-2) + ")";
        queryCreate = queryCreate + chain1 + " VALUES(";

        for(Map.Entry<String,Object> map : datosUsuario.entrySet()){
            Object value = map.getValue();

            if ((value instanceof String)||(value instanceof LocalDate)){
                chain2 += "'" + value.toString()+ "', ";
            }
            else {
                chain2 += value.toString()+", ";
            }
        }

        queryCreate = queryCreate + chain2.substring(0,chain2.length()-2) + ")";
        //System.out.println(queryCreate);

        try {
            ps = this.connection.prepareStatement(queryCreate);
            ps.executeUpdate();
            ps.close();
            this.connection.close();
            wasCreated = true;

        } catch (SQLException e) {
            throw new PersistenciaException(e);
        }

        return wasCreated;
    }

    @Override
    public boolean modifyUsuario(Usuario usuario) {
        PreparedStatement ps;
        String queryUpdate = "UPDATE usuarios SET ";
        String chain1 = "";
        Map<String,Object> update;
        boolean wasModified = false;

        if(usuario.getIdUsuario() != 0){
            update = getAtributosFromUsuario(usuario);

            for (Map.Entry<String, Object> map : update.entrySet()){

                if ((map.getValue() instanceof String)||(map.getValue() instanceof LocalDate)){
                    chain1 += map.getKey() + " = '" + map.getValue() + "', ";
                }
                else {
                    chain1 += map.getKey() + " = " + map.getValue() + ", ";
                }
            }
        }

        queryUpdate = queryUpdate + chain1.substring(0,chain1.length()-2) + "WHERE idusuario = " + usuario.getIdUsuario();

        try {
            ps = this.connection.prepareStatement(queryUpdate);
            ps.executeUpdate();

            ps.close();
            this.connection.close();

            wasModified = true;

        }catch (SQLException e){
            throw new PersistenciaException(e);
        }

        return wasModified;
    }

    @Override
    public boolean deleteUsuario(Usuario usuario) {

        PreparedStatement ps;
        boolean wasDeleted = false;

        try {
            if (usuario.getIdUsuario() != 0) {
                if (checkIfExist(usuario.getIdUsuario())) {

                    if(checkIsJugador(usuario.getIdUsuario())){
                        jei.darDeBajaJugador(usuario.getIdUsuario());
                    }

                    ps = this.connection.prepareStatement("DELETE FROM usuarios WHERE idusuario = ?");
                    ps.setInt(1, usuario.getIdUsuario());

                    ps.executeUpdate();
                    ps.close();


                    this.connection.close();
                    wasDeleted = true;
                }
            }
        }catch (SQLException e){
            throw new PersistenciaException(e);
        }
        return wasDeleted;
    }

    @Override
    public Usuario getById(int id) {
        Usuario u = new Usuario();
        PreparedStatement ps;
        ResultSet rs;

        if (checkIfExist(id)) {

            try {

                ps = this.connection.prepareStatement("SELECT * FROM usuarios WHERE idusuario = ? ");
                ps.setInt(1, id);
                rs = ps.executeQuery();

                while (rs.next()) {
                    u.setIdUsuario(rs.getInt("idusuario"));
                    u.setNoCuenta(rs.getString("nocuenta"));
                    u.setNombre(rs.getString("nombre"));
                    u.setApellido1(rs.getString("apellido1"));

                    if (rs.getString("apellido2") != null) {
                        u.setApellido2(rs.getString("apellido2"));
                    } else {
                        u.setApellido2("-");
                    }

                    u.setFechaNacimiento(rs.getDate("fechanacimiento").toLocalDate());
                    u.setCorreo(rs.getString("correo"));
                    u.setTipoUsuario(rs.getString("tipousuario"));
                    u.setNombreUsuario(rs.getString("nombreusuario"));
                    u.setContrasenna(rs.getString("contrasenna"));
                }
                ps.close();
                this.connection.close();
                return u;

            } catch (SQLException e) {
                throw new PersistenciaException(e);
            }
        }
        return null;
    }

    @Override
    public Usuario getByNoCuenta(String noCuenta) {
        Usuario u = new Usuario();
        PreparedStatement ps;
        ResultSet rs;

        if (checkIfExist(noCuenta)){
            try {

                ps = this.connection.prepareStatement("SELECT * FROM usuarios WHERE nocuenta = ? ");
                ps.setString(1, noCuenta);
                rs = ps.executeQuery();

                while (rs.next()) {
                    u.setIdUsuario(rs.getInt("idusuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setApellido1(rs.getString("apellido1"));

                    if (rs.getString("apellido2") != null) {
                        u.setApellido2(rs.getString("apellido2"));
                    } else {
                        u.setApellido2("-");
                    }

                    u.setFechaNacimiento(rs.getDate("fechanacimiento").toLocalDate());
                    u.setCorreo(rs.getString("correo"));
                    u.setTipoUsuario(rs.getString("tipousuario"));
                    u.setNombreUsuario(rs.getString("nombreusuario"));
                    u.setContrasenna(rs.getString("contrasenna"));
                }
                ps.close();
                this.connection.close();
                return u;

            } catch (SQLException e) {
                throw new PersistenciaException(e);
            }
        }
        return null;
    }


    //A discusion, ya despues veo si se implementa.
    @Override
    public Usuario getPersonalData(int id) {
        Usuario usuario;
        PreparedStatement ps;
        ResultSet rs;

        try{
            ps = this.connection.prepareStatement("SELECT nocuenta, nombre, apellido1, apellido2, fechanacimiento, correo FROM usuarios WHERE idusuario = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            usuario = new Usuario();
            usuario.setIdUsuario(id);

            while (rs.next()){
                usuario.setNoCuenta(rs.getString("nocuenta"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido1(rs.getString("apellido1"));

                if (rs.getString("apellido2") != null) {
                    usuario.setApellido2(rs.getString("apellido2"));
                } else {
                    usuario.setApellido2(null);
                }

                usuario.setFechaNacimiento(rs.getDate("fechanacimiento").toLocalDate());
                usuario.setCorreo(rs.getString("correo"));

            }
            ps.close();
            this.connection.close();

        }catch (SQLException e){
            throw new PersistenciaException(e);
        }

        return usuario;
    }

    @Override
    public int getIdByNoCuenta(String noCuenta) {
        int id = 0;
        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = this.connection.prepareStatement("SELECT idusuario FROM usuarios WHERE nocuenta = ?");
            ps.setString(1, noCuenta);
            rs = ps.executeQuery();

            if (rs.next()){
                id = rs.getInt(1);
            }
        }catch (SQLException e) {
        throw new PersistenciaException(e);
        }
        return id;
    }

    @Override
    public LocalDate getFechaNacimiento(int id) {

        PreparedStatement ps;
        ResultSet rs;
        LocalDate date = null;

        try {
            ps = this.connection.prepareStatement("SELECT fechanacimiento FROM usuarios WHERE idusuario = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()){
                date = rs.getDate("fechanacimiento").toLocalDate();
            }
            return date;
        }catch (SQLException e) {
            throw new PersistenciaException(e);
        }

    }

    private Map<String,Object> getAtributosFromUsuario(Usuario u){

        Map<String, Object> resultado = new HashMap<String, Object>();
        LocalDate date = u.getFechaNacimiento();
        String val = u.getNoCuenta();

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

    private boolean checkIfExist(Object algo) {
        boolean isPresent = false;
        PreparedStatement ps;
        ResultSet rs;

        if (algo instanceof Integer) {
            try {

                ps = this.connection.prepareStatement("SELECT EXISTS (SELECT 1 FROM usuarios WHERE idusuario = ?)");
                ps.setInt(1, (int) algo);
                rs = ps.executeQuery();

                if (rs.next()) {
                    isPresent = rs.getBoolean(1);
                }

                ps.close();

            } catch (SQLException e) {
                throw new PersistenciaException(e);
            }

        } else if (algo instanceof String) {
            try {

                ps = this.connection.prepareStatement("SELECT EXISTS (SELECT 1 FROM usuarios WHERE nocuenta = ?)");
                ps.setString(1, algo.toString());
                rs = ps.executeQuery();

                if (rs.next()) {
                    isPresent = rs.getBoolean(1);
                }

                ps.close();

            } catch (SQLException e) {
                throw new PersistenciaException(e);
            }
        }
        return isPresent;
    }

    private boolean checkIsJugador(int id){
        boolean isJugador = false;
        PreparedStatement ps;
        ResultSet rs;

        try{
            ps = this.connection.prepareStatement("SELECT tipousuario FROM usuarios WHERE idusuario = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if(rs.next()){
                if(Objects.equals(rs.getString(1), "jugador")){
                    isJugador = true;
                }
            }
           ps.close();

        }catch (SQLException e){
            throw new PersistenciaException(e);
        }
        return isJugador;
    }

}
