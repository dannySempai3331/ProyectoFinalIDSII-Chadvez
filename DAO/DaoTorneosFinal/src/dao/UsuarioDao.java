package dao;

import dtos.Usuario;

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

public interface UsuarioDao {

    //obtener todos los usuarios
    List<Usuario> getAllUsers();
    //crear un usuario
    boolean createUsuario(Usuario usuario);
    //modificar info de un usuario
    boolean modifyUsuario(Usuario usuario);
    //eliminar usuario
    boolean deleteUsuario(Usuario usuario);
    //obtener por id
    Usuario getById(int id);
    //obtener por noCuenta
    Usuario getByNoCuenta(String noCuenta);
    //obtener los datos personales
    Usuario getPersonalData(int id);
    //obtener el id segun el numero de cuenta
    int getIdByNoCuenta(String noCuenta);
    LocalDate getFechaNacimiento(int id);

    Usuario getDataForLogIn(Usuario u);

}
