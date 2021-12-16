package controller.users;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import services.UserService;

 @WebServlet("/itinerarios/index.do")
 public class ListItinerariosServlet extends HttpServlet implements Servlet {

	private static final long serialVersionUID = 3174658724671109979L;
	private UserService userService;

	@Override
	public void init() throws ServletException {
		super.init();
		this.userService = new UserService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<User> users = userService.list();
		req.setAttribute("users", users);

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/views/itinerarios/index.jsp");
		dispatcher.forward(req, resp);

	}

}