package dao;

import dtos.Torneo;

import java.time.LocalDate;
import java.util.List;

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


    /*
    //obtener por noCuenta
    Torneo getByNoCuenta(String noCuenta);
    //obtener los datos personales
    Torneo getPersonalData(int id);
    //obtener el id segun el numero de cuenta
    int getIdByNoCuenta(String noCuenta);
    LocalDate getFechaNacimiento(int id); */






}
