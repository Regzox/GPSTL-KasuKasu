<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/bootstrap.min.css">

<script type="text/javascript" src="/KasuKasu/js/connexion.js"></script>
<script	type="text/javascript" src="/KasuKasu/js/translation.js"></script>

<title id='titre'>Connexion</title>
</head>
<body onload = "trans()">

	<div class='layer-center'>

		<p id='connex' class='capital'>Connexion</p>

		<div id='notifier' class='notifier'></div>

		<form action="javascript:(function(){return;})()" method="get"
			OnSubmit="javascript:connexion(this)">

			<!--  	<form action="/KasuKasu/connectuser"> -->
			<div class="form-group row">
				<label id='email' for="email" class="col-xs-2 col-form-label">Email</label>
				<div class="col-xs-10">
					<input class="form-control" name="mail" type="email" value=""
						id="mail">
				</div>
			</div>


			<div class="form-group row">
<!-- 				<label for="mdp" class="col-xs-2 col-form-label">Password</label> -->
								<label id='mdp' for="mdp" class="col-xs-2 col-form-label">Mot de passe</label>
				
				<div class="col-xs-10">
					<input class="form-control" name="pass" type="password" value=""
						id="pass">
				</div>
			</div>

			<input id='connex' type="submit" class="btn btn-primary btn-block"
				value="Connexion"> <br> 
				<a
				href="/KasuKasu/retrievePassword.jsp" id='mdpo'>Mot de passe oublié?</a>
		</form>
	</div>
</body>
</html>

