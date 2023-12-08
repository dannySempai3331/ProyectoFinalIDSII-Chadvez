package dao;

import dtos.Grupo;

import java.util.List;

public interface GrupoDao {
    //Lista de todos los grupos
    List<Grupo> getAllGroups();
    //Crear un nuevo grupo
    boolean createGrupo(Grupo grupo);
    //Modificar un grupo
    boolean modifyGrupo(Grupo grupo);
    //Eliminar un grupo
    boolean deleteGrupo(Grupo grupo);

}
