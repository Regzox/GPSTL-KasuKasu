<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/bootstrap.min.css">
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/KasuKasu/js/utils.js"></script>

<script type="text/javascript" src="/KasuKasu/js/inflator/mirror.js"></script>
<script type="text/javascript"
	src="/KasuKasu/js/inflator/views/groups.js"></script>
<script type="text/javascript" src="/KasuKasu/js/traduction.js"></script>

<script type="text/javascript" src="/KasuKasu/js/cookies.js"></script>
<script type="text/javascript" src="/KasuKasu/js/inflator/mirror.js"></script>
<script type="text/javascript"
	src="/KasuKasu/js/inflator/views/groups.js"></script>
<script type="text/javascript" src="/KasuKasu/js/traduction.js"></script>


<script type="text/javascript">
	$(document).ready(function() {
		trans('groups.jsp', 'titre');
		trans('groups.jsp', 'create');
		trans('groups.jsp', 'submit');
	});
</script>
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/jmodal.css" />
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/sidebar.css" />
<link type="text/css" rel="stylesheet"	href="/KasuKasu/css/messenger.css" />
<title id="titre">Mes Groupes</title>
</head>
<body onload="userGroups();">

	<%@ include file="/fragments/interface/navbar.jspf"%>
	<%@ include file="/fragments/interface/sidebar.jspf"%>

	<div id="page">

		<%@ include file="/fragments/interface/menus/myfriends_menu.jspf"%>

		<div class='layer-center'>

			<h1 id="titre" class='capital'>Mes Groupes</h1>
			<hr style="width: 50%; margin-left: 25%">
			<br>
			<h3 id="create" >Créer un nouveau groupe</h3>
			<form action="javascript:(function(){return;})()" method="get"
				OnSubmit="javascript:createGroup(this.gname)">

				<div class="form-group row">
					<input type="text" class="form-control" name="gname" id="gname">
				</div>
<!-- <<<<<<< HEAD -->
				
				<div class="form-group row">
					<input id="submit" type="submit" class="btn btn-primary btn-block"
						value="Créer"> <br>
				</div>
				
				<div id="found-groups" class="abootsraper"></div>

<!-- =======

				<input id="submit" type="submit" class="btn btn-primary btn-block"
					value="Créer"> <br>

				<div style="width:150%; margin-left:10%"  id="found-groups" class="abootsraper"></div>
				<br><br>
>>>>>>> d6b792ba44f43b8b4b06652346ff7a14a4bc11e9 -->
			</form>
		</div>

		<%@ include file="/fragments/interface/footer.jspf"%>

	</div>
</body>
</html>

