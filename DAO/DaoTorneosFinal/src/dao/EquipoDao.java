package dao;

import java.util.List;

public interface EquipoDao {

    List<dtos.Equipo> getAllEquipos();

    dtos.Equipo createEquipo(dtos.Equipo equipo);

    dtos.Equipo modifyEquipo(dtos.Equipo equipo);

    void deleteEquipo(dtos.Equipo equipo);

    void deleteEquipoById(int id);

    dtos.Equipo getEquipoById(int id);

    List<dtos.Equipo> getEquiposByGrupo(int idGrupo);

}