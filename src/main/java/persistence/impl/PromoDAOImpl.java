package persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import persistence.AtraccionDAO;
import persistence.PromoDAO;
import persistence.commons.*;
import model.productos.*;

public class PromoDAOImpl implements PromoDAO {

	@Override
	public List<Promo> findAll() {
		try {
			Connection conn = ConnectionProvider.getConnection();
			String sql = "SELECT * FROM PROMOS WHERE BORRADO = 0";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<Promo> promos = new LinkedList<Promo>();
			while (resultados.next()) {
				Promo promoTest = toPromo(resultados);
				promos.add(promoTest);
			}
			return promos;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	private Promo toPromo(ResultSet resultados) {
		Promo promocion = null;
		try {
			Integer id = resultados.getInt(1);
			String nombre = resultados.getString(2);
			String descripcion = resultados.getString(3);
			TipoAtraccion tipo = TipoAtraccion.valueOf(resultados.getString(5));
			List<Integer> id_atracciones = this.getIdAtracciones(id);
			switch (resultados.getString(4)) {
			case "ABSOLUTA":
				promocion = new PromoAbsoluta(id, tipo, nombre, descripcion, id_atracciones, resultados.getInt(7));
				break;
			case "AXB":
				promocion = new PromoAxB(id, tipo, nombre, descripcion, id_atracciones, resultados.getInt(8));
				break;
			case "PORCENTUAL":
				promocion = new PromoPorcentual(id, tipo, nombre, descripcion, id_atracciones, resultados.getInt(6));
				break;
			}
			AtraccionDAO attractionDAO = DAOFactory.getAttractionDAO();
			promocion.establecerHsPromo(attractionDAO.findAll());
			promocion.establecerPrecioPromo(attractionDAO.findAll());
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		return promocion;
	}

	@Override
	public int insert(Promo t) {

		try {
			String sql = "INSERT INTO PROMOS (NOMBRE, DESCRIPCION, TIPO_PROMO, TIPO_ATRACCION, DESC_PORC, VALOR_ABSOL, FK_AT_GRATIS_AXB, BORRADO) VALUES (?, ?, ?, ?, ?, ?, ?, 0)";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, t.getNombre());
			statement.setString(2, t.getDescripcion());
			statement.setString(3, t.getTipoPromo());
			statement.setString(4, t.getTipo().name());

			switch (t.getTipoPromo()) {
			case "ABSOLUTA":
				statement.setNull(5, java.sql.Types.INTEGER);
				statement.setInt(6, t.getValor());
				statement.setNull(7, java.sql.Types.INTEGER);
				break;
			case "AXB":
				statement.setNull(5, java.sql.Types.INTEGER);
				statement.setNull(6, java.sql.Types.INTEGER);
				statement.setInt(7, ((PromoAxB) t).getAtraccionGratuita());
				break;
			case "PORCENTUAL":
				statement.setInt(5, t.getValor());
				statement.setNull(6, java.sql.Types.INTEGER);
				statement.setNull(7, java.sql.Types.INTEGER);
				break;
			}
			int rows = statement.executeUpdate();
			insert_AttractionsInPromo(t.getId_atracciones(),t);
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int insert_AttractionsInPromo(List<Integer> idAttractions, Promo p) {
		int rows = 0;
		try {
			String sql = "INSERT INTO ATTRACTIONS_IN_PROMOS (FK_ATRACCION, FK_PROMO) VALUES (?, ?)";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			for (Integer idAtraccion : idAttractions) {
				statement.setInt(1, idAtraccion);
				statement.setInt(2, p.getId());
				rows += statement.executeUpdate();
			}
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public int update(Promo t) {

		try {
			String sql = "UPDATE PROMOS SET NOMBRE = ?, DESCRIPCION = ?, TIPO_PROMO = ?, TIPO_ATRACCION = ?, DESC_PORC = ?, VALOR_ABSOL = ? FK_AT_GRATIS_AXB = ? BORRADO = 0 WHERE ID = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, t.getNombre());
			statement.setString(2, t.getDescripcion());
			statement.setString(3, t.getTipoPromo());
			statement.setString(4, t.getTipo().name());
			statement.setInt(8, t.getId());

			switch (t.getTipoPromo()) {
			case "ABSOLUTA":
				statement.setNull(5, java.sql.Types.INTEGER);
				statement.setInt(6, t.getValor());
				statement.setNull(7, java.sql.Types.INTEGER);
				break;
			case "AXB":
				statement.setNull(5, java.sql.Types.INTEGER);
				statement.setNull(6, java.sql.Types.INTEGER);
				statement.setInt(7, ((PromoAxB) t).getAtraccionGratuita());
				break;
			case "PORCENTUAL":
				statement.setInt(5, t.getValor());
				statement.setNull(6, java.sql.Types.INTEGER);
				statement.setNull(7, java.sql.Types.INTEGER);
				break;
			}

			int rows = statement.executeUpdate();
			return rows;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public int delete(Promo t) {
		try {
			String sql = "UPDATE FROM PROMOS SET BORRADO = 1 WHERE ID = ?";
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
	public Promo findByNombre(String nombre) {
		Promo promocion = null;
		try {
			String sql = "SELECT * FROM PROMOS WHERE NOMBRE = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, nombre);
			ResultSet resultados = statement.executeQuery();

			if (resultados.next()) {
				promocion = toPromo(resultados);
			}
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		return promocion;
	}

	public List<Integer> getIdAtracciones(Integer idPromo) {
		List<Integer> id_atracciones = null;
		try {
			String sql = "SELECT FK_ATRACCION FROM ATTRACTIONS_IN_PROMOS WHERE FK_PROMO = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, idPromo);
			ResultSet resultados = statement.executeQuery();

			id_atracciones = new LinkedList<Integer>();
			while (resultados.next()) {
				Integer id_atraccion = resultados.getInt(1);
				id_atracciones.add(id_atraccion);
			}
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		return id_atracciones;
	}

	@Override
	public Promo find(Integer id) {
		Promo promocion = null;
		try {
			String sql = "SELECT * FROM PROMOS WHERE ID = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultados = statement.executeQuery();

			if (resultados.next()) {
				promocion = toPromo(resultados);
			}
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		return promocion;
	}

	@Override
	public int countAll() {
		try {
			String sql = "SELECT COUNT(1) AS TOTAL FROM PROMOS";
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

}
