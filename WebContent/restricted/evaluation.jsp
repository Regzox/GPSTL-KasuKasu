<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Evaluations</title>

<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/module.css" />
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/profile.css" />
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/evaluation.css" />
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/evaluation-request-notification.css" />
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/evaluation-response-notification.css" />

<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="/KasuKasu/js/tether.min.js"></script>

<script type="text/ecmascript" src="/KasuKasu/js/classes/Element.js"></script>
<script type="text/ecmascript" src="/KasuKasu/js/classes/Html.js"></script>
<script type="text/ecmascript" src="/KasuKasu/js/classes/Star.js"></script>
<script type="text/ecmascript" src="/KasuKasu/js/classes/Profile.js"></script>
<script type="text/ecmascript" src="/KasuKasu/js/classes/StarBar.js"></script>
<script type="text/ecmascript" src="/KasuKasu/js/classes/Comment.js"></script>
<script type="text/ecmascript" src="/KasuKasu/js/classes/Button.js"></script>
<script type="text/ecmascript" src="/KasuKasu/js/classes/Choice.js"></script>
<script type="text/ecmascript" src="/KasuKasu/js/classes/ItemWrapper.js"></script>
<script type="text/ecmascript" src="/KasuKasu/js/classes/EvaluationWrapper.js"></script>
<script type="text/ecmascript" src="/KasuKasu/js/classes/EvaluationRequest.js"></script>
<script type="text/ecmascript" src="/KasuKasu/js/classes/EvaluationResponse.js"></script>
<script type="text/ecmascript" src="/KasuKasu/js/classes/EvaluationRequestNotification.js"></script>
<script type="text/ecmascript" src="/KasuKasu/js/classes/EvaluationResponseNotification.js"></script>
<script type="text/ecmascript" src="/KasuKasu/js/classes/Notifier.js"></script>
<script type="text/ecmascript" src="/KasuKasu/js/translation.js"></script>

</head>
<body>
	<%@ include file="/fragments/sidebar.jspf"%>

	<div id="page">

		<script type="text/javascript">
		trans();
		bool=0;
		if(document.cookie.search("lang=en")!=-1)
				bool=1;
			else
				if(document.cookie.search("lang=fr")!=-1)
					bool=0;
		var notifier = undefined;
		
		function loadEvaluationRequestNotifications () {
			(jQuery)	.get("/KasuKasu/evaluation", {
							resource : "request",
							action : "list"
						})
						
						.done(function (data) {
							
							if (jQuery.parseJSON(data).success === undefined) {
								console.log(jQuery.parseJSON(data).warning);
								return;
							}
							
							console.log(jQuery.parseJSON(data).success);
							
							let array = jQuery.parseJSON(data).success;
							var page = document.getElementById("page");
							var notifier = new Notifier("notifier");
							
							jQuery(page).append(notifier._dom);
							
							for (var i = 0; i < array.length; i++) 
							{
								let erqn = new EvaluationRequestNotification("erqn-" + i);
								erqn.data = array[i];
								
								$(erqn.cancel.dom).click (function() {
			                        $(erqn.dom).remove();
			                        $.post("/KasuKasu/evaluation", {
			                        	resource : "request",
			                        	action : "remove",
			                        	evaluation_request_id : erqn.data.requestId
			                        });
			                    });
								
								$(erqn.evaluation.choice.cancel.dom).click( function() {
									erqn.comment.clear();
									erqn.starBar.reset();
			                    });
								
								$(erqn.evaluation.choice.validate.dom).click( function() {
									var evaluation = {
				                    	resource : "evaluation",
				                        action : "insert",
										sender_id : erqn.data.senderId,
										receiver_id : erqn.data.receiverId,
										loan_id : erqn.data.loanId,
										comment : erqn.evaluation.evaluationWrapper.comment(),
										mark : erqn.evaluation.evaluationWrapper.mark
				                  	};
									
									$	.post("/KasuKasu/evaluation", evaluation) // Insertion de l'évaluation
										.done(function (data) {
											var response = {
						                    	resource : "response",
						                        action : "insert",
						                     	// Inversion du jeu de envoyeur/receveur
						                        sender_id : erqn.data.receiverId,
						                        receiver_id : erqn.data.senderId,
												loan_id : erqn.data.loanId,
						                  	};
											
											$	.post("/KasuKasu/evaluation", response) // Insertion de la réponse
												.done(function (data) {
													var request = {
								                    	resource : "request",
								                        action : "remove",
														evaluation_request_id : erqn.data.requestId
								                  	};
													
													console.log(request);
													$	.post("/KasuKasu/evaluation", request) // Suppression de la requête
														.done(function (data) {
															(function () {$(erqn.dom).remove();});
														});
												});
										});
								});
					
								/**
									Paramétrage du rendu la requête notification
								**/
													
								erqn.profile.image(array[i].senderImage);
								erqn.profile.imageDimensions(150, 150);
						        erqn.profile.idLabel("Id");
						        erqn.profile.id(array[i].senderId);
						        if(bool==0)
									erqn.profile.firstnameLabel("Prénom");
						        if(bool==1)
									erqn.profile.firstnameLabel("First name");
								erqn.profile.firstname(array[i].senderFirstname);
								if(bool==0)
									erqn.profile.lastnameLabel("Nom");
								if(bool==1)
									erqn.profile.lastnameLabel("Last name");
								erqn.profile.lastname(array[i].senderLastname);
								
						        erqn.evaluation.itemWrapper.idLabel("Id");
						        erqn.evaluation.itemWrapper.id(array[i].itemId);
						        if(bool==0)
						       		erqn.evaluation.itemWrapper.titleLabel("Titre");
						        if(bool==1)
						       		erqn.evaluation.itemWrapper.titleLabel("Title");
						        erqn.evaluation.itemWrapper.title(array[i].itemTitle);
						        erqn.evaluation.itemWrapper.descriptionLabel("Description");
						        erqn.evaluation.itemWrapper.description(array[i].itemDescription);
						        
						        if(bool==0)
						        	erqn.evaluation.evaluationWrapper.commentLabel("Commentaire");
						        if(bool==1)
						        	erqn.evaluation.evaluationWrapper.commentLabel("Comment");
						        if(bool==0)
						        	erqn.evaluation.evaluationWrapper.markLabel("Note");
						        if(bool==1)
							        erqn.evaluation.evaluationWrapper.markLabel("Mark");
								
								$(notifier._dom).append(erqn.dom);
							}
								
							//document.body.appendChild(page);
						});
		}
		
		function loadEvaluationResponseNotifications () {
			(jQuery)	.get ( "/KasuKasu/evaluation", {
							resource : "response",
							action : "list"
						} )
						
						.done ( function (data) {
							var result = $.parseJSON(data);
							let i = 0;
							
							if (result.success === undefined) {
								console.log(result.warning);
								return;
							}
							
							console.log(result.success);
							
							for (var ersn_data of result.success) {
								
								let ersn = new EvaluationResponseNotification("ersn-" + i++);
								ersn.data = ersn_data;
								
								$(ersn.cancel.dom).click( function () {
									$(ersn.dom).remove();
									$.post("/KasuKasu/evaluation", {
										resource : "response",
										action : "remove",
										evaluation_response_id : ersn_data.responseId
									} );
								} );
								
								ersn.profile.image(ersn_data.senderImage);
								ersn.profile.imageDimensions(150, 150);
								ersn.profile.idLabel("Id");
								ersn.profile.id(ersn_data.senderId);
								if(bool==0)
									ersn.profile.firstnameLabel("Prénom");
								if(bool==1)
									ersn.profile.firstnameLabel("First name");
									
								ersn.profile.firstname(ersn_data.senderFirstname);
								if(bool==0)
									ersn.profile.lastnameLabel("Nom");
								if(bool==1)
									ersn.profile.lastnameLabel("Last name");
									
								ersn.profile.lastname(ersn_data.senderLastname);
								
								ersn.evaluation.itemWrapper.idLabel("Id");
								ersn.evaluation.itemWrapper.id(ersn_data.itemId);
								if(bool==0)
									ersn.evaluation.itemWrapper.titleLabel("Titre");
								if(bool==1)
									ersn.evaluation.itemWrapper.titleLabel("Title");
								
								ersn.evaluation.itemWrapper.title(ersn_data.itemTitle);
								ersn.evaluation.itemWrapper.descriptionLabel("Description");
								ersn.evaluation.itemWrapper.description(ersn_data.itemDescription);
								
								if(bool==0)
									ersn.evaluation.evaluationWrapper.commentLabel("Commentaire");
								if(bool==1)
									ersn.evaluation.evaluationWrapper.commentLabel("Comment");
									
								ersn.evaluation.evaluationWrapper.comment(ersn_data.comment);
								if(bool==0)
									ersn.evaluation.evaluationWrapper.markLabel("Note");
								if(bool==1)
									ersn.evaluation.evaluationWrapper.markLabel("Mark");
								ersn.evaluation.evaluationWrapper.mark = ersn_data.mark;
								
								$(notifier._dom).append(ersn.dom);
							}
						} );
		}
		
		window.onload = function () {
			
			notifier = new Notifier("notifier");
			$("#page").append(notifier._dom);
			
			loadEvaluationRequestNotifications();
			loadEvaluationResponseNotifications();
			
		};
		
		</script>

	</div>

	<%@ include file="/fragments/footer.jspf"%>
</body>
</html>