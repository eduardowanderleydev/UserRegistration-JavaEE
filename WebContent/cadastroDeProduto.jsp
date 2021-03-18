<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastro de Produtos</title>
<script src="resources/javascript/jquery.min.js" type="text/javascript"></script>
<script src="resources/javascript/jquery.maskMoney.min.js"
	type="text/javascript"></script>
</head>
<body>
	<a href="AcessoLiberado.jsp" title="voltar para o inicio">Inicio</a> |
	<a href="Login.jsp" title="Deslogar">Sair</a>
	<h1 style="text-align: center">Cadastro de Produtos</h1>

	<h3>${msg}</h3>
	<form action="ProductServlet" method="post" id="form-product"
		onsubmit="return validarCampos() ? true : false;">
		<table>
			<tr>
				<td>Id :</td>
				<td><input type="text" readonly="readonly" name="id"
					value="${product.id}"></td>
			</tr>

			<tr>
				<td>Nome :</td>
				<td><input type=text name="name" id="name"
					value="${product.name}" maxlength="25" required="required"></td>
			</tr>

			<tr>
				<td>Quantidade :</td>
				<td><input type="text" name="quantity" id="quantity"
					maxlength="5" value="${product.quantity}" required="required"></td>
			</tr>

			<tr>
				<td>Preço :</td>
				<td><input type="text" name="price" id="price"
					data-precision="2" data-thousands="." data-decimal=","
					value="${product.textPrice}" maxlength="10"></td>
			</tr>

			<tr>
				<td><input type="submit" value="Save"></td>
				<td><input type="submit" value="Cancel"
					onclick="document.getElementById('form-product').action = 'ProductServlet?acao=reset'"></td>
			</tr>
		</table>
	</form>

	<table>
		<caption>Produtos Cadastrados</caption>

		<tr>
			<td>Id</td>
			<td>Nome</td>
			<td>Quantidade</td>
			<td>Valor</td>
			<td>Excluir</td>
			<td>Editar</td>
		</tr>

		<c:forEach items="${list}" var="product">
			<tr>
				<td><c:out value="${product.id}" /></td>
				<td><c:out value="${product.name}" /></td>
				<td><c:out value="${product.quantity}" /></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="2"
						value="${product.price}"></fmt:formatNumber></td>
				<td><a href="ProductServlet?acao=delete&product=${product.id}"
					onclick="return confirm('Are you sure');">Delete</a></td>
				<td><a href="ProductServlet?acao=edit&product=${product.id}">Edit</a>
				</td>
			</tr>
		</c:forEach>
	</table>

	<script>
		$(function() {
			$('#price').maskMoney();
		})

		$(document).ready(function() {
			$("#quantity").keyup(function() {
				$("#quantity").val(this.value.match(/[0-9]*/));
			});
		});
	</script>

</body>
</html>