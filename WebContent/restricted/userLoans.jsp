<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/bootstrap.min.css">
<script type="text/javascript" src="/KasuKasu/js/utils.js"></script>
<link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/applicants.css" />
<script type="text/javascript" src="/KasuKasu/js/cookies.js"></script>
<script type="text/javascript"
	src="/KasuKasu/js/inflator/views/loaning.js"></script>
<script type="text/javascript" src="/KasuKasu/js/inflator/mirror.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/KasuKasu/js/traduction.js"></script>



<script type="text/javascript">
	$(document).ready(function() {
		trans('userLoans.jsp', 'titre');
		trans('userLoans.jsp', 'liste');
	});
</script>

<link rel="stylesheet" type="text/css" href="/KasuKasu/css/sidebar.css" />
<title id='titre'>Mes Emprunts</title>
</head>
<body onload="javascript:find_user_loans();find_user_loans_request();">

	<%@ include file="/fragments/interface/navbar.jspf"%>
	<%@ include file="/fragments/interface/sidebar.jspf"%>

	<div id='page'>
		<div class='layer-center'>
			<h1 id='emprunts_h1'>Mes emprunts</h1>
			<br><hr><br>
			<h2 id='liste-demandes'>Ma liste de demandes d'objets (en
				attente de confirmation)</h2>
			<div id="found-loans-requests" class="abootsraper"></div>
			<br><br>
			<h2 id='liste'>Ma liste d'objets empruntés</h2>
			<div id="found-loans" class="abootsraper"></div>
			<br><br>
		</div>
		<%@ include file="/fragments/interface/footer.jspf"%>
	</div>
</body>
</html>