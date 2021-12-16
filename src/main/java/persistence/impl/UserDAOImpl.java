package persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.User;
import model.nullobjects.NullUser;
import model.productos.Atraccion;
import model.productos.Producto;
import model.productos.Promo;
import model.productos.TipoAtraccion;
import persistence.UserDAO;
import persistence.commons.ConnectionProvider;
import persistence.commons.MissingDataException;

public class UserDAOImpl implements UserDAO {

	public int insert(User user) {
		try {
			String sql = "INSERT INTO USERS (NOMBRE, PASSWORD, MONEDAS, TIEMPO, TIPO_PREFERENCIA, ADMIN, BORRADO) VALUES (?, ?, ?, ?, ?, ?, 0)";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, user.getNombre());
			statement.setString(2, user.getPassword());
			statement.setInt(3, user.getMonedas());
			statement.setDouble(4, user.getTiempoDisponible());
			statement.setString(5, user.getAtraccionPreferida().name());
			statement.setInt(6, user.getAdmin());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int update(User user) {
		try {
			String sql = "UPDATE USERS SET NOMBRE = ?, PASSWORD = ?, MONEDAS = ?, TIEMPO = ?, TIPO_PREFERENCIA = ?, ADMIN = ?, BORRADO = 0 WHERE ID = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, user.getNombre());
			statement.setString(2, user.getPassword());
			statement.setInt(3, user.getMonedas());
			statement.setDouble(4, user.getTiempoDisponible());
			statement.setString(5, user.getAtraccionPreferida().name());
			statement.setInt(6, user.getAdmin());
			statement.setInt(7, user.getId());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int delete(User user) {
		try {
			String sql = "UPDATE USERS SET BORRADO = 1 WHERE ID = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, user.getId());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public User findByUsername(String username) {
		try {
			String sql = "SELECT * FROM USERS WHERE NOMBRE = ?";
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
			String sql = "SELECT * FROM USERS WHERE BORRADO = 0";
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
		User usuario = new User(userRegister.getInt(1), userRegister.getString(2), userRegister.getString(3),
				TipoAtraccion.valueOf(userRegister.getString(6)), userRegister.getInt(4), userRegister.getDouble(5),
				userRegister.getBoolean(8));
		usuario.setId_atraccionesCompradas(getAtraccionesSolasCompradas(usuario));
		usuario.setId_promosCompradas(getPromosCompradas(usuario));
		return usuario;
	}

	public int saveItinerario(User t) {
		int rows = 0;
		try {
			deleteItinerario(t);
			Connection conn = ConnectionProvider.getConnection();
			String sql = "INSERT INTO ITINERARIOS (FK_USER, COSTO_TOTAL, TIEMPO_TOTAL) VALUES (?, ?, ?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, t.getId());
			statement.setInt(2, t.getGasto());
			statement.setDouble(3, t.getHsAConsumir());
			rows = statement.executeUpdate();
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		return rows;
	}

	public int deleteItinerario(User t) {
		try {
			String sql = "DELETE FROM ITINERARIOS WHERE FK_USER = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, t.getId());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	public int deleteCompras(User t) {
		try {
			String sql = "DELETE FROM COMPRAS_ITINERARIOS WHERE FK_ITINERARIO = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, t.getId());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public int saveCompras(User t) {
		int rows = 0;
		try {
			saveItinerario(t);
			deleteCompras(t);
			Connection conn = ConnectionProvider.getConnection();
			String sql = "INSERT INTO COMPRAS_ITINERARIOS (FK_ITINERARIO, FK_PROMO, FK_ATRACCION) VALUES (?, ?, ?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, t.getId());
			for (Producto P : t.getCompras()) {
				if (P instanceof Promo) {
					statement.setInt(2, ((Promo) P).getId());
					for (Integer idAtraccion : ((Promo) P).getId_atracciones()) {
						statement.setInt(3, idAtraccion);
						rows = statement.executeUpdate();
					}
				}
				if (P instanceof Atraccion) {
					statement.setNull(2, java.sql.Types.INTEGER);
					statement.setInt(3, ((Atraccion) P).getId());
					rows = statement.executeUpdate();
				}
			}
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		return rows;
	}

	@Override
	public List<Integer> getPromosCompradas(User u) {
		try {

			Connection conn = ConnectionProvider.getConnection();
			String sql = "SELECT FK_PROMO AS ID_PROMO FROM COMPRAS_ITINERARIOS WHERE FK_ITINERARIO=? AND FK_PROMO IS NOT NULL GROUP BY FK_PROMO";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, u.getId());
			ResultSet resultados = statement.executeQuery();

			List<Integer> id_PromosCompradas = new LinkedList<Integer>();
			while (resultados.next()) {
				id_PromosCompradas.add(resultados.getInt("ID_PROMO"));
			}
			return id_PromosCompradas;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public List<Integer> getAtraccionesSolasCompradas(User u) {
		try {

			Connection conn = ConnectionProvider.getConnection();
			String sql = "SELECT FK_ATRACCION AS ID_ATRACCION FROM COMPRAS_ITINERARIOS WHERE FK_ITINERARIO=? AND FK_PROMO IS NULL";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, u.getId());
			ResultSet resultados = statement.executeQuery();

			List<Integer> id_AtraccionesCompradas = new LinkedList<Integer>();
			while (resultados.next()) {
				id_AtraccionesCompradas.add(resultados.getInt("ID_ATRACCION"));
			}
			return id_AtraccionesCompradas;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

}
