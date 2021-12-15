package controller.promos;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.PromoService;

	@WebServlet("/promos/delete.do")
	public class DeletePromoServlet extends HttpServlet {

		private static final long serialVersionUID = 3569766427638632276L;
		private PromoService promotionService;

		@Override
		public void init() throws ServletException {
			super.init();
			this.promotionService = new PromoService();
		}
		
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			Integer id = Integer.parseInt(req.getParameter("id"));

			promotionService.delete(id);

			resp.sendRedirect("/turismo/promos/index.do");
		}


	}
