package controller.promos;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.productos.Promo;
import services.PromoService;

 @WebServlet("/promos/index.do")
 public class ListPromosServlet extends HttpServlet implements Servlet {


	private static final long serialVersionUID = 6641599288196945647L;
	private PromoService promotionService;

	@Override
	public void init() throws ServletException {
		super.init();
		this.promotionService = new PromoService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Promo> promotions = promotionService.list();
		req.setAttribute("promotions", promotions);

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/views/promotions/index.jsp");
		dispatcher.forward(req, resp);

	}

}
