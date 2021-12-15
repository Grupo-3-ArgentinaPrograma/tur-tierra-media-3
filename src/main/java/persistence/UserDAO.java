package persistence;

import java.util.List;

import model.User;
import persistence.commons.GenericDAO;

public interface UserDAO extends GenericDAO<User> {

	public abstract User findByUsername(String username);
	public abstract int saveCompras(User t);
	public List<Integer> getPromosCompradas(User u);
	public List<Integer> getAtraccionesSolasCompradas(User u);
}
