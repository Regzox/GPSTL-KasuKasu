<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/popup.css" />
<link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/bootstrap.min.css">
<script type="text/javascript" src="/KasuKasu/js/utils.js"></script>
<script type="text/javascript" src="/KasuKasu/js/inflator/mirror.js"></script>
<script type="text/javascript"
	src="/KasuKasu/js/inflator/views/loaning.js"></script>
<script type="text/javascript"
	src="/KasuKasu/js/inflator/views/items.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-ui.js"></script>
<link type="text/css" href="/KasuKasu/css/jquery-ui.css" rel="stylesheet" />
<script	type="text/javascript" src="/KasuKasu/js/translation.js"></script>


<script>
	$(document).ready(function() {
		$("#iquery").keyup(function(e) {
			searchItems($("#iquery").val());
		});
	});
</script>
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/sidebar.css" />
<title id='titre'>Recherche d'objets</title>
</head>

<body onload="searchMRItems(''); trans();">

	<%@ include file="/fragments/sidebar.jspf"%>

	<div id="page">
		<div class='layer-center'>

			<p id='p' class='capital'>Trouvez des Objets à proximité de vos points
				d'échanges</p>

			<form action="javascript:(function(){return;})()" method="get"
				OnSubmit="javascript:searchItems(this.iquery.value)">
				<div class="form-group row">
					<div class="col-xs-10">
						<input type="text" class="form-control" name="iquery" value=""
							id="iquery" autocomplete="off">
					</div>
				</div>
				<input id="submit" type="submit" class="btn btn-primary btn-block">
			</form>

			<br>
			<hr>
			<br>

			<div id="found-items" class="abootsraper"></div>
		</div>
	</div>
</body>
</html>

