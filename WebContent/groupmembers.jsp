<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/bootstrap.min.css">
<script type="text/javascript" src="/KasuKasu/js/utils.js"></script>
<script type="text/javascript" src="/KasuKasu/js/groupmembers.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<title>Groupe <%=request.getParameter("gname")%></title>
</head>
<body onload="groupMembers('<%=request.getParameter("gid")%>')">
	

	
	<div id='page'>
		<div class='layer-center'>
			<p class='capital'>Gerez votre groupes<%=request.getParameter("gname")%></p>

			<form action="javascript:(function(){return;})()" method="get"
				OnSubmit="javascript:finduser(this)">

				<div class="form-group row">
					<label for="email" class="col-xs-4 col-form-label">Rechercher
						par </label>
					<div class="col-xs-4">
						<SELECT name="search" size="1" class="form-control">
							<OPTION value="nom">Nom</OPTION>
							<OPTION value="prenom">Prénom</OPTION>
							<OPTION value="email">Email</OPTION>
							<OPTION value="numero">Numéro</OPTION>
						</SELECT>
					</div>
				</div>

				<div class="form-group row">

					<div class="col-xs-10">
						<input class="form-control" name="value" value="" id="value">
					</div>
				</div>

				<input type="submit" class="btn btn-primary btn-block"
					value="Chercher"> <br>
			</form>

			<div id='notifier'></div>
			<br><br><hr><br>
			<h2>Liste des membres du groupe</h2>
			<div id="found-members" class="abootsraper"></div>
			
		</div>
	</div>
</body>
</html>

