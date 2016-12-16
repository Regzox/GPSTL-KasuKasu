<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>

	<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
	<link type="text/css" rel="stylesheet" href="/KasuKasu/css/bootstrap.min.css">
	<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="/KasuKasu/js/tether.min.js"></script>
	<script	type="text/javascript" src="/KasuKasu/js/bootstrap.min.js"></script>
</head>
<body>
	<%@ include file="/fragments/sidebar.jspf"%>
	
	<div id="page">
		
		<script type="text/javascript">
		
		window.onload = function () {
			(jQuery)
			.get("/KasuKasu/evaluation", {
				resource : "request",
				action : "list"
			})
			.done(function (data) {
				console.log(data);
				console.log(jQuery.parseJSON(data).success);
				
				var array = jQuery.parseJSON(data).success;
				
				jQuery(array).each( function(index, element) {
					document.write("<div>" + index + " : " + element + "</div>");
					console.log(element);
				});
			});
		};
		
		</script>
		
	</div>
	
	<%@ include file="/fragments/footer.jspf"%>
</body>
</html>