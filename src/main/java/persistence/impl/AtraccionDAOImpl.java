package persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.productos.Atraccion;
import model.productos.*;
import persistence.AtraccionDAO;
import persistence.commons.ConnectionProvider;
import persistence.commons.MissingDataException;

public class AtraccionDAOImpl implements AtraccionDAO {

	public List<Atraccion> findAll() {
		try {
			String sql = "SELECT * FROM ATTRACTIONS WHERE BORRADO = 0";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<Atraccion> attractions = new LinkedList<Atraccion>();
			while (resultados.next()) {
				attractions.add(toAttraction(resultados));
			}

			return attractions;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public Atraccion find(Integer id) {
		try {
			String sql = "SELECT * FROM ATTRACTIONS WHERE ID = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);

			ResultSet resultados = statement.executeQuery();

			Atraccion attraction = null;
			if (resultados.next()) {
				attraction = toAttraction(resultados);
			}

			return attraction;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	private Atraccion toAttraction(ResultSet attractionRegister) throws SQLException {

		return new Atraccion(attractionRegister.getInt(1), attractionRegister.getString(2),
				attractionRegister.getString(7), attractionRegister.getInt(3), attractionRegister.getDouble(4), attractionRegister.getInt(5),
				TipoAtraccion.valueOf(attractionRegister.getString(6)));
	}

	@Override
	public int insert(Atraccion attraction) {
		try {
			String sql = "INSERT INTO ATTRACTIONS (NOMBRE, COSTO, DURACION, CAPACIDAD, TIPO_ATRACCION, DESCRIPCION, BORRADO) VALUES (?, ?, ?, ?, ?, ?, 0)";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, attraction.getNombre());
			statement.setInt(2, attraction.getValor());
			statement.setDouble(3, attraction.getTiempo());
			statement.setInt(4, attraction.getCupo());
			statement.setString(5, attraction.getTipo().name());
			statement.setString(6, attraction.getDescripcion());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public int update(Atraccion attraction) {
		try {
			String sql = "UPDATE ATTRACTIONS SET NOMBRE = ?, COSTO = ?, DURACION = ?, CAPACIDAD = ?, TIPO_ATRACCION = ?, DESCRIPCION = ?, BORRADO = 0 WHERE ID = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, attraction.getNombre());
			statement.setInt(2, attraction.getValor());
			statement.setDouble(3, attraction.getTiempo());
			statement.setInt(4, attraction.getCupo());
			statement.setString(5,attraction.getTipo().name());
			statement.setString(6,attraction.getDescripcion());
			statement.setInt(7, attraction.getId());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public int delete(Atraccion attraction) {
		try {
			String sql = "UPDATE ATTRACTIONS SET BORRADO = 1 WHERE ID = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, attraction.getId());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public int countAll() {
		try {
			String sql = "SELECT COUNT(1) AS TOTAL FROM ATTRACTIONS";
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

	@Override
	public Atraccion findByNombreAtraccion(String nombre) {
		Atraccion atraccion = null;
		try {
			String sql = "SELECT * FROM ATTRACTIONS WHERE NOMBRE = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, nombre);
			ResultSet resultados = statement.executeQuery();
			
			if (resultados.next()) {
				atraccion = toAttraction(resultados);
			}
		}catch(Exception e){
			throw new MissingDataException(e);
		}
		return atraccion;
	}

}
