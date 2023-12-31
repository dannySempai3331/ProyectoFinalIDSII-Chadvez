package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.EquipoDao;
import dtos.Equipo;
import errores.PersistenciaException;

/*
Elaborado por:

Andy Gerald San Juan Martinez

Valeria Itzel Contreras Miranda

Jose Alejandro Terraza Gonzalez

Brayan Enrique Hernandez Flores

José Daniel Pérez Mejía
*/

public class EquipoDaoImp implements EquipoDao {

    private Connection connection;

    private JugadoresEquipoImp jei = new JugadoresEquipoImp();

    public EquipoDaoImp() {
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
        jei.setConnection(connection);
    }

    @Override
    public List<dtos.Equipo> getAllEquipos() {
        List<dtos.Equipo> todos = new ArrayList<>();
        try {
            String query = "SELECT * FROM equipos";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    dtos.Equipo equipo = mapResultSetToEquipo(rs);
                    todos.add(equipo);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException(e);
        }
        return todos;
    }

    @Override
    public dtos.Equipo createEquipo(dtos.Equipo equipo) {
        try {
            String query = "INSERT INTO equipos (nombre, noJugadores, puntaje, idGrupo) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, equipo.getNombre());
                preparedStatement.setInt(2, equipo.getNoJugadores());
                preparedStatement.setInt(3, equipo.getPuntaje());
                preparedStatement.setInt(4, equipo.getIdGrupo());

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating equipo failed, no rows affected.");
                }

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        equipo.setIdEquipo(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating equipo failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException(e);
        }
        return equipo;
    }

    @Override
    public Equipo modifyEquipo(dtos.Equipo equipo) {
        try {
            StringBuilder queryBuilder = new StringBuilder("UPDATE equipos SET");
            List<Object> params = new ArrayList<>();

            if (equipo.getNombre() != null && !equipo.getNombre().isEmpty()) {
                queryBuilder.append(" nombre=?,");
                params.add(equipo.getNombre());
            }
            if (equipo.getNoJugadores() > 0) {
                queryBuilder.append(" noJugadores=?,");
                params.add(equipo.getNoJugadores());
            }
            if (equipo.getPuntaje() >= 0) {
                queryBuilder.append(" puntaje=?,");
                params.add(equipo.getPuntaje());
            }
            if (equipo.getIdGrupo() > 0) {
                queryBuilder.append(" idGrupo=?,");
                params.add(equipo.getIdGrupo());
            }

            if (params.size() > 0) {
                queryBuilder.deleteCharAt(queryBuilder.length() - 1);
            }

            queryBuilder.append(" WHERE idEquipo=?");
            params.add(equipo.getIdEquipo());

            String query = queryBuilder.toString();

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new PersistenciaException(e);
        }
		return equipo;
    }

    @Override
    public void deleteEquipo(Equipo equipo) {
        jei.darDeBajaEquipo(equipo.getIdEquipo());
        deleteEquipoById(equipo.getIdEquipo());
    }

    @Override
    public void deleteEquipoById(int id) {
        try {
            String query = "DELETE FROM equipos WHERE idEquipo=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new PersistenciaException(e);
        }
    }

    @Override
    public dtos.Equipo getEquipoById(int id) {
        dtos.Equipo equipo = null;
        try {
            String query = "SELECT * FROM equipos WHERE idEquipo=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        equipo = mapResultSetToEquipo(rs);
                    }
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException(e);
        }
        return equipo;
    }

    @Override
    public List<dtos.Equipo> getEquiposByGrupo(int idGrupo) {
        List<dtos.Equipo> equipos = new ArrayList<>();
        try {
            String query = "SELECT * FROM equipos WHERE idGrupo=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idGrupo);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        dtos.Equipo equipo = mapResultSetToEquipo(rs);
                        equipos.add(equipo);
                    }
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException(e);
        }
        return equipos;
    }

    private dtos.Equipo mapResultSetToEquipo(ResultSet rs) throws SQLException {
        dtos.Equipo equipo = new dtos.Equipo();
        equipo.setIdEquipo(rs.getInt("idEquipo"));
        equipo.setNombre(rs.getString("nombre"));
        equipo.setNoJugadores(rs.getInt("noJugadores"));
        equipo.setPuntaje(rs.getInt("puntaje"));
        equipo.setIdGrupo(rs.getInt("idGrupo"));
        return equipo;
    }

}