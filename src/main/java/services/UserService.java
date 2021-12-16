package services;

import java.util.List;
import model.User;
import model.productos.TipoAtraccion;
import persistence.UserDAO;
import persistence.commons.DAOFactory;

public class UserService {

	public List<User> list() {
		return DAOFactory.getUserDAO().findAll();
	}

	public User create(String username, String password, TipoAtraccion tipoFav, Integer coins, Double time,
			Boolean isAdmin) {
		User user = new User(username, password, tipoFav, coins, time, isAdmin);
		user.setPassword(password);

		if (user.isValid()) {
			DAOFactory.getUserDAO().insert(user);
			// XXX: si no devuelve "1", es que hubo más errores
		}

		return user;
	}

	public User update(Integer id, String name, String password, Integer monedas, Double tiempo,
			TipoAtraccion tipoPreferencia, Boolean isAdmin) {

		UserDAO usuarioDAO = DAOFactory.getUserDAO();
		User usuario = usuarioDAO.find(id);

		usuario.setNombre(name);
		usuario.setPassword(password);
		usuario.setMonedas(monedas);
		usuario.setTiempoDisponible(tiempo);
		usuario.setAtraccionPreferida(tipoPreferencia);
		usuario.setAdmin(isAdmin);

		if (usuario.isValid()) {
			usuarioDAO.update(usuario);
			// XXX: si no devuelve "1", es que hubo más errores
		}

		return usuario;
	}

	public void delete(Integer id) {
		UserDAO usuarioDAO = DAOFactory.getUserDAO();
		usuarioDAO.delete(usuarioDAO.find(id));
	}

	public User find(Integer id) {
		return DAOFactory.getUserDAO().find(id);
	}

	public User findByNombre(String nombre) {
		return DAOFactory.getUserDAO().findByUsername(nombre);
	}
}
