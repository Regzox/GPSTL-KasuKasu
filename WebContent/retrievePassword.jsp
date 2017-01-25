<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/bootstrap.min.css">

<script type="text/javascript" src="/KasuKasu/js/connexion.js"></script>
<script	type="text/javascript" src="/KasuKasu/js/traduction.js"></script>
<script type="text/javascript" src="/KasuKasu/js/cookies.js"></script>

<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	trans('retrievPassword.jsp','titre');
	trans('retrievPassword.jsp','ret');
	trans('retrievPassword.jsp','email');
	trans('retrievPassword.jsp','submit');
});
</script>
<title id='titre'>Récupération de mot de passe</title>
</head>
<body>

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

