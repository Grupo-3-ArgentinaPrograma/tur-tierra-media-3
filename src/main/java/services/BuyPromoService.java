package services;

import java.util.*;
import model.productos.*;
import model.User;
import persistence.*;
import persistence.commons.DAOFactory;

public class BuyPromoService {

	PromoDAO promoDAO = DAOFactory.getPromoDAO();
	UserDAO userDAO = DAOFactory.getUserDAO();
	AtraccionDAO atraccionDAO = DAOFactory.getAttractionDAO();

	public Map<String, String> buy(Integer userId, Integer promoId) {
		Map<String, String> errors = new HashMap<String, String>();

		User user = userDAO.find(userId);
		Promo promo = promoDAO.find(promoId);
		List<Atraccion> atracciones = atraccionDAO.findAll();

		if (!promo.hayCupo(atracciones)) {
			errors.put("promo", "No hay cupo disponible");
		}
		if (!user.tieneMonedasPara(promo)) {
			errors.put("user", "No tienes dinero suficiente");
		}
		if (!user.tieneTiempoPara(promo)) {
			errors.put("user", "No tienes tiempo suficiente");
		}
		if (!user.puedeComprar(promo)) {
			errors.put("user", "No puedes comprar esta promo");
		}

		if (errors.isEmpty()) {
			user.comprar(atracciones,promo);
			for(Integer idAtraccion:promo.getId_atracciones()) {
				atraccionDAO.update(atraccionDAO.find(idAtraccion));
			}
			promoDAO.update(promo);
			userDAO.update(user);
			userDAO.saveCompras(user);
		}

		return errors;

	}

}
