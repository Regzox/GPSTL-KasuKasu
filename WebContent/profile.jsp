<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>


<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript">
		
	var doGet = function () {
		$.get("/KasuKasu/profile", {
			email : "d.radeau@gmail.com" // A remplacer par un emal dans votre base de données
		})
		.done(function (data) {
			document.body.innerHTML += "<br/>" + "Resultats disponibles : <br/>";
			var json = $.parseJSON(data);
			
			console.log(json);
			
			$.each(json.success, function(user, profile) {
				$.each(profile, function (field, value) {
					document.body.innerHTML += field + ":" + value + "<br/>";
				})
			});
		});
	}
	
</script>

</head>
<body>

	Page de test de la fonctionnalité afficher profile.

	<button onclick="doGet()">TEST</button>

</body>
</html>