package controller.users;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.UserService;

 @WebServlet("/users/delete.do")
 public class DeleteUserServlet extends HttpServlet {

	private static final long serialVersionUID = -5672024431368913082L;
	private UserService usuarioService;

	@Override
	public void init() throws ServletException {
		super.init();
		this.usuarioService = new UserService();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer id = Integer.parseInt(req.getParameter("id"));

		usuarioService.delete(id);

		resp.sendRedirect("/turismo/users/index.do");
	}


}
