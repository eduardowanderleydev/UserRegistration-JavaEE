<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<form action="LoginServlet" method="post" class="login-form">
		Login <input type="text" name="login"> <br> 
		Senha <input type="password" name="senha"> <br>
		<button type="submit" value="Logar">Logar</button>
	</form>
</body>
</html>