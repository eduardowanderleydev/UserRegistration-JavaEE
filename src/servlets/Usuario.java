package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanLogin;
import dao.UsuarioDAO;

@WebServlet("/salvarUsuario")
public class Usuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	UsuarioDAO userDAO = new UsuarioDAO();

	public Usuario() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");
		String user = request.getParameter("user");

		if (acao.equals("delete")) {
			userDAO.delete(user);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroDeUsuario.jsp");
			request.setAttribute("list", userDAO.findAll());
			dispatcher.forward(request, response);
		} else if (acao.equals("edit")) {
			BeanLogin userEdit = userDAO.findByName(user);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroDeUsuario.jsp");
			request.setAttribute("user", userEdit);
			dispatcher.forward(request, response);
		} else if (acao.equalsIgnoreCase("listartodos")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroDeUsuario.jsp");
			request.setAttribute("list", userDAO.findAll());
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroDeUsuario.jsp");
			request.setAttribute("list", userDAO.findAll());
			dispatcher.forward(request, response);
		} else {
			String id = request.getParameter("id");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String nome = request.getParameter("nome");

			BeanLogin user = new BeanLogin();
			user.setId(!id.isEmpty() ? Long.parseLong(id) : 0);
			user.setLogin(login);
			user.setSenha(senha);
			user.setNome(nome);

			if (id == null || id.isEmpty()) {
				userDAO.insert(user);
			} else {
				userDAO.update(user);
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroDeUsuario.jsp");
			request.setAttribute("list", userDAO.findAll());
			dispatcher.forward(request, response);
		}

	}
}