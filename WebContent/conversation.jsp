<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Conversation with <%=request.getParameter("interlocutor")%></title>
<link rel="stylesheet" href="css/messenger.css" type="text/css" />
<script type="text/javascript" src="js/inflator/views/messenger.js"></script>
<script type="text/javascript" src="js/inflator/mirror.js"></script>
<script type="text/javascript" src="js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
</head>
<body id="gc-search"
	onload="conversation('<%=request.getParameter("uther")%>')">

	<h1 align="center" class="title">
		Conversation with @<%=request.getParameter("interlocutor")%>
	</h1>
	<br>
	<div id="found-messages" style="max-height: 500px; overflow: auto"
		align="center"></div>
	<br>
	<div class="new_message-content-wrapper" align="center">
		<div id="new_message-form-container">
			<form id="new_message-form" name="new_messageForm" method="post"
				action="javascript:(function(){return;})()"
				onsubmit="new_private_message('<%=request.getParameter("uther")%>'
			  ,this.msg.value)"
				autocomplete="on">
				<div class="input-wrapper">
					<input id="new_message-input" type="text" name="msg"
						placeholder="Write a new message here to @<%=request.getParameter("username")%>."
						style="height: 25px;" size="100%" />
				</div>
			</form>
		</div>
	</div>
</body>
</html>