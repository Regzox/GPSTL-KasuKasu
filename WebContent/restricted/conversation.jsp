<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Conversation with <%=request.getParameter("interlocutor")%></title>
<script type="text/javascript" src="/KasuKasu/js/utils.js"></script>
<script type="text/javascript" src="/KasuKasu/js/inflator/views/messenger.js"></script>
<script type="text/javascript" src="/KasuKasu/js/inflator/mirror.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="/KasuKasu/js/translation.js"></script>

<link rel="stylesheet" type="text/css" href="/KasuKasu/css/sidebar.css" />
</head>
<body id="gc-search"
	onload="trans(); conversation('<%=request.getParameter("uther")%>')">

	<%@ include file="/fragments/sidebar.jspf"%>

	<h1  align="center" class="title">
		<p id="titre">
		Conversation avec
		</p>
		<%=request.getParameter("interlocutor")%>
	</h1>
	<br>
	<div id="found-messages" align="center"></div>
	<br>
	<div class="new_message-content-wrapper" align="center">
		<div id="new_message-form-container">
			<form id="new_message-form" name="new_messageForm" method="post"
				action="javascript:(function(){return;})()"
				onsubmit="new_private_message('<%=request.getParameter("uther")%>'
			  ,this.msg.value)"
				autocomplete="on">
				<div class="input-wrapper">
					<input id="new_message-input" type="text" name="msg" autocomplete="off" 
						placeholder="Ecrivez un nouveau message a <%=request.getParameter("interlocutor")%>"
						style="height: 25px;" size="100%" />
				</div>
			</form>
		</div>
	</div>
</body>
</html>