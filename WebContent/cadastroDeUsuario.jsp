<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Página de Cadastro</title>
</head>
<body>
	<a href="AcessoLiberado.jsp" title="voltar para o inicio">Inicio</a> |
	<a href="Login.jsp" title="Deslogar">Sair</a>
	<h1 style="text-align: center">Cadastro de Usuário</h1>

	<h3>${msg}</h3>
	<form action="salvarUsuario" method="post" id="form-user-cadastro"
		onsubmit="return validarCampos() ? true : false;">
		<table>

			<tr>
				<td>Id :</td>
				<td><input type="text" readonly="readonly" name="id"
					value="${user.id}"></td>
			</tr>

			<tr>
				<td>Nome :</td>
				<td><input type="text" name="nome" id="nome"
					value="${user.nome}"></td>
			</tr>


			<tr>
				<td>Login :</td>
				<td><input type="text" name="login" id="login"
					value="${user.login}"></td>
			</tr>

			<tr>
				<td>Senha :</td>
				<td><input type="password" name="senha" id="senha"
					value="${user.senha}"></td>
			</tr>

			<tr>
				<td>Telefone :</td>
				<td><input type="text" name="fone" id="fone"
					value="${user.fone}"></td>
			</tr>

			<tr>
				<td><input type="submit" value="Salvar"></td>
				<td><input type="submit" value="Cancelar"
					onclick="document.getElementById('form-user-cadastro').action = 'salvarUsuario?acao=reset'"></td>
			</tr>


		</table>
	</form>

	<table>

		<caption>Usuarios Cadastrados</caption>

		<tr>
			<td>Id</td>
			<td>Login</td>
			<td>Nome</td>
			<td>Fone</td>
			<td>Excluir</td>
			<td>Editar</td>
		</tr>

		<c:forEach items="${list}" var="user">
			<tr>
				<td><c:out value="${user.id}" /></td>
				<td><c:out value="${user.login}" /></td>
				<td><c:out value="${user.nome}"></c:out></td>
				<td><c:out value="${user.fone}"></c:out></td>
				<td><a href="salvarUsuario?acao=delete&user=${user.id}">Excluir</a></td>
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
			} else if (document.getElementById("fone").value == '') {
				alert("Phone cannot be empty");
				return false;
			}
			return true;
		}
	</script>

</body>
</html>