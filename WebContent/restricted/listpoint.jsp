<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/KasuKasu/js/tether.min.js"></script>
<script type="text/javascript" src="/KasuKasu/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/KasuKasu/js/listpoint.js"></script>
<script type="text/javascript" src="/KasuKasu/js/translation.js"></script>

<title id="titre">Liste des points d'échange</title>


</head>
<body onload="trans(); userPoints()">
	
	<%@ include file="/fragments/interface/navbar.jspf"%>
	<%@ include file="/fragments/interface/sidebar.jspf"%>
	
	<div id='page'>
	
		<%@ include file="/fragments/interface/menus/listpoint_menu.jspf"%>
	
		<div class='layer-center'>
			<div id='notifier'></div>
		</div>
		
		<%@ include file="/fragments/interface/footer.jspf"%>
		
	</div>
	
	<div id="myModal" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <!-- dialog body -->
      <div id="comment" class="modal-body">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        Les changements ont bien été pris en compte
      </div>
    </div>
  </div>
</div>

	<!-- Modal -->
	<div class="modal fade" id="myModal2" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span> <span id="close" class="sr-only">Fermer</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Modifier le lieu</h4>
				</div>

				<!-- Modal Body -->
				<div class="modal-body">

					<form class="form-horizontal" role="form">
						<div class="form-group row">
							<label id="nom" for="old_email_input" class="col-xs-3 col-form-label">Nom</label>
							<div class="col-xs-8">
								<input class="form-control" value="" id="nom_input"
									name="nom_input" >
<!-- 									placeholder="Nom" -->
									
							</div>
						</div>
						<div class="form-group row">
							<label id="range" for="old_password_input" class="col-xs-3 col-form-label">Portée</label>
							<div class="col-xs-8">
								<input class="form-control" value="" id="radius_input"
									type="number" name="radius_input"
									>
<!-- 									placeholder="Portée" -->
							</div>
						</div>
					</form>






				</div>

				<!-- Modal Footer -->
				<div class="modal-footer">
					<button id="ferm" type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
					<button id="enreg" type="button" id="save" class="btn btn-primary">Enregistrer</button>
				</div>
			</div>
		</div>		
	</div>

</body>
</html>