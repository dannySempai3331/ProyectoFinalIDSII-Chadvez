package db;

import dao.GruposTorneosDao;
import dtos.GruposTorneos;
import errores.PersistenciaException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GrupoTorneoDaoImp implements GruposTorneosDao {
    private Connection connection;

    public GrupoTorneoDaoImp(){}

    public void setConnection(Connection connection){
        this.connection = connection;

    }
    @Override
    public List<GruposTorneos> getGrupoTorneo() {
        Statement statement;
        ResultSet rs;
        GruposTorneos grupoTorneo;
        List<GruposTorneos> todos = new ArrayList<>();
        try{
            statement = this.connection.createStatement();
            rs = statement.executeQuery("SELECT *FROM grupos");
            while(rs.next()){
                grupoTorneo = new GruposTorneos();
                grupoTorneo.setIdGrupo(rs.getInt("idgrupo"));
                grupoTorneo.setIdTorneo(rs.getInt("idtorneo"));
                todos.add(grupoTorneo);
            }
            statement.close();
            connection.close();
            return todos;
        }catch(SQLException e){
            throw new PersistenciaException(e);
        }
    }
}
