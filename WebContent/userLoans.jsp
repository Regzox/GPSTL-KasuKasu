<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/bootstrap.min.css">
<script type="text/javascript" src="/KasuKasu/js/utils.js"></script>
<script type="text/javascript" src="/KasuKasu/js/inflator/views/loaning.js"></script>
<script type="text/javascript" src="/KasuKasu/js/inflator/mirror.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/sidebar.css" />
<title>Ma liste d'objets emprunt�s</title>
</head>
<body onload="javascript:find_user_loans()">
<%@ include file="/fragments/sidebar.jspf"%>
<div id='page'>
		<div class='layer-center'>
<!-- 			<p class='capital'>Rechercher un objet</p> -->

<!-- 			<form action="javascript:(function(){return;})()" method="get" -->
<!-- 				OnSubmit="javascript:find_user_loans(this)"> -->
<!-- 				<div class="form-group row"> -->
<!-- 					<div class="col-xs-10"> -->
<!-- 						<input type="text" class="form-control" name="iquery" id="iquery"> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<input type="submit" class="btn btn-primary btn-block" -->
<!-- 					value="Valider"> -->
<!-- 			</form> -->

<!-- 			<br><hr><br> -->
			
			<h2>Liste de vos objets emprunt�s</h2>
			<div id="found-loans" class="abootsraper"></div>
		</div>
	</div>
</body>
</html>