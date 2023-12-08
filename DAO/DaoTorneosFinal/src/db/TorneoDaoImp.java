package db;

import dao.TorneoDao;
import dtos.Torneo;
import dtos.Usuario;
import errores.PersistenciaException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TorneoDaoImp implements TorneoDao{

    private Connection connection;

    public TorneoDaoImp() {
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public List<Torneo> getAllUsers() {

        Statement statement;
        ResultSet rs;
        Torneo torneo;
        List<Torneo> todos = new ArrayList<>();

        try {
            statement = this.connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM Torneos");

            while (rs.next()){
                torneo = new Torneo();
                torneo.setIdTorneo(rs.getInt("idtorneo"));
                torneo.setNombre(rs.getString("nombre"));
                torneo.setDisciplina(rs.getString("disciplina"));
                torneo.setNoEquipos(rs.getInt("noequipos"));
                torneo.setFechaInicio(rs.getDate("fechainicio").toLocalDate());
                torneo.setFechaFin(rs.getDate("fechafin").toLocalDate());
                torneo.setNoGrupos(rs.getInt("nogrupos"));
                torneo.setPorcentajeAvance(rs.getDouble("porcentajeavance"));
                todos.add(torneo);
            }
            statement.close();
            connection.close();

            return todos;

        } catch (SQLException e) {
            throw new PersistenciaException(e);
        }
    }

    @Override
    public boolean createTorneo(Torneo torneo) {

        PreparedStatement ps;
        String queryCreate ="INSERT INTO torneos ";
        String chain1 = "(";
        String chain2 = "";
        boolean wasCreated = false;
        Map<String, Object> datosTorneo = getAtributosFromTorneo(torneo);

        for(Map.Entry<String,Object> map : datosTorneo.entrySet()){
            chain1 += map.getKey() + ", ";
        }

        chain1 = chain1.substring(0,chain1.length()-2) + ")";
        queryCreate = queryCreate + chain1 + " VALUES(";

        for(Map.Entry<String,Object> map : datosTorneo.entrySet()){
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
    public boolean modifyTorneo(Torneo torneo) {
        PreparedStatement ps;
        String queryUpdate = "UPDATE torneos SET ";
        String chain1 = "";
        Map<String,Object> update;
        boolean wasModified = false;

        if(torneo.getIdTorneo() != 0){
            update = getAtributosFromTorneo(torneo);

            for (Map.Entry<String, Object> map : update.entrySet()){

                if ((map.getValue() instanceof String)||(map.getValue() instanceof LocalDate)){
                    chain1 += map.getKey() + " = '" + map.getValue() + "', ";
                }
                else {
                    chain1 += map.getKey() + " = " + map.getValue() + ", ";
                }
            }
        }

        queryUpdate = queryUpdate + chain1.substring(0,chain1.length()-2) + "WHERE idtorneo = " + torneo.getIdTorneo();

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
    public boolean deleteTorneo(Torneo torneo) {

        PreparedStatement ps;
        boolean wasDeleted = false;

        try {
            if (torneo.getIdTorneo() != 0) {
                if (checkIfExist(torneo.getIdTorneo())) {

                    ps = this.connection.prepareStatement("DELETE FROM torneos WHERE idtorneo = ?");
                    ps.setInt(1, torneo.getIdTorneo());

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
    public Torneo getById(int id) {
        Torneo t = new Torneo();
        PreparedStatement ps;
        ResultSet rs;

        if (checkIfExist(id)) {

            try {

                ps = this.connection.prepareStatement("SELECT * FROM torneos WHERE idtorneo = ? ");
                ps.setInt(1, id);
                rs = ps.executeQuery();

                while (rs.next()) {
                    t.setIdTorneo(rs.getInt("idtorneo"));
                    t.setNombre(rs.getString("nombre"));
                    t.setDisciplina(rs.getString("disciplina"));
                    t.setNoEquipos(rs.getInt("noequipos"));

                    t.setFechaInicio(rs.getDate("fechainicio").toLocalDate());
                    t.setFechaFin(rs.getDate("fechafin").toLocalDate());

                    t.setNoGrupos(rs.getInt("nogrupos"));
                    t.setPorcentajeAvance(rs.getDouble("porcentajeavance"));
                }
                ps.close();
                this.connection.close();
                return t;

            } catch (SQLException e) {
                throw new PersistenciaException(e);
            }
        }
        return null;
    }

    /*
    @Override
    public Torneo getByNoCuenta(String noCuenta) {
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
    } */


    //A discusion, ya despues veo si se implementa.


    /*@Override
        public Torneo getPersonalData(int id) {
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

        return torneo;
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

    }                           */

    private Map<String,Object> getAtributosFromTorneo(Torneo t){

        Map<String, Object> resultado = new HashMap<String, Object>();

        LocalDate dateI = t.getFechaInicio();
        LocalDate dateF = t.getFechaFin();

        String val = t.getNombre();

        if (val != null){
            resultado.put("idTorneo",val);
        }

        val = t.getDisciplina();

        if (val != null){
            resultado.put("disciplina",val);
        }

        int num = t.getNoEquipos();

        if (num != 0){
            resultado.put("noequipos",num);
        }


        if (dateI != null){
            resultado.put("fechainicio",dateI);
        }

        if (dateF != null){
            resultado.put("fichafin",dateF);
        }

        num = t.getNoGrupos();

        if (val != null){
            resultado.put("nogrupos",num);
        }

        double porc = t.getPorcentajeAvance();

        if (porc != -1){
            resultado.put("porcentajeavance",porc);
        }

        return resultado;
    }

    public boolean checkIfExist(Object algo) {
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

                ps = this.connection.prepareStatement("SELECT EXISTS (SELECT 1 FROM torneos WHERE idtorneo = ?)");
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

}

