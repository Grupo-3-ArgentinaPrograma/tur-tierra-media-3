package persistence;

import model.productos.Promo;
import persistence.commons.GenericDAO;

public interface PromoDAO extends GenericDAO<Promo>{

	public abstract Promo findByNombre(String nombre);
}
