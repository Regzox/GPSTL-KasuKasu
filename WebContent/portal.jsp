<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- <title>Welcome to KasuKasu !</title> -->
 <title id='titre'>Bienvenue sur KasuKasu !</title> 
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/header.css">

<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="/KasuKasu/js/tether.min.js"></script>
<script	type="text/javascript" src="/KasuKasu/js/bootstrap.min.js"></script>
<script	type="text/javascript" src="/KasuKasu/js/translation.js"></script>

</head>
<body onload="trans()">

	<%@ include file="/fragments/header.jspf" %>

	<div id='page' class='layer-center'>
<!-- 		<p class='capital'>Welcome to KasuKasu !<p> -->
<!-- 		<p>The web site where you can share your items with your friends</p> -->

	<button id='btn_en' type="button" >English</button>
	<button id='btn_fr' type="button">Français</button>	
<div id='bienvenue'>Bienvenue sur KasuKasu !</div>
<!-- 		<p class='capital'>Bienvenue sur KasuKasu !<p> -->
<p id='site'>Le site où vous pouvez prêter vos objets à vos amis</p>

<!-- 		<button type="button" class="btn btn-primary btn-block" onclick="window.location.href = '/KasuKasu/createuser.jsp';">Create your account</button> -->
<!-- 		<button type="button" class="btn btn-primary btn-block" onclick="window.location.href = '/KasuKasu/connexion.jsp';">Sign in </button> -->
		
		<button id='btn_insc' type="button" class="btn btn-primary btn-block" onclick="window.location.href = '/KasuKasu/createuser.jsp';">Créer un compte</button>
		<button id='btn_connex' type="button" class="btn btn-primary btn-block" onclick="window.location.href = '/KasuKasu/connexion.jsp';">Se connecter </button>
	</div>

</body>
</html>