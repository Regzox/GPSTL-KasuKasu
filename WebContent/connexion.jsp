<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Connexion Page</title>
</head>
<body>

<h1>Connexion</h1>

<form action="/KasuKasu/connectuser" >
	Enter your mail and password <br>
	Mail : <input type="text" name="mail" size="30px">
	Password : <input type="password" name="pass" size="30px">
	<input type="submit" value="submit">
	<br>
	<a href="/KasuKasu/retrievePassword.jsp">Mot de passe oublié?</a>
</form>
</body>
</html>