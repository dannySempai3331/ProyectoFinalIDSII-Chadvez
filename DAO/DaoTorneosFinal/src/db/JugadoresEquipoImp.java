package db;

import dao.JugadoresEquipoDao;
import errores.PersistenciaException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
Elaborado por:
Andy Gerald San Juan Martinez

Valeria Itzel Contreras Miranda

Jose Alejandro Terraza Gonzalez

Brayan Enrique Hernandez Flores

José Daniel Pérez Mejía
*/

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
        boolean wasRegistered = false;

        if (!isEquipoFull(idEquipo)) {

            try {
                ps = this.connection.prepareStatement("INSERT INTO jugadoresequipo (idjugador, idequipo) VALUES (?,?)");
                ps.setInt(1, idJugador);
                ps.setInt(2, idEquipo);
                ps.executeUpdate();
                ps.close();
                this.connection.close();
                wasRegistered = true;

                /*Cual es la diferencia entre el try catch y poner la anotación en los métodos para lanzar excepciones?*/

            } catch (SQLException e) {
                throw new PersistenciaException(e);
            }
        }
        return wasRegistered;
    }

    @Override
    public boolean darDeBajaJugador(int id) {
        PreparedStatement ps;
        boolean wasDeleted;

        try{
            ps = this.connection.prepareStatement("DELETE FROM jugadoresequipo WHERE idjugador = ?");
            ps.setInt(1,id);
            ps.executeUpdate();
            ps.close();
            //this.connection.close();

            wasDeleted = true;

        }catch (SQLException e){
            throw new PersistenciaException(e);
            // Porque se hacen nuestras propias excepciones?
        }
        return wasDeleted;
    }

    @Override
    public boolean darDeBajaEquipo(int id) {
        PreparedStatement ps;
        boolean wasDeleted;

        try {
            ps = this.connection.prepareStatement("DELETE FROM jugadoresequipo WHERE idequipo = ?");
            ps.setInt(1,id);
            ps.executeUpdate();
            ps.close();
            //this.connection.close();

            wasDeleted = true;
        }catch (SQLException e){
            throw new PersistenciaException(e);
        }
        return wasDeleted;
    }

    private boolean isEquipoFull(int idEquipo){

        PreparedStatement ps;
        ResultSet rs;
        boolean isFull;
        int r1 = 0,r2 = 0;

        try{
            ps = this.connection.prepareStatement("SELECT nojugadores FROM equipos WHERE idequipo = ?");
            ps.setInt(1,idEquipo);
            rs = ps.executeQuery();

            if (rs.next()){
                r1 = rs.getInt(1);
                System.out.println(r1);
            }

            ps = this.connection.prepareStatement("SELECT COUNT(*) FROM jugadoresequipo WHERE idequipo = ?");
            ps.setInt(1,idEquipo);
            rs = ps.executeQuery();

            if (rs.next()){
                r2 = rs.getInt(1);
                System.out.println(r2);
            }

            ps.close();

            isFull = (r2 >= r1);
        }catch (SQLException e){
            throw new PersistenciaException(e);
        }
        return isFull;
    }
}
