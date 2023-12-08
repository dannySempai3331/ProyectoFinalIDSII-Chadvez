package db;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import dao.EquipoDao;
import dtos.Equipo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDaoImp implements EquipoDao {

    private Connection connection;

    // Constructor y otros métodos de inicialización de la conexión...

    @Override
    public List<Equipo> getAllEquipos() {
        List<Equipo> todos = new ArrayList<>();
        try {
            String query = "SELECT * FROM equipos";
            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(query)) {
                while (rs.next()) {
                    Equipo equipo = new Equipo();
                    equipo.setIdEquipo(rs.getInt("idEquipo"));
                    equipo.setNombre(rs.getString("nombre"));
                    equipo.setNoJugadores(rs.getInt("noJugadores"));
                    equipo.setPuntaje(rs.getInt("puntaje"));
                    equipo.setIdGrupo(rs.getInt("idGrupo"));

                    todos.add(equipo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción adecuadamente en un entorno real
        }
        return todos;
    }

    @Override
    public Equipo createEquipo(Equipo equipo) {
        try {
            String query = "INSERT INTO equipos (nombre, noJugadores, puntaje, idGrupo) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
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
            e.printStackTrace(); // Manejar la excepción adecuadamente en un entorno real
        }
        return equipo;
    }

    @Override
    public Equipo modifyEquipo(Equipo equipo) {
        try {
            String query = "UPDATE equipos SET nombre=?, noJugadores=?, puntaje=?, idGrupo=? WHERE idEquipo=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, equipo.getNombre());
                preparedStatement.setInt(2, equipo.getNoJugadores());
                preparedStatement.setInt(3, equipo.getPuntaje());
                preparedStatement.setInt(4, equipo.getIdGrupo());
                preparedStatement.setInt(5, equipo.getIdEquipo());

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Modifying equipo failed, no rows affected.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción adecuadamente en un entorno real
        }
        return equipo;
    }

    @Override
    public void deleteEquipo(Equipo equipo) {
        deleteEquipoById(equipo.getIdEquipo());
    }

    @Override
    public void deleteEquipoById(int id) {
        try {
            String query = "DELETE FROM equipos WHERE idEquipo=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Deleting equipo failed, no rows affected.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción adecuadamente en un entorno real
        }
    }

    @Override
    public Equipo getEquipoById(int id) {
        Equipo equipo = null;
        try {
            String query = "SELECT * FROM equipos WHERE idEquipo=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        equipo = new Equipo();
                        equipo.setIdEquipo(rs.getInt("idEquipo"));
                        equipo.setNombre(rs.getString("nombre"));
                        equipo.setNoJugadores(rs.getInt("noJugadores"));
                        equipo.setPuntaje(rs.getInt("puntaje"));
                        equipo.setIdGrupo(rs.getInt("idGrupo"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción adecuadamente en un entorno real
        }
        return equipo;
    }

    @Override
    public List<Equipo> getEquiposByGrupo(int idGrupo) {
        List<Equipo> equipos = new ArrayList<>();
        try {
            String query = "SELECT * FROM equipos WHERE idGrupo=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idGrupo);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        Equipo equipo = new Equipo();
                        equipo.setIdEquipo(rs.getInt("idEquipo"));
                        equipo.setNombre(rs.getString("nombre"));
                        equipo.setNoJugadores(rs.getInt("noJugadores"));
                        equipo.setPuntaje(rs.getInt("puntaje"));
                        equipo.setIdGrupo(rs.getInt("idGrupo"));

                        equipos.add(equipo);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción adecuadamente en un entorno real
        }
        return equipos;
    }

	@Override
	public Object createEquipo(Object equipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object modifyEquipo(Object equipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteEquipo(Object equipo) {
		// TODO Auto-generated method stub
		
	}

    // Otros métodos según tus necesidades
}