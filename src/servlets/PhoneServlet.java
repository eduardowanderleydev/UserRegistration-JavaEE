package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanLogin;
import dao.UserDAO;

@WebServlet("/PhoneServlet")
public class PhoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	UserDAO userDAO = new UserDAO();
	
	public PhoneServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userId = request.getParameter("user");

		BeanLogin user = userDAO.findById(userId); 
		
		request.getSession().setAttribute("choosedUser", user);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Phones.jsp");
		request.setAttribute("choosedUser", user);
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	
		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroDeUsuario.jsp");
			request.setAttribute("list", userDAO.findAll());
			dispatcher.forward(request, response);
		} else {
			BeanLogin user = (BeanLogin) request.getSession().getAttribute("choosedUser");
			String number = request.getParameter("number");
			String type = request.getParameter("type");
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/Phones.jsp");
			dispatcher.forward(request, response);
		}
	}
	
}
