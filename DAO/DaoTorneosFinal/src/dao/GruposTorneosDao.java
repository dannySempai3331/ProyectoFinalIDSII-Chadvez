package dao;

import dtos.GruposTorneos;

import java.util.List;

/*
Elaborado por:
Andy Gerald San Juan Martinez

Valeria Itzel Contreras Miranda

Jose Alejandro Terraza Gonzalez

Brayan Enrique Hernandez Flores

José Daniel Pérez Mejía
*/

public interface GruposTorneosDao {

    //Obtener todos los grupos y torneos
    List<GruposTorneos> getGrupoTorneo();
}
