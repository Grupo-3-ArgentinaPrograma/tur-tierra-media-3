package services;

import java.util.List;

import model.productos.*;
import persistence.AtraccionDAO;
import persistence.commons.DAOFactory;

public class AttractionService {

	public List<Atraccion> list() {
		return DAOFactory.getAttractionDAO().findAll();
	}

	public Atraccion create(String name,String description, Integer cost, Double duration, Integer capacity, TipoAtraccion tipo) {

		Atraccion attraction = new Atraccion(name, description, cost, duration, capacity, tipo);

		if (attraction.isValid()) {
			AtraccionDAO attractionDAO = DAOFactory.getAttractionDAO();
			attractionDAO.insert(attraction);
			// XXX: si no devuelve "1", es que hubo más errores
		}

		return attraction;
	}

	public Atraccion update(Integer id, String name, String description, Integer cost, Double duration, Integer capacity, TipoAtraccion type) {

		AtraccionDAO attractionDAO = DAOFactory.getAttractionDAO();
		Atraccion attraction = attractionDAO.find(id);

		attraction.setNombre(name);
		attraction.setPrecio(cost);
		attraction.setTiempo(duration);
		attraction.setCupo(capacity);
		attraction.setDescripcion(description);
		attraction.setTipo(type);
		

		if (attraction.isValid()) {
			attractionDAO.update(attraction);
			// XXX: si no devuelve "1", es que hubo más errores
		}

		return attraction;
	}

	public void delete(Integer id) {
		AtraccionDAO attractionDAO = DAOFactory.getAttractionDAO();
		attractionDAO.delete(attractionDAO.find(id));
	}

	public Atraccion find(Integer id) {
		return DAOFactory.getAttractionDAO().find(id);
	}
	
	public Atraccion findByNombreAtraccion(String nombre) {
		return DAOFactory.getAttractionDAO().findByNombreAtraccion(nombre);
	}

}
