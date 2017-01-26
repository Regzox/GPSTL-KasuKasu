<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title id='titre1'>Conversation with <%=request.getParameter("interlocutor")%></title>
<script type="text/javascript" src="/KasuKasu/js/utils.js"></script>
<script type="text/javascript" src="/KasuKasu/js/cookies.js"></script>
<script type="text/javascript"
	src="/KasuKasu/js/inflator/views/messenger.js"></script>
<script type="text/javascript" src="/KasuKasu/js/inflator/mirror.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="/KasuKasu/js/traduction.js"></script>


<script type="text/javascript">
	$(document).ready(function() {
		trans('conversation.jsp', 'titre1');
		trans('conversation.jsp', 'titre');
	});
</script>

<link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/messenger.css" />
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/sidebar.css" />
</head>
<body id="gc-search"
	onload=" conversation('<%=request.getParameter("uther")%>')">

	<%@ include file="/fragments/interface/navbar.jspf"%>
	<%@ include file="/fragments/interface/sidebar.jspf"%>

	<div id="page">
		<h1 align="center" class="title">
			<p id="titre">Conversation avec</p>
			<%=request.getParameter("interlocutor")%>
		</h1>
		<br>
		<div style="height:380px;overflow-y:scroll;" id="found-messages" align="center"></div>
		<br>
		<div class="new_message-content-wrapper" align="center"
			style="margin-bottom:20px;">
			<div id="new_message-form-container">
				<form id="new_message-form" name="new_messageForm" method="post"
					action="javascript:(function(){return;})()"
					onsubmit="new_private_message('<%=request.getParameter("uther")%>'
			  ,this.msg.value)"
					autocomplete="off">
					<div class="input-wrapper">
						<input id="new_message-input" type="text" name="msg"
							autocomplete="off"
							placeholder="Ecrivez un nouveau message à <%=request.getParameter("interlocutor")%>"
							style="height: 50px; width: 60%; padding: 12px 20px; margin: 8px 0; display: inline-block; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box;"
							size="100%" /> <input type="submit" value="Envoyer"
							class="btn btn-primary btn-xs">
					</div>
				</form>
			</div>
		</div>
		<%@ include file="/fragments/interface/footer.jspf"%>
	</div>

</body>
</html>