<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Messenger</title>
<link rel="stylesheet" href="css/messenger.css" type="text/css" />
<script type="text/javascript" src="js/kami/objects/messenger.js"></script>
<script type="text/javascript" src="js/kami/kamisama.js"></script>
<script type="text/javascript" src="js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="js/wtf.js"></script>
<script type="text/javascript" src="js/processors.js"></script>
<script type="text/javascript" src="js/guardian.js"></script>
<script type="text/javascript" src="js/cookies.js"></script>
<script type="text/javascript" src="js/router.js"></script>



<!-- Website Font style -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">

<!-- Google Fonts -->
<link href='https://fonts.googleapis.com/css?family=Passion+One'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Oxygen'
	rel='stylesheet' type='text/css'>


<!--********** BOOTSTRAP **********-->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" type="text/css"
	href="bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" />
<!-- Optional theme -->
<link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css" />
<!-- Latest compiled and minified JavaScript -->
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.js"></script>
</head>
<body id="gc-search" onload="interlocutors()">
	<!-- Note: Il vaut mieux utiliser une structuration selon le flux naturel : (div/span)
	(Comportement par défaut des navigateurs), 
	qu'une structuration par tableau (<table>)(peu flexible & dificilement portable)-->
	<div id="errFormContainer"></div>
	<div id="notifier" align="center"></div>

	<jsp:include page="profile_sidebar.jsp" />

	<div class="main-login main-center">

		<h1 class="title" align="center">All your conversations</h1>
		<br>
		<div id="found-speakers" align="center" class="result_writable_zone"></div>
		<br>
	</div>
</body>
</html>