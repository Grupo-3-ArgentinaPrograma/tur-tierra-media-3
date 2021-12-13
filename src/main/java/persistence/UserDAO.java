package persistence;

import java.sql.SQLException;
import model.User;
import persistence.commons.GenericDAO;

public interface UserDAO extends GenericDAO<User> {

	public abstract User findByUsername(String username);
	public abstract int saveItinerario(User u) throws SQLException;
	public abstract String getComprasRealizadas(User u);
}
