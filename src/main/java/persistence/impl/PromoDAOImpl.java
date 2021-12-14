package persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import persistence.PromoDAO;
import persistence.commons.*;
import model.productos.*;

public class PromoDAOImpl implements PromoDAO {

	@Override
	public List<Promo> findAll() {
		try {
			Connection conn = ConnectionProvider.getConnection();
			String sql = "SELECT * FROM PROMOCION";
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
			switch (resultados.getString(2)) {
			case "Absoluta":
				promocion = new PromoAbsoluta(TipoAtraccion.valueOf(resultados.getString(3)), resultados.getString(1),
						resultados.getString(4), resultados.getInt(7));
				break;
			case "AxB":
				promocion = new PromoAxB(TipoAtraccion.valueOf(resultados.getString(3)), resultados.getString(1),
						resultados.getString(4), resultados.getString(6));
				break;
			case "Porcentual":
				promocion = new PromoPorcentual(TipoAtraccion.valueOf(resultados.getString(3)), resultados.getString(1),
						resultados.getString(4), resultados.getInt(5));
				break;
			}
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		return promocion;
	}

	@Override
	public int insert(Promo t) {

		try {
			String sql = "INSERT INTO PROMOCION (NOMBRE_PACK, TIPO_PROMO, TIPO_ATRACCION, ATRACCIONES, PORC_DESC, AXB_GRATIS, ABS_COSTO) VALUES (?, ?, ?, ?, ?, ?, ?)";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, t.getNombre());
			statement.setString(2, t.getTipoPromo());
			statement.setString(3, t.getTipo().name());
			statement.setString(4, t.nombresAtracciones());

			switch (t.getTipoPromo()) {
			case "Absoluta":
				statement.setNull(5, java.sql.Types.INTEGER);
				statement.setString(6, null);
				statement.setInt(7, t.getValor());
				break;
			case "AxB":
				statement.setNull(5, java.sql.Types.INTEGER);
				statement.setString(6, ((PromoAxB) t).getAtraccionGratuita());
				statement.setInt(7, java.sql.Types.INTEGER);
				break;
			case "Porcentual":
				statement.setNull(5, t.getValor());
				statement.setString(6, null);
				statement.setInt(7, java.sql.Types.INTEGER);
				break;
			}
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public int update(Promo t) {

		try {
			String sql = "UPDATE PROMOCION SET TIPO_PROMO = ?, TIPO_ATRACCION = ?, ATRACCIONES = ?, PORC_DESC = ?, AXB_GRATIS = ?, ABS_COSTO = ? WHERE NOMBRE_PACK = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(7, t.getNombre());
			statement.setString(1, t.getTipoPromo());
			statement.setString(2, t.getTipo().name());
			statement.setString(3, t.nombresAtracciones());
			
			switch (t.getTipoPromo()) {
			case "Absoluta":
				statement.setNull(4, java.sql.Types.INTEGER);
				statement.setString(5, null);
				statement.setInt(6, t.getValor());
				break;
			case "AxB":
				statement.setNull(4, java.sql.Types.INTEGER);
				statement.setString(5, ((PromoAxB) t).getAtraccionGratuita());
				statement.setNull(6, java.sql.Types.INTEGER);
				break;
			case "Porcentual":
				statement.setInt(4, t.getValor());
				statement.setString(5, null);
				statement.setNull(6, java.sql.Types.INTEGER);
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
			String sql = "DELETE FROM PROMOCION WHERE NOMBRE_PACK = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, t.getNombre());
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
			String sql = "SELECT * FROM PROMOCION WHERE NOMBRE_PACK = ?";
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

	@Override
	public Promo find(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countAll() {
		// TODO Auto-generated method stub
		return 0;
	}

}
