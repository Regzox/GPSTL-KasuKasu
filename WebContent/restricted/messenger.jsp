<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title id='titre1'>Conversations</title>
<script type="text/javascript" src="/KasuKasu/js/utils.js"></script>
<script type="text/javascript" src="/KasuKasu/js/cookies.js"></script>
<script type="text/javascript"
	src="/KasuKasu/js/inflator/views/messenger.js"></script>
<script type="text/javascript" src="/KasuKasu/js/inflator/mirror.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="/KasuKasu/js/traduction.js"></script>


<script type="text/javascript">
	$(document).ready(function() {
		trans('messenger.jsp', 'titre1');
		trans('messenger.jsp', 'titre');
	});
</script>
<script>
	$(document).ready(function() {
		$("#query").keyup(function(e) {
			finduser($("#query").val());
		});
	});
</script>
<script type="text/javascript" src="/KasuKasu/js/finduser.js"></script>
<link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/messenger.css" />
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/sidebar.css" />
</head>
<body id="gc-search" onload="interlocutors()">

	<%@ include file="/fragments/interface/navbar.jspf"%>
	<%@ include file="/fragments/interface/sidebar.jspf"%>

	<div id="page">
		<div class='layer-center'>
			<h1 id="titre" class="title" align="center">Toutes mes
				conversations</h1>
			<br><hr style="width:50%; margin-left:25%"><br>

			<div style="width: 80%; margin-left: 10%" type="text">
				<form action="javascript:(function(){return;})()" method="get"
					OnSubmit="javascript:finduser(this.query.value)">
					<div class="wrapper">
						<input class="form-control" name="query" value="" id="query"
							autocomplete="off">
					</div>
				</form>
				<div id='notifier'></div>
			</div>
			

			<div id="found-speakers" align="center" class="result_writable_zone"></div>
		</div>
		<%@ include file="/fragments/interface/footer.jspf"%>
	</div>
</body>
</html>