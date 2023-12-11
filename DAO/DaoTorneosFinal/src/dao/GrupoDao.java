package dao;

import dtos.Grupo;

import java.util.List;

/*
Elaborado por:
Andy Gerald San Juan Martinez

Valeria Itzel Contreras Miranda

Jose Alejandro Terraza Gonzalez

Brayan Enrique Hernandez Flores

José Daniel Pérez Mejía
*/

public interface GrupoDao {

    List<Grupo> getAllGroups();

    boolean createGrupo(Grupo grupo);

    boolean modifyGrupo(Grupo grupo);

    boolean deleteGrupo(Grupo grupo);

}
