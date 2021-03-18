<%@page import="dao.UserDAO"%>
<%@page import="beans.BeanLogin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Página de Cadastro</title>

<!-- Adicionando JQuery -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
	integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
	crossorigin="anonymous"></script>
</head>
<body>
	<a href="AcessoLiberado.jsp" title="voltar para o inicio">Inicio</a> |
	<a href="Login.jsp" title="Deslogar">Sair</a>
	<h1 style="text-align: center">Cadastro de Usuário</h1>

	<h3>${msg}</h3>
	<form action="salvarUsuario" method="post" id="form-user-cadastro"
		onsubmit="return validarCampos() ? true : false;"
		enctype="multipart/form-data">
		<table>

			<tr>
				<td>Id :</td>
				<td><input type="text" readonly="readonly" name="id"
					value="${user.id}" placeholder="Gerado automaticamente"></td>

				<td>Rua :</td>
				<td><input type="text" name="rua" id="rua" value="${user.rua}"
					placeholder="Digite o nome da rua"></td>

			</tr>

			<tr>
				<td>Nome :</td>
				<td><input type="text" name="nome" id="nome"
					value="${user.nome}" placeholder="Digite o nome do usuário"></td>

				<td>Bairro :</td>
				<td><input type="text" name="bairro" id="bairro"
					value="${user.bairro}" placeholder="Digite o nome do bairro"></td>

			</tr>


			<tr>
				<td>Login :</td>
				<td><input type="text" name="login" id="login"
					value="${user.login}" placeholder="Digite o login do usuario"></td>

				<td>Cidade :</td>
				<td><input type="text" name="cidade" id="cidade"
					value="${user.cidade}" placeholder="Digite o nome da cidade"></td>

			</tr>

			<tr>
				<td>Senha :</td>
				<td><input type="password" name="senha" id="senha"
					value="${user.senha}" placeholder="Digite a senha do usuário"></td>

				<td>Estado :</td>
				<td><input type="text" name="estado" id="estado"
					value="${user.estado}" placeholder="Digite o nome do estado"></td>

			</tr>

			<tr>
				<td>Cep :</td>
				<td><input type="text" name="cep" id="cep" value="${user.cep}"
					onblur="consultarCep()" placeholder="Digite o CEP"></td>

				<td>IBGE :</td>
				<td><input type="text" name="ibge" id="ibge"
					value="${user.ibge}" placeholder="Digite o número do IBGE"></td>


			</tr>

			<tr>
				<td>Foto :</td>
				<td><input type="file" name="photo" id="photo">
				<td>Sexo :</td>
				<td><input type="radio" name="sexo"
					<%if (request.getAttribute("user") != null) {
						BeanLogin user = (BeanLogin) request.getAttribute("user");
						if (user.getSexo() != null && user.getSexo().equals("masculino")) {
							out.print("");
							out.print("checked=\"checked\"");
							out.print("");
						}
					}%>
					value="masculino"> Masculino <input type="radio"
					name="sexo"
					<%if (request.getAttribute("user") != null) {
						BeanLogin user = (BeanLogin) request.getAttribute("user");
						if (user.getSexo() != null && user.getSexo().equals("feminino")) {
							out.print("");
							out.print("checked=\"checked\"");
							out.print("");
						}
					}%>
					value="feminino"> feminino</td>
			</tr>

			<tr>
				<td>Curriculo :</td>
				<td><input type="file" name="curriculum" id="curriculum">
				<td>Ativo :</td>
				<td><input type="checkbox" name="active" id="active"
					<%if (request.getAttribute("user") != null) {
						BeanLogin user = (BeanLogin) request.getAttribute("user");
						if (user.isActive()) {
							out.print("");
							out.print("checked=\"checked\"");
							out.print("");
						}
					}%>>
			</tr>


			<tr>
				<td>Perfil de Usuário</td>
				<td><select id="profile" name="profile">
						<option value="not_informed">[--SELECIONE--]</option>
						<option value="admin"
						
						<%if (request.getAttribute("user") != null) {
							BeanLogin user = (BeanLogin) request.getAttribute("user");
							if (user.getProfile() != null && user.getProfile().equalsIgnoreCase("admin")) {
								out.print("");
								out.print("selected=\"selected\"");
								out.print("");
							}
						}%> 
						>Admninistrador</option>
						<option value="secretary"
						
						<%if (request.getAttribute("user") != null) {
							BeanLogin user = (BeanLogin) request.getAttribute("user");
							if (user.getProfile() != null && user.getProfile().equalsIgnoreCase("secretary")) {
								out.print("");
								out.print("selected=\"selected\"");
								out.print("");
							}
						}%>
						>Secretário</option>
						<option value="manager"
						
						<%if (request.getAttribute("user") != null) {
							BeanLogin user = (BeanLogin) request.getAttribute("user");
							if (user.getProfile() != null && user.getProfile().equalsIgnoreCase("manager")) {
								out.print("");
								out.print("selected=\"selected\"");
								out.print("");
							}
						}%>
						>Gerente</option>
				</select></td>
			</tr>

			<tr>
				<td><input type="submit" value="Salvar"></td>
				<td><input type="submit" value="Cancelar"
					onclick="document.getElementById('form-user-cadastro').action = 'salvarUsuario?acao=reset'"></td>
			</tr>

		</table>
	</form>

	<form action="searchServlet" method="post">
		<table>
			<tr> 
			<td>Descrição</td>
			<td> <input type="text" name = "description" id = "search"> </td>
			<td> <input type="submit" name = "search" id = "search "value = "Search"> </td>
			</tr>
		</table>
	</form>


	<table>

		<caption>Usuarios Cadastrados</caption>

		<tr>
			<td>Foto</td>
			<td>Id</td>
			<td>Login</td>
			<td>Nome</td>
			<td>CEP</td>
			<td>Rua</td>
			<td>Bairro</td>
			<td>Cidade</td>
			<td>Estado</td>
			<td>Ibge</td>
			<td>Telefones</td>
			<td>Curriculo</td>
			<td>Excluir</td>
			<td>Editar</td>
		</tr>

		<c:forEach items="${list}" var="user">
			<tr>

				<c:if test="${user.photoBase64Miniature != null}">
					<td><a
						href="salvarUsuario?acao=download&type=image&user=${user.id}">
							<img src='<c:out value="${user.photoBase64Miniature}"></c:out>'
							width="50px" alt="User Image" title="User Image">
					</a></td>
				</c:if>
				<c:if test="${user.photoBase64Miniature == null}">
					<td><img alt="Imagem User" width="50px"
						src="https://i.pinimg.com/originals/0c/3b/3a/0c3b3adb1a7530892e55ef36d3be6cb8.png"
						onclick="alert('user does not have a registered profile photo')">
					</td>
				</c:if>

				<td><c:out value="${user.id}" /></td>
				<td><c:out value="${user.login}" /></td>
				<td><c:out value="${user.nome}"></c:out></td>
				<td><c:out value="${user.cep}"></c:out></td>
				<td><c:out value="${user.rua}"></c:out></td>
				<td><c:out value="${user.bairro}"></c:out></td>
				<td><c:out value="${user.cidade}"></c:out></td>
				<td><c:out value="${user.estado}"></c:out></td>
				<td><c:out value="${user.ibge}"></c:out></td>
				<td><a href="PhoneServlet?acao=list&user=${user.id}"> <img
						title="Telefones" width="30px"
						src="http://simpleicon.com/wp-content/uploads/phone-symbol-2.png">
				</a></td>

				<c:if
					test="${user.curriculumBase64 != null || user.curriculumBase64.isEmpty() == false}">
					<td><a
						href="salvarUsuario?acao=download&type=curriculum&user=${user.id}">
							<img alt="Curriculo" width="40px"
							src="https://www.ufpb.br/propesq/contents/imagens/pdf-icon.png/@@images/image.png">
					</a></td>
				</c:if>
				<c:if
					test="${user.curriculumBase64 == nul || luser.curriculumBase64.isEmpty() == true }">
					<td><img alt="Curriculo" width="40px"
						src="https://www.ufpb.br/propesq/contents/imagens/pdf-icon.png/@@images/image.png"
						onclick="alert('user does not have a registered curriculum')">
					</td>
				</c:if>
				<td><a href="salvarUsuario?acao=delete&user=${user.id}"
					onclick="return confirm('Are you sure');">Excluir</a></td>
				<td><a href="salvarUsuario?acao=edit&user=${user.id}">Editar</a></td>
			</tr>
		</c:forEach>
	</table>

	<script type="text/javascript">
		function validarCampos() {
			if (document.getElementById("nome").value == '') {
				alert("Name cannot be empty");
				return false;
			} else if (document.getElementById("login").value == '') {
				alert("Login cannot be empty");
				return false;
			} else if (document.getElementById("senha").value == '') {
				alert("Password cannot be empty");
				return false;
			}
			return true;
		}

		function consultarCep() {
			var cep = $("#cep").val();

			$.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?",
					function(dados) {

						if (!("erro" in dados)) {
							$("#rua").val(dados.logradouro);
							$("#bairro").val(dados.bairro);
							$("#cidade").val(dados.localidade);
							$("#estado").val(dados.uf);
							$("#ibge").val(dados.ibge);
						} else {

							$("#cep").val('');
							$("#rua").val('');
							$("#bairro").val('');
							$("#cidade").val('');
							$("#uf").val('');
							$("#ibge").val('');

							alert("CEP não encontrado.");
						}
					});
		}
	</script>
</body>
</html>