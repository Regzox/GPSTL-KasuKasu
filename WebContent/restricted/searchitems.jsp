<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/popup.css" />
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/jmodal.css" />
<link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/bootstrap.min.css">

<script type="text/javascript" src="/KasuKasu/js/utils.js"></script>
<script type="text/javascript" src="/KasuKasu/js/cookies.js"></script>
<script type="text/javascript" src="/KasuKasu/js/inflator/mirror.js"></script>
<script type="text/javascript"
	src="/KasuKasu/js/inflator/views/loaning.js"></script>
<script type="text/javascript"
	src="/KasuKasu/js/inflator/views/items.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-ui.js"></script>

<link type="text/css" href="/KasuKasu/css/jquery-ui.css"
	rel="stylesheet" />

<link type="text/css" href="/KasuKasu/css/jquery-ui.css" rel="stylesheet" />
<script	type="text/javascript" src="/KasuKasu/js/traduction.js"></script>


<script>
	$(document).ready(function() {
		$("#iquery").keyup(function(e) {
			searchItems($("#iquery").val());
		});
	});
</script>

<script type="text/javascript">
$(document).ready(function() {
	trans('searchitems.jsp','titre');
	trans('searchitems.jsp','comment');
	trans('searchitems.jsp','submit');
});
</script>

<title id='titre'>Recherche d'objets</title>
</head>

<body onload="searchMRItems('');">

	<%@ include file="/fragments/interface/navbar.jspf"%>
	<%@ include file="/fragments/interface/sidebar.jspf"%>

	<div id="page">

		<div class='layer-center'>

			<p id='comment' class='capital'>Trouvez des Objets à proximité de
				vos points d'échanges</p>

			<form action="javascript:(function(){return;})()" method="get"
				OnSubmit="javascript:searchItems(this.iquery.value)">

				<div class="wrapper">
					<input type="text" class="form-control" name="iquery" value=""
						id="iquery" autocomplete="off">
				</div>
				<input id="submit" type="submit" class="btn btn-primary btn-block"
					value="Rechercher">
			</form>

			<br>
			<hr>
			<br>

			<div id="found-items" class="abootsraper"></div>
		</div>

		<%@ include file="/fragments/interface/footer.jspf"%>

	</div>
</body>
</html>

