package persistence;

import model.User;
import persistence.commons.GenericDAO;

public interface UserDAO extends GenericDAO<User> {

	public abstract User findByUsername(String username);
	public abstract int saveItinerario(User u);
	public abstract String getComprasRealizadas(User u);
}
