<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/bootstrap.min.css">
<script type="text/javascript" src="/KasuKasu/js/utils.js"></script>
<script type="text/javascript" src="/KasuKasu/js/inflator/mirror.js"></script>
<script type="text/javascript"
	src="/KasuKasu/js/inflator/views/groupmembers.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/KasuKasu/js/translation.js"></script>
<script type="text/javascript" src="/KasuKasu/js/cookies.js"></script>


<script>
	$(document).ready(function() {
		$("#query").keyup(function(e) {
			finduseramongfriends($("#query").val());
		});
	});
</script>

<script type="text/javascript">
$(document).ready(function() {
	trans('groupmembers.jsp','titre');
	trans('groupmembers.jsp','gest');
	trans('groupmembers.jsp','comment');
	trans('groupmembers.jsp','liste');
	trans('groupmembers.jsp','submit');
});
</script>
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/sidebar.css" />

<title id="titre">Groupe <%=request.getParameter("gname")%></title>
</head>
<body
	onload="groupMembers('<%=request.getParameter("gid")%>'); trans();">

	<%@ include file="/fragments/interface/navbar.jspf"%>
	<%@ include file="/fragments/interface/sidebar.jspf"%>

	<div id='page'>
		<div class='layer-center'>
			<div class='capital'>
				<%=request.getParameter("gname")%>
			</div>
			<p id="gest">Gérer le groupe</p>
			<p id="comment">
				En ajoutant des membres
			</p>



				<!-- 			<div id='notifier'>TODO REPLACE BY MODAL</div> -->

			
			<form action="javascript:(function(){return;})()" method="get"
				OnSubmit="javascript:finduseramongfriends(this.query.value)">

				<div class="form-group row">
					<div class="col-xs-10">
						<input class="form-control" name="query" value="" id="query"
							autocomplete="off">
					</div>
				</div>

				<input id="submit" type="submit" class="btn btn-primary btn-block"
					value="Chercher"> <br>
			</form>

			<div id="found-friends" class="abootsraper"></div>

			<br> <br>
			<hr>
			<br>
			<h2 id="liste">Liste des membres du groupe</h2>

			<div id="found-members" class="abootsraper"></div>
		</div>
		
		<%@ include file="/fragments/interface/footer.jspf"%>
		
	</div>
</body>
</html>

