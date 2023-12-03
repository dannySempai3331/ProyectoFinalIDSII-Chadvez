package db;

import dao.JugadoresEquipoDao;
import errores.PersistenciaException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JugadoresEquipoImp implements JugadoresEquipoDao {

    private Connection connection;

    public JugadoresEquipoImp() {
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean inscribirJugador(int idJugador, int idEquipo) {

        PreparedStatement ps;
        boolean wasRegistered;

        try{
            ps = this.connection.prepareStatement("INSERT INTO jugadoresequipo (idjugador, idequipo) VALUES (?,?)");
            ps.setInt(1, idJugador);
            ps.setInt(2,idEquipo);
            ps.executeUpdate();
            ps.close();
            this.connection.close();
            wasRegistered = true;

            /*Cual es la diferencia entre el try catch y poner la anotación en los métodos para lanzar excepciones?*/

        }catch (SQLException e){
            throw new PersistenciaException(e);
        }
        return wasRegistered;
    }

    @Override
    public boolean darDeBajaJugador(int id) {
        return false;
    }

    @Override
    public boolean darDeBajaEquipo(int id) {
        return false;
    }
}
