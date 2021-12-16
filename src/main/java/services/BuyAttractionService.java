package services;

import java.util.HashMap;
import java.util.Map;
import model.productos.*;
import model.User;
import persistence.AtraccionDAO;
import persistence.UserDAO;
import persistence.commons.DAOFactory;

public class BuyAttractionService {

	AtraccionDAO attractionDAO = DAOFactory.getAttractionDAO();
	UserDAO userDAO = DAOFactory.getUserDAO();

	public Map<String, String> buy(Integer userId, Integer attractionId) {
		Map<String, String> errors = new HashMap<String, String>();

		User user = userDAO.find(userId);
		Atraccion attraction = attractionDAO.find(attractionId);

		if (!attraction.hayCupoPara(1)) {
			errors.put("attraction", "No hay cupo disponible");
		}
		if (!user.tieneMonedasPara(attraction)) {
			errors.put("user", "No tienes dinero suficiente");
		}
		if (!user.tieneTiempoPara(attraction)) {
			errors.put("user", "No tienes tiempo suficiente");
		}
		if(!user.puedeComprar(attraction)) {
			errors.put("user", "No se puede comprar");
		}

		if (errors.isEmpty()) {
			user.comprar(attraction);
			attractionDAO.update(attraction);
			userDAO.update(user);
			userDAO.saveCompras(user);
		}

		return errors;

	}

}
