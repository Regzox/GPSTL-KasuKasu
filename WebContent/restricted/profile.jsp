<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title id='titre'>Mon profil</title>

<!-- <link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/bootstrap.min.css"> -->
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/style.css" />
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/profile.css" />

<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/KasuKasu/js/check-connection.js"></script>
<script type="text/javascript" src="/KasuKasu/js/profile.js"></script>
<script	type="text/javascript" src="/KasuKasu/js/translation.js"></script>

<link rel="stylesheet" type="text/css" href="/KasuKasu/css/interface/sidebar.css" />
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/interface/navbar.css" />
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/interface/footer.css" />

</head>

<body onload = 'trans();fillMyProfile();getUserVacationStatus();'>

	<%@ include file="/fragments/interface/navbar.jspf"%>
	<%@ include file="/fragments/interface/sidebar.jspf"%>

	<div id="page">
<!-- 	<button id='btn_en' type="button" >English</button> -->
<!-- 	<button id='btn_fr' type="button">Français</button>	 -->

		<%@ include file="/fragments/interface/menus/profile_menu.jspf"%>
		<%@ include file="/fragments/profile.jspf"%>
		<br><br>
		<%@ include file="/fragments/interface/footer.jspf"%>
	</div>
</body>
</html>