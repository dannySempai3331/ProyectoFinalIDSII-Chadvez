package dao;

public interface JugadoresEquipoDao {

    boolean inscribirJugador(int idJufador, int idEquipo);
    boolean darDeBajaJugador(int id);
    boolean darDeBajaEquipo(int id);
}
