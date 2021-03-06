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
	<h1 style="text-align: center">Cadastro de Telefone</h1>

	<h3>${msg}</h3>

	<form action="PhoneServlet" method="post" id="form-phone-cadastro">
		<table>

			<tr>
				<td>User :</td>
				<td><input type="text" name="user" readonly="readonly"
					id="user" value="${choosedUser.id}"></td>
			</tr>

			<tr>
				<td>Número :</td>
				<td><input type="text" name="number" id="number"></td>
			</tr>

			<tr>
				<td>Tipo :</td>
				<td><select name="type" id="type">
						<option>Casa</option>
						<option>Contato</option>
						<option>Celular</option>
				</select></td>
			</tr>

			<tr>
				<td><input type="submit" value="Salvar"></td>
				<td><input type="submit" value="Cancelar"
					onclick="document.getElementById('form-phone-cadastro').action = 'PhoneServlet?acao=back'"></td>
			</tr>
		</table>
	</form>

	<table>

		<caption>Telefones Cadastrados</caption>
		
		<tr>
			<td>Id</td>
			<td>Número</td>
			<td>Tipo</td>
			<td>Excluir</td>
		</tr>
		
		<c:forEach items="${listPhones}" var="phone">
			<tr>
				<td><c:out value="${phone.id}" /></td>
				<td><c:out value="${phone.number}" /></td>
				<td><c:out value="${phone.type}"></c:out></td>
				<td><a
					href="PhoneServlet?acao=delete&phone=${phone.id}&user=${choosedUser.id}"
					onclick="return confirm('Are you sure');">Excluir</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>