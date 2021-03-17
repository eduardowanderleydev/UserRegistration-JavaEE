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

		String acao = request.getParameter("acao") != null ? request.getParameter("acao") : "list";

		if (acao.equalsIgnoreCase("list")) {

			String userId = request.getParameter("user");
			BeanLogin user = userDAO.findById(userId);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/Phones.jsp");

			request.setAttribute("listPhones", phoneDAO.findAll(Long.parseLong(userId)));
			request.getSession().setAttribute("choosedUser", user);
			request.setAttribute("choosedUser", user);

			dispatcher.forward(request, response);
		} else if (acao != null && acao.equalsIgnoreCase("delete")) {
			long phoneId = Long.parseLong(request.getParameter("phone"));
			phoneDAO.delete(phoneId);

			request.setAttribute("msg", "Delete successfully completed");

			String userId = request.getParameter("user");
			BeanLogin user = userDAO.findById(userId);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/Phones.jsp");
			request.setAttribute("listPhones", phoneDAO.findAll(Long.parseLong(userId)));
			request.getSession().setAttribute("choosedUser", user);
			request.setAttribute("choosedUser", user);
			dispatcher.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("back")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroDeUsuario.jsp");
			request.setAttribute("list", userDAO.findAll());
			dispatcher.forward(request, response);
		} else {
			BeanLogin user = (BeanLogin) request.getSession().getAttribute("choosedUser");
			String number = request.getParameter("number");
			String type = request.getParameter("type");
			long id = user.getId();

			if (number == null || number.isEmpty() || type == null || type.isEmpty()) {
				RequestDispatcher ReqDispatcher = request.getRequestDispatcher("/Phones.jsp");
				request.getSession().setAttribute("choosedUser", user);
				request.setAttribute("user", user);
				ReqDispatcher.forward(request, response);
			} else {
				BeanPhone phone = new BeanPhone();
				phone.setNumber(number);
				phone.setType(type);
				phone.setUser(id);

				phoneDAO.insert(phone);
				request.setAttribute("msg", "registration completed succesfully");

				RequestDispatcher dispatcher = request.getRequestDispatcher("/Phones.jsp");
				request.getSession().setAttribute("choosedUser", user);
				request.setAttribute("user", user);

				request.setAttribute("listPhones", phoneDAO.findAll(id));
				dispatcher.forward(request, response);
			}

		}
	}
}
