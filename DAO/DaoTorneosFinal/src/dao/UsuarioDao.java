package dao;

import dtos.Usuario;

import java.util.List;

public interface UsuarioDao {

    //obtener todos los usuarios
    List<Usuario> getAllUsers();
    //crear un usuario
    Usuario createUsuario(Usuario usuario);
    //modificar info de un usuario
    Usuario modifyUsuario(Usuario usuario);
    //eliminar usuario
    void deleteUsuario(Usuario usuario);
    //obtener por id
    Usuario getById(int id);
    //obtener por noCuenta
    Usuario getByNoCuenta(String noCuenta);
    //obtener los datos personales
    Usuario getPersonalData(int id);
    //obtener el id segun el numero de cuenta
    int getIdByNoCuenta(String noCuenta);

}
