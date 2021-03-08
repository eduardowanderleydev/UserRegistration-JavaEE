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
	<h1 style="text-align: center">Cadastro de Usuário</h1>

	<form action="salvarUsuario" method="post">
		<table>
			<tr>
				<td>Id :</td>
				<td><input type="text" readonly="readonly" name="id"
					value="${user.id}"></td>
			</tr>
			<tr>
				<td>Login :</td>
				<td><input type="text" name="login" value="${user.login}"></td>
			</tr>

			<tr>
				<td>Senha :</td>
				<td><input type="password" name="senha" value="${user.senha}"></td>
			</tr>

			<tr>
				<td><input type="submit" value="Salvar"></td>
			</tr>

		</table>
	</form>

	<h1>Usuários cadastrados</h1>

	<table>
		<c:forEach items="${list}" var="user">
			<tr>
				<td><c:out value="${user.id}" /></td>
				<td><c:out value="${user.login}" /></td>
				<td><c:out value="${user.senha}"></c:out></td>
				<td><a href="salvarUsuario?acao=delete&user=${user.login}">Excluir</a></td>
				<td><a href="salvarUsuario?acao=edit&user=${user.login}">Editar</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>