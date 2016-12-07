<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/bootstrap.min.css">
<script type="text/javascript" src="/KasuKasu/js/utils.js"></script>
<script type="text/javascript" src="/KasuKasu/js/inflator/views/loaning.js"></script>
<script type="text/javascript" src="/KasuKasu/js/inflator/mirror.js"></script>
<script type="text/javascript" src="/KasuKasu/js/inflator/views/items.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/sidebar.css" />

<title>Objet <%=request.getParameter("title")%></title>
</head>
<body onload="getItem('<%=request.getParameter("id")%>')">

	<%@ include file="/fragments/sidebar.jspf"%>

	<div id='page'>
		<div class='layer-center'>
			<p class='capital'>Objet <%=request.getParameter("title")%></p>

			<br><hr><br>
			
			<div id="found-items" class="abootsraper"></div>
			
		</div>
	</div>
</body>
</html>

