<%@page import="json.Success"%>
<%@page import="json.Error"%>
<%@page import="json.Warning"%>
<%@page import="org.json.JSONException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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

	<%@ include file="/fragments/interface/navbar.jspf"%>
	<%@ include file="/fragments/interface/sidebar.jspf"%>

	<%@ page import="services.User"%>
	<%@ page import="org.json.JSONObject"%>
	<%@ page import="enumerations.Status"%>
	<%@ page import="fr.upmc.file.Resource"%>

	<%
		Resource resource = ((Resource) this.getServletContext().getAttribute("resource"));
		boolean isAdmin = false;
		try {
			isAdmin = ((String) session.getAttribute("isAdmin")).compareTo("true") == 0;
		} catch (Exception e) {
			isAdmin = false;
		}

		String id = request.getParameter("id");
		entities.User user = null;
		user = User.getUserById(id);
		JSONObject json = null; 
		if (user !=null )
			User.getUserImage(user);
		String url = null;
		if (json instanceof Warning)
			System.out.println("WARNING : " + json.getString("warning"));
		else if (json instanceof Error)
			System.out.println("ERROR : " + json.getString("error"));
		else if (json instanceof Success)
			url = json.getString("success");
		else
			url = "/KasuKasu/data/profile-icon.png";
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
					<td class="information" id="firstname"><%=user.getFirstname()%></td>
				</tr>
				<tr class="row">
					<td id="nom" class="title">Nom</td>
					<td class="information" id="name"><%=user.getName()%></td>
				</tr>
				<tr class="row">
					<td id="mail" class="title">Email</td>
					<td class="information" id="email"><%=user.getEmail()%></td>
				</tr>
				<tr class="row">
					<td id="tel" class="title">Téléphone</td>
					<td class="information" id="phone"><%=user.getPhone()%></td>
				</tr>
				<%
					if (isAdmin) {
						String status = User.getStatus(id).toString();
				%>
				<tr class="row">
					<td id="admin" class="title"><div id="status">STATUS :</div> <%=status%></td> 

					<%
						switch (status) {
							case "BANNED":
					%>
					<td class="information" id="admin-action">...</td>
					<%
						break;
							case "FROZEN":
					%>
					<td class="information" id="admin-action"><a class="btn btn-success btn-block btn-sm"
						href="<%= resource.absolutePath("AdminUserManagementServlet") %>?action=2&userId=<%=id%>"> Unfreeze
					</a> <a class="btn btn-danger btn-block btn-sm" href="<%= resource.absolutePath("AdminUserManagementServlet") %>?action=3&userId=<%=id%>"> Ban /!\
							REVERT IMPOSSIBLE /!\ </a></td>

					<%
						break;
							default:
					%>
					<td class="information" id="admin-action"><a class="btn btn-danger btn-block btn-sm"
						href="<%= resource.absolutePath("AdminUserManagementServlet") %>?action=1&userId=<%=id%>"> Freeze </a> <a class="btn btn-danger btn-block btn-sm"
						href="<%= resource.absolutePath("AdminUserManagementServlet") %>?action=3&userId=<%=id%>"> Ban /!\
							REVERT IMPOSSIBLE /!\ </a></td>
					<%
						break;

							}
					%>

				</tr>
				<%
					}
				%>

			</table>
		</div>
		<br> <br>
		<div id="profile-action">
			<a id="contact" class="contact-user"
				href="<%= resource.absolutePath("conversation_jsp") %>?uther=<%=user.getId()%>
				&interlocutor=<%=user.getName()%> <%=user.getFirstname()%>">
				Contacter</a>
		</div>
		
		<%@ include file="/fragments/interface/footer.jspf"%>
		
	</div>

</body>
</html>