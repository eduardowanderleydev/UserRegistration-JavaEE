package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanLogin;
import beans.BeanPhone;
import dao.PhoneDAO;
import dao.UserDAO;

@WebServlet("/PhoneServlet")
public class PhoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDAO userDAO = new UserDAO();
	private PhoneDAO phoneDAO = new PhoneDAO();
	
	public PhoneServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		long phoneId = Long.parseLong(request.getParameter("phone"));
		String userId = request.getParameter("user");
		BeanLogin user = userDAO.findById(userId);
		long idLong = Long.parseLong(userId);
		
		if (acao != null && acao.equalsIgnoreCase("delete")) {
			phoneDAO.delete(phoneId);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Phones.jsp");
		request.getSession().setAttribute("choosedUser", user);
		request.setAttribute("choosedUser", user);
		request.setAttribute("listPhones", phoneDAO.findAll(idLong));
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
			long id = user.getId();
			
			BeanPhone phone = new BeanPhone();
			phone.setNumber(number);
			phone.setType(type);
			phone.setUser(id);
			
			phoneDAO.insert(phone);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/Phones.jsp");
			request.getSession().setAttribute("choosedUser", user);
			request.setAttribute("user", user);
			request.setAttribute("listPhones", phoneDAO.findAll(id));
			dispatcher.forward(request, response);
		}
	}
	
}
