<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/jmodal.css" />
<script type="text/javascript" src="/KasuKasu/js/utils.js"></script>
<script type="text/javascript" src="/KasuKasu/js/inflator/views/loaning.js"></script>
<script type="text/javascript" src="/KasuKasu/js/inflator/mirror.js"></script>
<script type="text/javascript" src="/KasuKasu/js/inflator/views/items.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/KasuKasu/js/traduction.js"></script>
<script type="text/javascript" src="/KasuKasu/js/cookies.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	trans('applicants.jsp','titre');
	trans('applicants.jsp','applicants');
});
</script>
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/applicants.css"/>
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/sidebar.css" />

<title id="titre">Demandes d'emprunt</title>
</head>
<body onload="user_items_applicants()">

	<%@ include file="/fragments/interface/navbar.jspf"%>
	<%@ include file="/fragments/interface/sidebar.jspf"%>

	<div id='page'>
		<div class='layer-center'>
			<p id="applicants" class='capital'> Liste des demandes d'emprunt </p>
			<hr><br>
			<div id="item-applicants" class="abootsraper"></div>
		</div>
		<%@ include file="/fragments/interface/footer.jspf"%>
	</div>
	
</body>
</html>

