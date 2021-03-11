<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastro de Produtos</title>
</head>
<body>

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
				<td><input type="text" name="name" id="name"
					value="${product.name}"></td>
			</tr>

			<tr>
				<td>Quantidade :</td>
				<td><input type="text" name="quantity" id="quantity"
					value="${product.quantity}"></td>
			</tr>

			<tr>
				<td>Preço :</td>
				<td><input type="text" name="price" id="price"
					value="${product.price}"></td>
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
				<td><c:out value="${product.price}" /></td>
				<td><a href="ProductServlet?acao=delete&product=${product.id}">Delete</a>
				</td>
				<td><a href="ProductServlet?acao=edit&product=${product.id}">Edit</a>
				</td>
			</tr>

		</c:forEach>
	</table>

	<script type="text/javascript">
		function validarCampos() {
			if (document.getElementById('name').value == '') {
				alert("Name cannot be empty");
				return false;
			}
			if (document.getElementById('quantity').value == '') {
				alert("Quantity cannot be empty");
				return false;
			}
			if (document.getElementById('price').value == '') {
				alert("Price cannot be empty");
				return false;
			}
			return true;
		}
	</script>

</body>
</html>