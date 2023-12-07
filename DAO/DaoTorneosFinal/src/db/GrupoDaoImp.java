package db;

import dao.GrupoDao;
import dtos.Grupo;
import dtos.Usuario;
import errores.PersistenciaException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrupoDaoImp implements GrupoDao {
    private Connection connection;

    public GrupoDaoImp(){
    }
    public void setConnection(Connection connection){this.connection =connection;}

    @Override
    public List<Grupo> getAllGroups() {
        Statement statement;
        ResultSet rs;
        Grupo grupo;
        List<Grupo> todos= new ArrayList<>();
        try{
            statement = this.connection.createStatement();
            rs = statement.executeQuery("SELECT *FROM grupos");
            while(rs.next()){
                grupo = new Grupo();
                grupo.setIdGrupo(rs.getInt("idgrupo"));
                grupo.setNombre(rs.getString("nombre"));
                todos.add(grupo);
            }
             statement.close();
            connection.close();
            return todos;
        }catch (SQLException e) {
            throw new PersistenciaException(e);
        }
    }

    @Override
    public boolean createGrupo(Grupo grupo) {
        PreparedStatement ps;
        String queryCreate = "INSERT INTO grupos";
        String chain1 = "(";
        String chain2 = "";
        boolean wasCreated =false;
        Map<String, Object> datosGrupo = getAtributosFromGrupo(grupo);
        for(Map.Entry<String,Object> map : datosGrupo.entrySet()){
            chain1 += map.getKey() + ", ";
        }
        chain1=chain1.substring(0,chain1.length()-2)+ ")";
        queryCreate=queryCreate + chain1 + "VALUES(";

        for(Map.Entry<String,Object> map : datosGrupo.entrySet()) {
            Object value = map.getValue();

            if ((value instanceof String)){
                chain2 += "'" + value.toString()+"',";
            }
            else {
                chain2 +=value.toString()+", ";
            }
        }
        queryCreate=queryCreate + chain2.substring(0,chain2.length()-2) + ")";
        try{
            ps = this.connection.prepareStatement(queryCreate);
            ps.executeUpdate();
            ps.close();
            this.connection.close();
            wasCreated=true;
        } catch (SQLException e){
            throw new PersistenciaException(e);
        }
        return  wasCreated;
    }

    @Override
    public boolean modifyGrupo(Grupo grupo) {
        PreparedStatement ps;
        String queryUpdate= "UPDATE grupos SET ";
        String chain1= "";
        Map<String,Object> update;
        boolean wasModified = false;
        if(grupo.getIdGrupo()!=0){
            update=getAtributosFromGrupo(grupo);
            for (Map.Entry<String,Object> map : update.entrySet()){
                if((map.getValue()instanceof String)){
                    chain1 += map.getKey() + " = '" + map.getValue() + "', ";
                }
                else {
                    chain1 += map.getKey() + " = " + map.getValue() + ", ";
                }
            }
        }
        queryUpdate = queryUpdate + chain1.substring(0,chain1.length()-2)+ "WHERE idgrupo = "+ grupo.getIdGrupo();
        try{
            ps=this.connection.prepareStatement(queryUpdate);
            ps.executeUpdate();
            ps.close();
            this.connection.close();
            wasModified = true;
        }catch (SQLException e){
            throw  new PersistenciaException(e);
        }
        return wasModified;
    }

    @Override
    public boolean deleteGrupo(Grupo grupo) {
        PreparedStatement ps;
        boolean wasDeleted = false;
        try {
            if (grupo.getIdGrupo() != 0) {
                if (checkIfExist(grupo.getIdGrupo())) {
                    ps = this.connection.prepareStatement("DELETE FROM grupos WHERE idgrupo = ?");
                    ps.setInt(1, grupo.getIdGrupo());
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

    private Map<String,Object> getAtributosFromGrupo(Grupo g){
        Map<String, Object> resultado = new HashMap<String,Object>();
        String val = g.getNombre();
        if (val!=null){
            resultado.put("nombre",val);
        }
        return resultado;
    }

    public boolean checkIfExist(Object algo){
        boolean isPresent = false;
        PreparedStatement ps;
        ResultSet rs;
        if(algo instanceof  Integer){
            try{
                ps = this.connection.prepareStatement("SELECT EXISTS (SELECT 1 FROM grupos WHERE idgrupo = ?)");
                ps.setInt(1,(int) algo);
                rs = ps.executeQuery();

                if(rs.next()){
                    isPresent = rs.getBoolean(1);
                }
                ps.close();
            } catch (SQLException e){
                throw new PersistenciaException(e);
            }
        }else if (algo instanceof  String){
            try{
                ps = this.connection.prepareStatement("SELECT EXISTS (SELECT 1 FROM grupos WHERE nombre=?)");
                ps.setString(1, algo.toString());
                rs = ps.executeQuery();
                if (rs.next()){
                    isPresent = rs.getBoolean(1);
                }
                ps.close();
            }catch (SQLException e){
                throw new PersistenciaException(e);
            }
        }
        return  isPresent;
    }
}
