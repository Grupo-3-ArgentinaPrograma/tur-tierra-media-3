package controller.promos;

import java.io.IOException;
import java.util.Map;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import persistence.commons.DAOFactory;
import services.BuyPromoService;

@WebServlet("/promos/buy.do")
public class BuyPromoServlet extends HttpServlet {

	private static final long serialVersionUID = 1565354113235195370L;
	private BuyPromoService buyPromoService;

	@Override
	public void init() throws ServletException {
		super.init();
		this.buyPromoService = new BuyPromoService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Integer promoId = Integer.parseInt(req.getParameter("id"));
		User user = (User) req.getSession().getAttribute("user");
		Map<String, String> errors = buyPromoService.buy(user.getId(), promoId);
		
		User user2 = DAOFactory.getUserDAO().find(user.getId());
		req.getSession().setAttribute("user", user2);
		
		if (errors.isEmpty()) {
			req.setAttribute("success", "¡Gracias por comprar!");
		} else {
			req.setAttribute("errors", errors);
			req.setAttribute("flash", "No ha podido realizarse la compra");
		}

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/promos/index.do");
		dispatcher.forward(req, resp);
	}
}
