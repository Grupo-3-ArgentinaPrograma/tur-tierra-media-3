package persistence.commons;

import persistence.AtraccionDAO;
import persistence.PromoDAO;
import persistence.UserDAO;
import persistence.impl.AtraccionDAOImpl;
import persistence.impl.PromoDAOImpl;
import persistence.impl.UserDAOImpl;

public class DAOFactory {

	public static UserDAO getUserDAO() {
		return new UserDAOImpl();
	}
	
	public static AtraccionDAO getAttractionDAO() {
		return new AtraccionDAOImpl();
	}
	
	public static PromoDAO getPromoDAO() {
		return new PromoDAOImpl();
	}
}
