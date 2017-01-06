<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/bootstrap.min.css">
<script type="text/javascript" src="/KasuKasu/js/utils.js"></script>
<script type="text/javascript" src="/KasuKasu/js/inflator/mirror.js"></script>
<script type="text/javascript"
	src="/KasuKasu/js/inflator/views/groupmembers.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/KasuKasu/js/translation.js"></script>


<script>
	$(document).ready(function() {
		$("#query").keyup(function(e) {
			finduseramongfriends($("#query").val());
		});
	});
</script>

<link rel="stylesheet" type="text/css" href="/KasuKasu/css/sidebar.css" />

<title id="titre">Groupe <%=request.getParameter("gname")%></title>
</head>
<body onload="groupMembers('<%=request.getParameter("gid")%>'); trans();">
	
	<%@ include file="/fragments/sidebar.jspf"%>
	<div id='page'>
		<div class='layer-center'>
			<p id="gest" class='capital'>Gérer le groupe 
				<div class='capital'> <%=request.getParameter("gname")%>
				</div>
				<p id="comment">
					En ajoutant des membres
				</p>
			</p>


			<!-- 			<div id='notifier'>TODO REPLACE BY MODAL</div> -->

			<form action="javascript:(function(){return;})()" method="get"
				OnSubmit="javascript:finduseramongfriends(this.query.value)">

				<div class="form-group row">
					<div class="col-xs-10">
						<input class="form-control" name="query" value="" id="query" autocomplete="off" >
					</div>
				</div>

				<input id = "submit" type="submit" class="btn btn-primary btn-block"
					value="Chercher"> <br>
			</form>

			<div id="found-friends" class="abootsraper"></div>
<<<<<<< HEAD
			<br><br><hr><br>
			<h2 id="liste">Liste des membres du groupe</h2>
=======
			<br>
			<br>
			<hr>
			<br>
			<h2>Liste des membres du groupe</h2>
>>>>>>> df1d59612f6db0e734a164daba1e75c4ae2cc480
			<div id="found-members" class="abootsraper"></div>

		</div>
	</div>
</body>
</html>

