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

 @WebServlet("/users/edit.do")
 public class EditUserServlet extends HttpServlet {

	private static final long serialVersionUID = -2895379111342240526L;
	private UserService usuarioService;

	@Override
	public void init() throws ServletException {
		super.init();
		this.usuarioService = new UserService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer id = Integer.parseInt(req.getParameter("id"));

		User usuario = usuarioService.find(id);
		req.setAttribute("tmp_user", usuario);

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/views/users/edit.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		String password = req.getParameter("password");
		Integer monedas = Integer.parseInt(req.getParameter("coins"));
		Double tiempo = Double.parseDouble(req.getParameter("time"));
		TipoAtraccion type = TipoAtraccion.valueOf(req.getParameter("type_fav").toUpperCase());
		Boolean isAdmin = Boolean.valueOf(req.getParameter("isAdmin"));

		User usuario = usuarioService.update(id, name, password, monedas, tiempo, type, isAdmin);

		if (usuario.isValid()) {
			resp.sendRedirect("/turismo/users/index.do");
		} else {
			req.setAttribute("tmp_user", usuario);

			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/views/users/edit.jsp");
			dispatcher.forward(req, resp);
		}
	}
}