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
<script type="text/javascript"
	src="/KasuKasu/js/inflator/views/loaning.js"></script>
<script type="text/javascript" src="/KasuKasu/js/inflator/mirror.js"></script>
<script type="text/javascript"
	src="/KasuKasu/js/inflator/views/items.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/KasuKasu/js/translation.js"></script>

<script>
	$(document).ready(function() {
		$("#iquery").keyup(function(e) {
			userItems($("#iquery").val());
		});
	});
</script>
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/sidebar.css" />

<title id='titre'>Mes Objets</title>
</head>
<body onload="userItems(''); trans();">

	<%@ include file="/fragments/interface/navbar.jspf"%>
	<%@ include file="/fragments/interface/sidebar.jspf"%>

	<div id='page'>
		
		<%@ include file="/fragments/interface/menus/useritems_menu.jspf"%>
	
		<div class='layer-center'>
			<p id='manage' class='capital'>Gerez vos objets</p>

			<form action="javascript:(function(){return;})()" method="get"
				OnSubmit="javascript:filterUserItems(this.iquery.value)">
				<div class="wrapper">
					<input	type="text" 
							class="form-control" 
							name="iquery" 
							value=""
							id="iquery" 
							autocomplete="off">
				</div>
			</form>

			<br>
			<hr>
			<br>

			<h2 id='liste'>Liste de mes objets</h2>
			<div id="found-items" class="abootsraper"></div>
		</div>
		
		<%@ include file="/fragments/interface/footer.jspf"%>
		
	</div>
</body>
</html>

