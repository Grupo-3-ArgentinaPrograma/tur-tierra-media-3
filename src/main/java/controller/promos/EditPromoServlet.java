package controller.promos;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.productos.Promo;
import model.productos.TipoAtraccion;
import persistence.AtraccionDAO;
import persistence.commons.DAOFactory;
import services.PromoService;

 @WebServlet("/promos/edit.do")
 public class EditPromoServlet extends HttpServlet {

	private static final long serialVersionUID = 6896515384155041389L;
	private PromoService promotionService;

	@Override
	public void init() throws ServletException {
		super.init();
		this.promotionService = new PromoService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer id = Integer.parseInt(req.getParameter("id"));

		Promo promotion = promotionService.find(id);
		req.setAttribute("promotion", promotion);

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/views/promos/edit.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		String typePromotion = req.getParameter("promo_type").toUpperCase();
		TipoAtraccion typeAttraction = TipoAtraccion.valueOf(req.getParameter("attraction_type").toUpperCase());
		Integer discount = Integer.parseInt(req.getParameter("discount"));
		List<Integer> idAttractions = new LinkedList<Integer>();
		
		for (String id_attraction : req.getParameter("id_attractions").split("-")) {
			idAttractions.add(Integer.parseInt(id_attraction));
		}
		
		Promo promotion = promotionService.update(id, name, description, discount, typeAttraction, typePromotion, idAttractions);

		AtraccionDAO attraction = DAOFactory.getAttractionDAO();

		if (promotion.isValid(attraction.findAll())) {
			resp.sendRedirect("/turismo/promos/index.do");
		} else {
			req.setAttribute("promotion", promotion);

			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/views/promos/edit.jsp");
			dispatcher.forward(req, resp);
		}
	}
}