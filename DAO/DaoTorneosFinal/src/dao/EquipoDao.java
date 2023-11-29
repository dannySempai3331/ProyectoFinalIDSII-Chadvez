package dao;

import java.util.List;

public interface EquipoDao<Equipo> {

    List<Equipo> getAllEquipos();

    Equipo createEquipo(Equipo equipo);

    Equipo modifyEquipo(Equipo equipo);

    void deleteEquipo(Equipo equipo);

    void deleteEquipoById(int id);

    Equipo getEquipoById(int id);

    List<Equipo> getEquiposByGrupo(int idGrupo);

	dtos.Equipo createEquipo(dtos.Equipo equipo);

	dtos.Equipo modifyEquipo(dtos.Equipo equipo);

	void deleteEquipo(dtos.Equipo equipo);

    
}