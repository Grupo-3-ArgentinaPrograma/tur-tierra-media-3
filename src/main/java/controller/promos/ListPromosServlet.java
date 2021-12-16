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
import model.productos.Atraccion;
import model.productos.Promo;
import services.AttractionService;
import services.PromoService;

 @WebServlet("/promos/index.do")
 public class ListPromosServlet extends HttpServlet implements Servlet {


	private static final long serialVersionUID = 6641599288196945647L;
	private PromoService promotionService;
	private AttractionService attractionService;

	@Override
	public void init() throws ServletException {
		super.init();
		this.promotionService = new PromoService();
		this.attractionService = new AttractionService();	
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Promo> promotions = promotionService.list();
		List<Atraccion> attractions = attractionService.list();
		req.setAttribute("attractions", attractions);
		req.setAttribute("promotions", promotions);

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/views/promos/index.jsp");
		dispatcher.forward(req, resp);

	}

}
