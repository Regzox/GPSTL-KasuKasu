<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--  <link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" /> -->
<link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/bootstrap.min.css">

<script type="text/javascript" src="/KasuKasu/js/friendsandrequest.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script	type="text/javascript" src="/KasuKasu/js/traduction.js"></script>
<script type="text/javascript" src="/KasuKasu/js/cookies.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	trans('pendingrequests.jsp','titre');
});
</script>
<title id='titre'>Requêtes d'amis</title>
</head>
 <body onload="retrieveRequestsFunc();">
	
	<%@ include file="/fragments/interface/navbar.jspf"%>
	<%@ include file="/fragments/interface/sidebar.jspf"%>

	<div id='page'>
		<div class='layer-center'>
			<div id='notifier'></div>
		</div>
		
		<%@ include file="/fragments/interface/footer.jspf"%>
		
	</div>
</body>
</html>