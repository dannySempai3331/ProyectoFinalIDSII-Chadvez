package dao;

import java.util.List;

/*
Elaborado por:
Andy Gerald San Juan Martinez

Valeria Itzel Contreras Miranda

Jose Alejandro Terraza Gonzalez

Brayan Enrique Hernandez Flores

José Daniel Pérez Mejía
*/

public interface EquipoDao {

    List<dtos.Equipo> getAllEquipos();

    dtos.Equipo createEquipo(dtos.Equipo equipo);

    dtos.Equipo modifyEquipo(dtos.Equipo equipo);

    void deleteEquipo(dtos.Equipo equipo);

    void deleteEquipoById(int id);

    dtos.Equipo getEquipoById(int id);

    List<dtos.Equipo> getEquiposByGrupo(int idGrupo);

}