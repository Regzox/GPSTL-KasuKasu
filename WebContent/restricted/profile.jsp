<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mon profil</title>

<link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/style.css" />
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/profile.css" />

<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/KasuKasu/js/check-connection.js"></script>
<script type="text/javascript" src="/KasuKasu/js/profile.js"></script>

<script type="text/javascript">
		
	/* var getMyProfile = function (container_id) {
		$.get("/KasuKasu/profile")
		.done(function (data) {
			var json = $.parseJSON(data),
			 	page = $(container_id),
			 	card = $(document.createElement("div")),
			 	my_id = $(document.createElement("div")),
			 	my_email = $(document.createElement("div")),
			 	my_firstname = $(document.createElement("div")),
			 	my_name = $(document.createElement("div")),
			 	my_phone = $(document.createElement("div")),
			 	my_image = $(document.createElement("div"));
						
			my_id.append(json.success.user0.id);
			my_email.append(json.success.user0.email);
			my_firstname.append(json.success.user0.firstname);
			my_name.append(json.success.user0.name);
			my_phone.append(json.success.user0.phone);
			
			$(card).attr("id", "card");
			$(my_image).attr("id", "image");
			
			card.append(my_image, my_id, my_email, my_firstname, my_name, my_phone);
			card.addClass("layer-left");
			card.addClass("show");
			
			page.html(card);
			
		});
	}
	
	var getProfileById = function (container_id, userId) {
		$.get("/KasuKasu/profile?id=" + userId)
		.done(function (data) {
			var json = $.parseJSON(data),
			 	page = $(container_id),
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
			card.addClass("layer-left");
			
			page.html(card);
			
		});
	}
	
	var getMyImage = function (container_id) {
		$.get("/KasuKasu/imagesservlet")
		.done(function (data) {
			var json = $.parseJSON(data),
		 		page = $(container_id);
			
			page.html(page.html() + "<img width='300px' height='300px' src='" + json.success + "'></img>")
		});
	}
	
	getMyProfile('#page');
	getMyImage('#image'); */
	
	
	

	window.onload = function () {
		fillMyProfile();
	}
			
</script>

</head>
<body>

	<%@ include file="/fragments/sidebar.jspf"%>

	<div id="page">




		<%@ include file="/fragments/profile.jspf"%>
		<br /> 
		<a href='<%= Url.MODIFY_USER.value() %>'> Modifier mes informations</a> 
		<br /> 
		<a href='<%= Url.UPLOAD.value() %>'> 	Changer d'image</a>





	</div>




</body>
</html>