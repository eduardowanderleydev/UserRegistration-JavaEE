package servlets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.BeanLogin;
import dao.UserDAO;

@WebServlet("/salvarUsuario")
@MultipartConfig
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	UserDAO userDAO = new UserDAO();

	public UsuarioServlet() {
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
			BeanLogin userEdit = userDAO.findById(user);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroDeUsuario.jsp");
			request.setAttribute("user", userEdit);
			request.setAttribute("list", userDAO.findAll());
			dispatcher.forward(request, response);
		} else if (acao.equalsIgnoreCase("listartodos")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroDeUsuario.jsp");
			request.setAttribute("list", userDAO.findAll());
			dispatcher.forward(request, response);
		}else if (acao.equalsIgnoreCase("download")) {
			
			BeanLogin userDownload = userDAO.findById(user);
			
			if (userDownload != null) { 	
				
				String contentType = "";
				byte[] fileBytes = null;
				String type = request.getParameter("type");
				
				if (type.equalsIgnoreCase("image")) {
					contentType = userDownload.getContentType();
					new Base64();
					fileBytes = Base64.decodeBase64(userDownload.getPhotoBase64());
				} else if (type.equalsIgnoreCase("curriculum")) {
					contentType = userDownload.getCurriculumContentType();
					new Base64();
					fileBytes = Base64.decodeBase64(userDownload.getCurriculumBase64());
				}
				
				String TypeFile = contentType.split("/")[1];
				// change the header passing the request for download
				response.setHeader("Content-Disposition", "attachment;filename=arquivo." + TypeFile);
				
				/* Places bytes in an input object to process */
				InputStream is = new ByteArrayInputStream(fileBytes);
				
				/* Answer to Browse */
				int read = 0;
				byte[] bytes = new byte[1024];
				OutputStream os = response.getOutputStream();
				
				while ((read = is.read(bytes)) != -1) {
					os.write(bytes,0,read);
				}
				
				os.flush();
				os.close();
			}
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
			String fone = request.getParameter("fone");

			String cep = request.getParameter("cep");
			String rua = request.getParameter("rua");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String estado = request.getParameter("estado");
			String ibge = request.getParameter("ibge");

			BeanLogin user = new BeanLogin();
			user.setId(!id.isEmpty() ? Long.parseLong(id) : null);
			user.setLogin(login);
			user.setSenha(senha);
			user.setNome(nome);
			user.setFone(fone);

			user.setCep(cep);
			user.setRua(rua);
			user.setBairro(bairro);
			user.setCidade(cidade);
			user.setEstado(estado);
			user.setIbge(ibge);

			String msg = null;
			boolean podeInserir = true;

			/* Starting file upload */

			if (ServletFileUpload.isMultipartContent(request)) { // checks if there is a upload file

				Part imagePhoto = request.getPart("photo"); // get the object received from a multipart/form-data
				
				new Base64();
				String photoBase64 = Base64.encodeBase64String(StreamToByte(imagePhoto.getInputStream())); // change the image to base64
				
				//set the attributes in the user
				user.setPhotoBase64(photoBase64);
				user.setContentType(imagePhoto.getContentType());
				
				/* Proccess pdf */
				
				Part curriculumPdf = request.getPart("curriculum");
				
				if (curriculumPdf != null) {
					new Base64();
					String curriculumBase64 = Base64.encodeBase64String(StreamToByte(curriculumPdf.getInputStream())); // change the image to base64
					
					//set the attributes in the user
					user.setCurriculumContentType(curriculumPdf.getContentType());
					user.setCurriculumBase64(curriculumBase64);
				}
				
				
			}

			/* Checks the fields validations */
			if (login == null || login.isEmpty()) {
				podeInserir = false;
				msg = "Login cannot be empty";
			} else if (nome == null || nome.isEmpty()) {
				podeInserir = false;
				msg = "Name cannot be empty";
			} else if (senha == null || senha.isEmpty()) {
				podeInserir = false;
				msg = "Password cannot be empty";
			} else if (fone == null || fone.isEmpty()) {
				podeInserir = false;
				msg = "Phone number cannot be empty";
			} else {
				/* Checks the validation of occurrence in the database */
				if (id == null || id.isEmpty() && !userDAO.validateLogin(login)) {
					msg = "Login already exists";
					podeInserir = false;
				}

				if (id == null || id.isEmpty() && !userDAO.validatePassword(senha)) {
					msg = "Password already exists";
					podeInserir = false;
				}
			}

			if (msg != null) {
				request.setAttribute("msg", msg);
			}

			if (id == null || id.isEmpty() && podeInserir) {
				userDAO.insert(user);
				msg = "registration successfully completed";
			} else if (id != null || !id.isEmpty() && podeInserir) {
				userDAO.update(user);
			}

			if (!podeInserir) {
				request.setAttribute("user", user);
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroDeUsuario.jsp");
			request.setAttribute("msg", msg);
			request.setAttribute("list", userDAO.findAll());
			dispatcher.forward(request, response);
		}

	}

	/* Change the flow input data from image to a byte array */
	private byte[] StreamToByte(InputStream image) {
		try {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int reads = image.read();
			
			while (reads != -1) {
				baos.write(reads);
				reads = image.read();
			}
			
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}