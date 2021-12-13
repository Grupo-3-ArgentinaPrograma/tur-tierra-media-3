package persistence;

import model.productos.Atraccion;
import persistence.commons.GenericDAO;

public interface AtraccionDAO extends GenericDAO<Atraccion> {
	public abstract Atraccion findByNombreAtraccion(String nombre);
}
