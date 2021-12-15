package controller.users;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import model.productos.TipoAtraccion;
import services.UserService;

@WebServlet("/users/create.do")
public class CreateUserServlet extends HttpServlet {

	private static final long serialVersionUID = 6770857886710948270L;
	private UserService userService;

	@Override
	public void init() throws ServletException {
		super.init();
		this.userService = new UserService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/views/users/create.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		Integer coins = Integer.parseInt(req.getParameter("coins"));
		Double time = Double.parseDouble(req.getParameter("time"));
		TipoAtraccion type= TipoAtraccion.valueOf(req.getParameter("typeAttraction"));
		Boolean isAdmin = Boolean.valueOf(req.getParameter("isAdmin"));

		User tmp_user =  userService.findByNombre(username);
		if(tmp_user!=null) {
			Integer id = tmp_user.getId();
			tmp_user=userService.update(id, username, password, coins, time, type, isAdmin);
		}else {
			tmp_user = userService.create(username, password, type, coins, time, isAdmin);
		}
		
		if (tmp_user.isValid()) {
			resp.sendRedirect("turismo/users/index.do");
		} else {
			req.setAttribute("tmp_user", tmp_user);

			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/views/users/create.jsp");
			dispatcher.forward(req, resp);
		}

	}

}