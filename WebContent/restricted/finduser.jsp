<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/bootstrap.min.css">

<script type="text/javascript" src="/KasuKasu/js/finduser.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script	type="text/javascript" src="/KasuKasu/js/translation.js"></script>

<title id='titre'>Rechercher un utilisateur</title>
</head>
<body onload="trans()">

	<%@ include file="/fragments/sidebar.jspf"%>

	<div id='page'>
		<div class='layer-center'>
			<p id='rech' class='capital'>Rechercher un utilisateur</p>


			<form action="javascript:(function(){return;})()" method="get"
				OnSubmit="javascript:finduser(this)">

				<div class="form-group row">
					<div class="col-xs-10">
						<input class="form-control" name="value" value="" id="value">
					</div>
				</div>

				<input id='submit' type="submit" class="btn btn-primary btn-block"
					value="Chercher"> <br>
			</form>

			<div id='notifier'></div>
		</div>
	</div>
</body>
</html>

