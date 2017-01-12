<%@page import="json.Error"%>
<%@page import="json.Warning"%>
<%@page import="org.json.JSONException"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title id='titre'>Profil</title>

<link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/style.css" />
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/profile.css" />

<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/KasuKasu/js/check-connection.js"></script>
<script type="text/javascript" src="/KasuKasu/js/profile.js"></script>
<script type="text/javascript" src="/KasuKasu/js/translation.js"></script>

</head>
<body onload="trans()">

	<%@ include file="/fragments/sidebar.jspf"%>

	<%@ page import="services.User"%>
	<%@ page import="org.json.JSONObject"%>

	<%
		String id = request.getParameter("id");
		entities.User user = User.getUserById(id);
		JSONObject json = User.getUserImage(user);
		String url = null;
		if (json instanceof Warning)
			System.out.println("WARNING : " + json.getString("warning"));
		else if (json instanceof Error)
			System.out.println("ERROR : " + json.getString("error"));
		else
			url = json.getString("success");
	%>

	<div id="page">
		<div id="profile">
			<table>
				<tr>
					<td colspan="2" id="image-wrapper"><img id="image"
						src="<%=url%>"></img></td>
				</tr>

				<!-- 				<tr class="row"> -->
				<!-- 					<td class="title">Id</td> -->
				<%-- 					<td class="information" id="id"><%= user.getId() %></td> --%>
				<!-- 				</tr> -->
				<tr class="row">
					<td id="prenom" class="title">Prénom</td>
					<td class="information" id="firstname"><%= user.getFirstname() %></td>
				</tr>
				<tr class="row">
					<td id="nom" class="title">Nom</td>
					<td class="information" id="name"><%= user.getName() %></td>
				</tr>
				<tr class="row">
					<td id="mail" class="title">Email</td>
					<td class="information" id="email"><%= user.getEmail() %></td>
				</tr>
				<tr class="row">
					<td id="tel" class="title">Téléphone</td>
					<td class="information" id="phone"><%= user.getPhone() %></td>
				</tr>
			</table>
		</div>
		<br> <br>
		<div id="profile-action">
			<a id="contact" class="contact-user"
				href="http://localhost:8080/KasuKasu/conversation.jsp?uther=<%=user.getId()%>
				&interlocutor=<%=user.getName()%> <%=user.getFirstname()%>">
				Contacter</a>
		</div>
	</div>

</body>
</html>