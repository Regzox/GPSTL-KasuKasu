<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link type="text/css" rel="stylesheet" href="/KasuKasu/css/sidebar.css" />
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />

<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript">

	
		
	var doGet = function (containerId) {
		$.get("/KasuKasu/profile", {
			email : "d.radeau@gmail.com" // A remplacer par un emal dans votre base de données
		})
		.done(function (data) {
			//$(containerId).html($(containerId).html() + "<br/>" + "Resultats disponibles : <br/>");
			//document.body.innerHTML += "<br/>" + "Resultats disponibles : <br/>";
			var json = $.parseJSON(data);
			
			console.log(json);
			
			$.each(json.success, function(user, profile) {
				$.each(profile, function (field, value) {
					//document.body.innerHTML += field + ":" + value + "<br/>";
					$(containerId).html($(containerId).html() + "<br/>" +  field + ":" + value + "<br/>");
				})
			});
		});
	}
	
	function session_id() {
	    return /SESS\w*ID=([^;]+)/i.test(document.cookie) ? RegExp.$1 : false;
	}
	
	
	//window.onload = doGet('#page');
	//console.log("ssi : " + session_id());
	
</script>

</head>
<body style="margin: 0%; padding: 0%;">

	<div id="page" class="show" style="width: 87.5%;
	float: right;">
		<!-- Page de test de la fonctionnalité afficher profile.
		<button onclick="doGet(page)">Test</button> -->
	</div>

	
	
	<%@ include file="/fragments/sidebar.jspf" %>

</body>
</html>