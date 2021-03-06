<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 		<title>KasuKasu Dashboard</title> -->
<title id='titre'>KasuKasu</title>

<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/bootstrap.min.css">
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="/KasuKasu/js/tether.min.js"></script>
<script type="text/javascript" src="/KasuKasu/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/KasuKasu/js/cookies.js"></script>
<script type="text/javascript" src="/KasuKasu/js/traduction.js"></script>

<script type="text/javascript" src="/KasuKasu/js/aide.js"></script>

<script type="text/javascript">
  		$(document).ready(function() { 
 				trans('dashboard.jsp','titre'); 
  				trans('dashboard.jsp','bienvenue'); 
  			}); 
			</script>
</head>

<body onload="javascript:init();">

	<%@ include file="/fragments/interface/navbar.jspf"%>
	<%@ include file="/fragments/interface/sidebar.jspf"%>

	<div id='page'>
		<div class='layer-center'>
			<!-- 			<p class='capital' id='accueil'>Accueil</p> -->
			<p class='capital' id ='bienvenue'>Bienvenue sur KasuKasu!</p>
		</div>

		<div class='layer-center'>
			<p id='act'></p>
			<p id='emprunt'></p>
			<p id='pret'></p>
			<p id='pend'></p>
			<p id='back'></p>
			<p id='friendRequests'></p>
			<pre id="contents"></pre>
		</div>

		<%@ include file="/fragments/interface/footer.jspf"%>

	</div>
</body>
</html>