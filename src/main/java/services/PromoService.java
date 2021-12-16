package services;

import java.util.List;
import model.productos.Promo;
import model.productos.PromoAbsoluta;
import model.productos.PromoAxB;
import model.productos.PromoPorcentual;
import model.productos.TipoAtraccion;
import persistence.AtraccionDAO;
import persistence.PromoDAO;
import persistence.commons.DAOFactory;

public class PromoService {

	public List<Promo> list() {
		return DAOFactory.getPromoDAO().findAll();
	}

	public Promo create(String name, String description, Integer discount, TipoAtraccion tipo, String promotionType,
			List<Integer> id_Atracciones) {

		Promo promotion = null;
		switch (promotionType.toUpperCase()) {
		case "ABSOLUTA":
			promotion = new PromoAbsoluta(tipo, name, description, id_Atracciones, discount);
			break;
		case "PORCENTUAL":
			promotion = new PromoPorcentual(tipo, name, description, id_Atracciones, discount);
			break;
		case "AXB":
			promotion = new PromoAxB(tipo, name, description, id_Atracciones, discount);
			break;
		}

		AtraccionDAO attractionDAO = DAOFactory.getAttractionDAO();

		if (promotion.isValid(attractionDAO.findAll())) {
			PromoDAO promotionDAO = DAOFactory.getPromoDAO();
			promotionDAO.insert(promotion);
			// XXX: si no devuelve "1", es que hubo más errores
		}

		return promotion;
	}

	public Promo update(Integer id,String name, String description, Integer discount, TipoAtraccion tipo, String promotionType,
			List<Integer> id_Atracciones) {

		PromoDAO promotionDAO = DAOFactory.getPromoDAO();
		Promo promotion = null;
		switch(promotionType.toUpperCase()) {
		case "ABSOLUTA":
			promotion = new PromoAbsoluta(id,tipo, name, description, id_Atracciones, discount);
			break;
		case "PORCENTUAL":
			promotion = new PromoPorcentual(id,tipo, name, description, id_Atracciones, discount);
			break;
		case "AXB":
			promotion = new PromoAxB(id,tipo, name, description, id_Atracciones, discount);
			break;
		}

		AtraccionDAO attractionDAO = DAOFactory.getAttractionDAO();

		if (promotion.isValid(attractionDAO.findAll())) {
			promotionDAO.update(promotion);
			// XXX: si no devuelve "1", es que hubo más errores
		}

		return promotion;
	}

	public void delete(Integer id) {
		PromoDAO promotionDAO = DAOFactory.getPromoDAO();
		promotionDAO.delete(promotionDAO.find(id));
	}

	public Promo find(Integer id) {
		return DAOFactory.getPromoDAO().find(id);
	}

	public Promo findByNombrePromo(String nombre) {
		return DAOFactory.getPromoDAO().findByNombre(nombre);
	}

}