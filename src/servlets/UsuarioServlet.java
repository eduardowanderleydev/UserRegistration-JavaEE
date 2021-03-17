package servlets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

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
		String acao = request.getParameter("acao") != null ? request.getParameter("acao") : "listarTodos";
		String user = request.getParameter("user");

		if (acao != null && acao.equals("delete")) {
			userDAO.delete(user);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroDeUsuario.jsp");
			request.setAttribute("list", userDAO.findAll());
			dispatcher.forward(request, response);
		} else if (acao != null && acao.equals("edit")) {
			BeanLogin userEdit = userDAO.findById(user);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroDeUsuario.jsp");
			request.setAttribute("user", userEdit);
			request.setAttribute("list", userDAO.findAll());
			dispatcher.forward(request, response);
		} else if (acao != null && acao.equalsIgnoreCase("listartodos")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroDeUsuario.jsp");
			request.setAttribute("list", userDAO.findAll());
			dispatcher.forward(request, response);
		} else if (acao != null && acao.equalsIgnoreCase("download")) {

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
					os.write(bytes, 0, read);
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

				if (imagePhoto != null && imagePhoto.getInputStream().available() > 0) {

					byte[] image = StreamToByte(imagePhoto.getInputStream());

					new Base64();
					String photoBase64 = Base64.encodeBase64String(image); // change
																			// the
					// set the attributes in the user
					user.setPhotoBase64(photoBase64);
					user.setContentType(imagePhoto.getContentType());

					/* change image to miniature */

					new Base64();
					byte[] imageByteDecode = Base64.decodeBase64(photoBase64);

					BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));

					int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

					BufferedImage resizedImage = new BufferedImage(100, 100, type);
					Graphics2D graphic = resizedImage.createGraphics();
					graphic.drawImage(bufferedImage, 0, 0, 100, 100, null);
					graphic.dispose();

					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write(resizedImage, "png", baos);

					String miniatureBase64 = "data:image/png;base64,"
							+ DatatypeConverter.printBase64Binary(baos.toByteArray());

					user.setPhotoBase64Miniature(miniatureBase64);

				} else {

				}

				/* Proccess pdf */

				Part curriculumPdf = request.getPart("curriculum");

				if (curriculumPdf != null && curriculumPdf.getInputStream().available() > 0) {
					new Base64();
					String curriculumBase64 = Base64.encodeBase64String(StreamToByte(curriculumPdf.getInputStream())); // change
																														// the
					// set the attributes in the user
					user.setCurriculumContentType(curriculumPdf.getContentType());
					user.setCurriculumBase64(curriculumBase64);
				} else {
					user.setCurriculumBase64(request.getParameter("curriculumTemp"));
					user.setCurriculumContentType(request.getParameter("contentTypeCurriculumTemp"));
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