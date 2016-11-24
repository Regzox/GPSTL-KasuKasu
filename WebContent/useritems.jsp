<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/bootstrap.min.css">
<script type="text/javascript" src="/KasuKasu/js/utils.js"></script>
<script type="text/javascript" src="/KasuKasu/js/items.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/sidebar.css" />

<title>Mes Objets</title>
</head>
<body onload="userItems('')">

	<%@ include file="/fragments/sidebar.jspf"%>

	<div id='page'>
		<div class='layer-center'>
			<p class='capital'>Gerez vos objets</p>

			<form action="javascript:(function(){return;})()" method="get"
				OnSubmit="javascript:userItems(this.iquery.value)">
				<div class="form-group row">
					<div class="col-xs-10">
						<input type="text" class="form-control" name="iquery" id="iquery">
					</div>
				</div>
				<input type="submit" class="btn btn-primary btn-block"
					value="Filtrer">
			</form>

			<br><hr><br>
			
			<h2>Liste de vos objets</h2>
			<div id="found-items" class="abootsraper"></div>
		</div>
	</div>
</body>
</html>

