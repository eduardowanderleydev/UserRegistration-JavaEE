package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanProduct;
import dao.ProductDAO;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProductDAO productDAO = new ProductDAO();

	public ProductServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String acao = request.getParameter("acao") != null ? request.getParameter("acao") : "list";
		String productId = request.getParameter("product");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroDeProduto.jsp");
		
		if (acao.equals("list")) {
			
			request.setAttribute("list", productDAO.findAll());
			request.setAttribute("categories", productDAO.findAllCategories());
		
		} else if (acao.equals("delete")) {
		
			productDAO.delete(productId);
			request.setAttribute("list", productDAO.findAll());
		
		} else if (acao.equals("edit")) {
		
			BeanProduct product = productDAO.findById(productId);
			request.setAttribute("product", product);
			request.setAttribute("list", productDAO.findAll());
			
		}
		
		request.setAttribute("categories", productDAO.findAllCategories());
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && acao.equals("reset")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroDeProduto.jsp");
			request.setAttribute("list", productDAO.findAll());
			request.setAttribute("categories", productDAO.findAllCategories());
			dispatcher.forward(request, response);
		} else {

			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String quantity = request.getParameter("quantity");
			String price = request.getParameter("price");
			String category = request.getParameter("category_id");
			
			boolean podeInserir = true;
			String msg = "";

			if (name == null || name.isEmpty()) {
				msg = "Name cannot be empty";
				podeInserir = false;
			} else if (quantity == null || quantity.isEmpty()) {
				msg = "Quantity cannot be empty";
				podeInserir = false;
			} else if (price == null || price.isEmpty()) {
				msg = "Price cannot be empty";
				podeInserir = false;
			}

			if (id == null || id.isEmpty() && !productDAO.validateProductName(name)) {
				podeInserir = false;
				msg = "Product name already exists";
			}

			BeanProduct product = new BeanProduct();
			product.setName(name);
			product.setId(!id.isEmpty() ? Long.parseLong(id) : 0);
			product.setCategory_id(Long.parseLong(category));
			
			if (quantity != null && !quantity .isEmpty()) {
				product.setQuantity(Integer.parseInt(quantity));
			}
			
			if (price != null && !price.isEmpty()) {
				String valueParse = price.replaceAll("\\.", "" );
				valueParse = valueParse.replaceAll("\\,", "\\.");
				product.setPrice(Double.parseDouble(valueParse));
			}
			

			if (id == null || id.isEmpty() && podeInserir && productDAO.validateProductName(name)) {
				productDAO.insert(product);
			} else if (id != null || !id.isEmpty() && podeInserir) {
				productDAO.update(product);
			}

			if (!podeInserir) {
				request.setAttribute("product", product);
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroDeProduto.jsp");
			request.setAttribute("list", productDAO.findAll());
			request.setAttribute("categories", productDAO.findAllCategories());
			request.setAttribute("msg", msg);
			dispatcher.forward(request, response);
		}
	}
}