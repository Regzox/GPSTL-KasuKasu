<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>

	<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
	<link type="text/css" rel="stylesheet" href="/KasuKasu/css/module.css" />
	<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="/KasuKasu/js/tether.min.js"></script>

	<script type="text/ecmascript" src="/KasuKasu/js/classes/Html.js"></script>		
	<script type="text/ecmascript" src="/KasuKasu/js/classes/Star.js"></script>	
    <script type="text/ecmascript" src="/KasuKasu/js/classes/Profile.js"></script>
    <script type="text/ecmascript" src="/KasuKasu/js/classes/StarBar.js"></script>
    <script type="text/ecmascript" src="/KasuKasu/js/classes/Comment.js"></script>
    <script type="text/ecmascript" src="/KasuKasu/js/classes/Button.js"></script>
    <script type="text/ecmascript" src="/KasuKasu/js/classes/Choice.js"></script>
    <script type="text/ecmascript" src="/KasuKasu/js/classes/LoanMark.js"></script>
    <script type="text/ecmascript" src="/KasuKasu/js/classes/Notifier.js"></script>
	
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
				var page = document.getElementById("page");
				var notifier = new Notifier("notifier");
				
				jQuery(page).append(notifier._dom);
				
				for (var i = 0; i < array.length; i++) {
					//var div = document.createElement("div");
					
					let loanMark = new LoanMark("loan-mark-" + i);
					
					$(loanMark.cancel.dom).click (function() {
                        $(loanMark.dom).remove();
                    });
					
					$(loanMark.choice.cancel.dom).click( function() {
                        loanMark.comment.clear();
                        loanMark.starBar.reset();
                    });
					
					$(loanMark.choice.validate.dom).click( function() {
                        loanMark.comment.clear();
                        loanMark.starBar.reset();
                    });
					
					loanMark.profile.image(array[i].senderImage[0]);
					loanMark.profile.firstname(array[i].senderFirstname[0]);
					loanMark.profile.lastname(array[i].senderLastname[0]);
					
					jQuery(page).append(loanMark.dom);
					
					/* div.innerHTML = 
					"<div>" + array[i].senderId[0] + "</div>" +
					"<div>" + array[i].senderFirstname[0] + "</div>" +
					"<div>" + array[i].senderLastname[0] + "</div>" +
					"<div>" + array[i].senderImage[0] + "</div>" +
					"<div>" + array[i].receiverId[0] + "</div>" +
					"<div>" + array[i].loanId[0] + "</div>" +
					"<div>" + array[i].itemId[0] + "</div>" +
					"<div>" + array[i].itemTitle[0] + "</div>" +
					"<div>" + array[i].itemDescription[0] + "</div>"; */
					/* page.appendChild(div);
					console.log(element); */
				}
				
				jQuery(array).each( function(index, element) {
					
				});
				
				document.body.appendChild(page);
			});
		};
		
		</script>
		
	</div>
	
	<%@ include file="/fragments/footer.jspf"%>
</body>
</html>