<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <title>Welcome to KasuKasu !</title> -->
<title id='titre'>Bienvenue sur KasuKasu !</title>
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/header.css">

<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="/KasuKasu/js/tether.min.js"></script>
<script type="text/javascript" src="/KasuKasu/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/KasuKasu/js/translation.js"></script>

</head>
<body onload="trans()">

	<%@ include file="/fragments/header.jspf"%>

	<div class="layer-center">
		<div style="width: 100%">

			<div>
				<br><br><br>
				<h1 id='bienvenue'>Bienvenue sur KasuKasu !</h1>

				<p>
					<h3 id='site'>Le site où vous pouvez prêter vos objets à vos amis</h3>
				</p>
				<br><br><br><br>
			</div>

			<div class="wrapper btn-block">
				<div class="tier">
					<button id='btn_en' type="button" class="btn btn-info btn-block">English</button>
				</div>
				<div class="tier"></div>
				<div class="tier">
					<button id='btn_fr' type="button" class="btn btn-info btn-block">Français</button>
				</div>
			</div>

			<button id='btn_insc' type="button" class="btn btn-primary btn-block"
				onclick="window.location.href = '/KasuKasu/createuser.jsp';">Créer
				un compte</button>
			<button id='btn_connex' type="button"
				class="btn btn-primary btn-block"
				onclick="window.location.href = '/KasuKasu/connexion.jsp';">Se
				connecter</button>
		</div>
	</div>

</body>
</html>