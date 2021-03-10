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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		
		if (acao.equals("list")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroDeProduto.jsp");
			request.setAttribute("list", productDAO.findAll());
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String quantity = request.getParameter("quantity");
		String price = request.getParameter("price");
		
		BeanProduct product = new BeanProduct();
		product.setId(!id.isEmpty() ? Long.parseLong(id) : 0);
		product.setName(name);
		product.setPrice(Double.parseDouble(price));
		product.setQuantity(Integer.parseInt(quantity));
		
		productDAO.insert(product);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroDeProduto.jsp");
		request.setAttribute("list", productDAO.findAll());
		dispatcher.forward(request, response);
	}

}
