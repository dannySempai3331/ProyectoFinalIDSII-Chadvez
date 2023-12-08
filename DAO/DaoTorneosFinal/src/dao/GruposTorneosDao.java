package dao;

import dtos.GruposTorneos;

import java.util.List;

public interface GruposTorneosDao {

    //Obtener todos los grupos y torneos
    List<GruposTorneos> getGrupoTorneo();
}
