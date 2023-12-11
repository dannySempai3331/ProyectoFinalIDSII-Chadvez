package dao;

import dtos.Torneo;

import java.time.LocalDate;
import java.util.List;

/*
Elaborado por:
Andy Gerald San Juan Martinez

Valeria Itzel Contreras Miranda

Jose Alejandro Terraza Gonzalez

Brayan Enrique Hernandez Flores

José Daniel Pérez Mejía
*/

public interface TorneoDao {

    List<Torneo> getAllUsers();
    boolean createTorneo(Torneo torneo);
    boolean modifyTorneo(Torneo torneo);
    boolean deleteTorneo(Torneo torneo);
    Torneo getById(int id);

}
