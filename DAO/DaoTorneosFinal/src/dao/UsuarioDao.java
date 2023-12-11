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


    List<Usuario> getAllUsers();
    boolean createUsuario(Usuario usuario);
    boolean modifyUsuario(Usuario usuario);
    boolean deleteUsuario(Usuario usuario);
    Usuario getById(int id);
    Usuario getByNoCuenta(String noCuenta);
    Usuario getPersonalData(int id);
    int getIdByNoCuenta(String noCuenta);
    LocalDate getFechaNacimiento(int id);
    Usuario getDataForLogIn(Usuario u);

}
