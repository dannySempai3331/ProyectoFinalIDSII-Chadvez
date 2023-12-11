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

    //obtener todos los torneos
    List<Torneo> getAllUsers();
    //crear un torneo
    boolean createTorneo(Torneo torneo);
    //modificar info de un torneo
    boolean modifyTorneo(Torneo torneo);
    //eliminar torneo
    boolean deleteTorneo(Torneo torneo);
    //obtener por id
    Torneo getById(int id);

}
