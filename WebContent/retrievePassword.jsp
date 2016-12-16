<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/bootstrap.min.css">

<script type="text/javascript" src="/KasuKasu/js/connexion.js"></script>
<script	type="text/javascript" src="/KasuKasu/js/translation.js"></script>

<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<title id='titre'>Récupération de mot de passe</title>
</head>
<body onload="trans()">

	<div class='layer-center'>

		<p id='ret' class='capital'>Récupération de votre mot de passe</p>


		<form action="/KasuKasu/retrievepassword">


			<div class="form-group row">
				<label id='email' for="email" class="col-xs-2 col-form-label">Email</label>
				<div class="col-xs-10">
					<input class="form-control" name="mail" type="email" value=""
						id="mail">
				</div>
			</div>


		
			<input id='submit' type="submit" class="btn btn-primary btn-block"
				value="Envoyer"> 
		</form>
	</div>
</body>
</html>

