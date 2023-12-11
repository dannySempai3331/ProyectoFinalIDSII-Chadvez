package dao;

/*
Elaborado por:
Andy Gerald San Juan Martinez

Valeria Itzel Contreras Miranda

Jose Alejandro Terraza Gonzalez

Brayan Enrique Hernandez Flores

José Daniel Pérez Mejía
*/

public interface JugadoresEquipoDao {

    boolean inscribirJugador(int idJufador, int idEquipo);
    boolean darDeBajaJugador(int id);
    boolean darDeBajaEquipo(int id);
}
