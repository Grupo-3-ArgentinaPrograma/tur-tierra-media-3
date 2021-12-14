package persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.User;
import model.nullobjects.NullUser;
import persistence.UserDAO;
import persistence.commons.ConnectionProvider;
import persistence.commons.MissingDataException;

public class UserDAOImpl implements UserDAO {

	public int insert(User user) {
		try {
			String sql = "INSERT INTO USERS (USERNAME, PASSWORD, COINS, TIME) VALUES (?, ?, ?, ?)";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, user.getNombre());
			statement.setString(2, user.getPassword());
			statement.setInt(3, user.getMonedas());
			statement.setDouble(4, user.getTiempoDisponible());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int update(User user) {
		try {
			String sql = "UPDATE USERS SET COINS = ?, TIME = ? WHERE ID = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, user.getMonedas());
			statement.setDouble(2, user.getTiempoDisponible());
			statement.setDouble(3, user.getId());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int delete(User user) {
		try {
			String sql = "DELETE FROM USERS WHERE USERNAME = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, user.getNombre());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public User findByUsername(String username) {
		try {
			String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet resultados = statement.executeQuery();

			User user = NullUser.build();

			if (resultados.next()) {
				user = toUser(resultados);
			}

			return user;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MissingDataException(e);
		}
	}

	public User find(Integer id) {
		try {
			String sql = "SELECT * FROM USERS WHERE ID = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultados = statement.executeQuery();

			User user = NullUser.build();

			if (resultados.next()) {
				user = toUser(resultados);
			}

			return user;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int countAll() {
		try {
			String sql = "SELECT COUNT(1) AS TOTAL FROM USERS";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			resultados.next();
			int total = resultados.getInt("TOTAL");

			return total;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public List<User> findAll() {
		try {
			String sql = "SELECT * FROM USERS";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<User> usuarios = new LinkedList<User>();
			while (resultados.next()) {
				usuarios.add(toUser(resultados));
			}

			return usuarios;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	private User toUser(ResultSet userRegister) throws SQLException {
		return new User(userRegister.getInt(1), userRegister.getString(2), userRegister.getString(3),
				null, userRegister.getInt(5), userRegister.getDouble(6), userRegister.getBoolean(4));
	}

	@Override
	public int saveItinerario(User t) {
		int rows=0;
		try {
			Connection conn = ConnectionProvider.getConnection();
			String sql = "INSERT INTO ITINERARIO (U_NOMBRE, COMPRAS, GASTO_TOTAL, TIEMPO_TOTAL) VALUES (?, ?, ?, ?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, t.getNombre());
			statement.setString(2, t.getStringCompras());
			statement.setInt(3, t.getGasto());
			statement.setDouble(4, t.getHsAConsumir());
			rows = statement.executeUpdate();
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		return rows;
	}

	@Override
	public String getComprasRealizadas(User u){
		try {
			Connection conn = ConnectionProvider.getConnection();
			String sql = "SELECT COMPRAS FROM ITINERARIO WHERE U_NOMBRE=?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, u.getNombre());
			ResultSet resultados = statement.executeQuery();

			return resultados.toString();
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

}
