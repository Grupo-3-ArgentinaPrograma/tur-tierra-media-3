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

@WebServlet("/promos/create.do")
public class CreatePromoServlet extends HttpServlet {

	private static final long serialVersionUID = -3714897157387736951L;
	private PromoService promotionService;

	@Override
	public void init() throws ServletException {
		super.init();
		this.promotionService = new PromoService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/promos/create.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		String typePromotion = req.getParameter("promo_type");
		TipoAtraccion typeAttraction = TipoAtraccion.valueOf(req.getParameter("attraction_type"));
		Integer discount = Integer.parseInt(req.getParameter("discount"));
		List<Integer> idAttractions = new LinkedList<Integer>();
		
		for (String id_attraction : req.getParameter("id_attractions").split("-")) {
			idAttractions.add(Integer.parseInt(id_attraction));
		}
		
		Promo promotion = promotionService.findByNombrePromo(name);
		if (promotion != null) {
			Integer id = promotion.getId();
			promotion = promotionService.update(id, name, description, discount, typeAttraction, typePromotion,
					idAttractions);
		} else {
			promotion = promotionService.create(name, description, discount, typeAttraction, typePromotion,
					idAttractions);
		}
		AtraccionDAO attraction = DAOFactory.getAttractionDAO();

		if (promotion.isValid(attraction.findAll())) {
			resp.sendRedirect("/turismo/promos/index.do");
		} else {
			req.setAttribute("promo", promotion);

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/attractions/create.jsp");
			dispatcher.forward(req, resp);
		}

	}

}
