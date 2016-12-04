<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/bootstrap.min.css">
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/KasuKasu/js/utils.js"></script>
<script type="text/javascript" src="/KasuKasu/js/groups.js"></script>

<link rel="stylesheet" type="text/css" href="/KasuKasu/css/sidebar.css" />

<title>Mes Groupes</title>
</head>
<body onload="userGroups()">

	<%@ include file="/fragments/sidebar.jspf"%>
	<div id="page">

		<div class='layer-center'>

			<p class='capital'>Creez un nouveau groupe</p>

			<div id='notifier'></div>
			<br>
			<form action="javascript:(function(){return;})()" method="get"
				OnSubmit="javascript:createGroup(this.gname)">

				<div class="form-group row">
					<div class="col-xs-10">
						<input type="text" class="form-control" name="gname" id="gname">
					</div>
				</div>

				<input type="submit" class="btn btn-primary btn-block"
					value="Creer un nouveau groupe"> <br>

				<div id="found-groups" class="abootsraper"></div>

			</form>
		</div>

	</div>
</body>
</html>

