<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="/KasuKasu/css/style.css" />

<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/KasuKasu/js/check-connection.js"></script>
<script type="text/javascript">
		
	/* var doGet = function (containerId) {
		$.get("/KasuKasu/profile", {
			email : "d.radeau@gmail.com" // A remplacer par un emal dans votre base de données
		})
		.done(function (data) {
			var json = $.parseJSON(data);
			
			console.log(json);
			
			$.each(json.success, function(user, profile) {
				$.each(profile, function (field, value) {
					$(containerId).html($(containerId).html() + "<br/>" +  field + ":" + value + "<br/>");
				})
			});
		});
	} */
	
	var getMyCard = function (container_id) {
		$.get("/KasuKasu/profile")
		.done(function (data) {
			var json = $.parseJSON(data),
			 	page = $(container_id);
			 	card = $(document.createElement("div")),
			 	my_id = $(document.createElement("div")),
			 	my_email = $(document.createElement("div")),
			 	my_firstname = $(document.createElement("div")),
			 	my_name = $(document.createElement("div")),
			 	my_phone = $(document.createElement("div"));
						
			my_id.append(json.success.user0.id);
			my_email.append(json.success.user0.email);
			my_firstname.append(json.success.user0.firstname);
			my_name.append(json.success.user0.name);
			my_phone.append(json.success.user0.phone);
			
			card.append(my_id, my_email, my_firstname, my_name, my_phone);
			card.addClass("layer-center");
			
			page.html(card);
			
		});
	}
	
	var getCardById = function (container_id, userId) {
		$.get("/KasuKasu/profile?id=" + userId)
		.done(function (data) {
			var json = $.parseJSON(data),
			 	page = $(container_id);
			 	card = $(document.createElement("div")),
			 	my_id = $(document.createElement("div")),
			 	my_email = $(document.createElement("div")),
			 	my_firstname = $(document.createElement("div")),
			 	my_name = $(document.createElement("div")),
			 	my_phone = $(document.createElement("div"));
						
			my_id.append(json.success.user0.id);
			my_email.append(json.success.user0.email);
			my_firstname.append(json.success.user0.firstname);
			my_name.append(json.success.user0.name);
			my_phone.append(json.success.user0.phone);
			
			card.append(my_id, my_email, my_firstname, my_name, my_phone);
			card.addClass("layer-center");
			
			page.html(card);
			
		});
	}

	// Requête GET asynchrone pour charger les données dans le conteneur 'page'
	getMyCard('#page');
	
</script>

</head>
<body style="margin: 0%; padding: 0%;">

	<%@ include file="/fragments/sidebar.jspf" %>

	<div id="page">
	</div>
	
</body>
</html>