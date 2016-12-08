<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Ajout d'un Objet</title>

<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet"href="/KasuKasu/css/bootstrap.min.css">
<script type="text/javascript" src="js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="/KasuKasu/js/tether.min.js"></script>
<script type="text/javascript" src="/KasuKasu/js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/createobject.js"></script>
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/sidebar.css" />






</head>

<body onload="javascript:init()">
	
	<%@ include file="/fragments/sidebar.jspf"%>

	<div id="page">
	
	<p class='capital' style='text-align: center'>Ajouter un objet</p>
	
	<div class='modal-body row'>
		<div class='col-md-6'>
			<form id="formulaire" method="get">

				<div class="form-group row">
					<label for="nom" class="col-xs-2 col-form-label">Nom</label>
					<div class="col-xs-10">
						<input class="form-control" type="text" value="" id="nom"
							maxlength="255">
					</div>
				</div>


				<div id='error_nom'></div>

				<div class="form-group row">
					<label for="description" class="col-xs-2 col-form-label">Description</label>
					<div class="col-xs-10">
						<input class="form-control" type="text" value="" id="description"
							maxlength="255">
					</div>
				</div>

				<div id='error_description'></div>

<!-- 				<div class="form-group row"> -->
<!-- 					<label for="datedeb" class="col-xs-2 col-form-label">Début -->
<!-- 						du prêt</label> -->
<!-- 					<div class="col-xs-10"> -->
<!-- 						<input type="date" id="datedeb" placeholder="Format : jj/mm/aaaa"> -->
<!-- 					</div> -->
<!-- 				</div> -->

<!-- 				<div id='error_datedeb'></div> -->


<!-- 				<div class="form-group row"> -->
<!-- 					<label for="datefin" class="col-xs-2 col-form-label">Fin du -->
<!-- 						prêt</label> -->
<!-- 					<div class="col-xs-10"> -->
<!-- 						<input type="date" id="datefin" placeholder="Format : jj/mm/aaaa"> -->
<!-- 					</div> -->
<!-- 				</div> -->

<!-- 				<div id='error_datefin'></div> -->

				<div id="found-points" class="abootsraper"></div>
				<div class="form-point row">
					<label for="point" class="col-xs-2 col-form-label">Points d'échange</label>
					<div class="col-xs-10" id="id_check2">
<!-- 						<select name="point" id="point" multiple> -->
<!-- 						</select> -->
					</div>
				</div>
				
				<div id='error_point'></div>
			
				
				<button type="submit" class="positive" name="ajout" id="ajout" style="display:none;">
				Ajouter un point d'échange
				</button>

				<div id="found-groups" class="abootsraper"></div>
				<div class="form-group row">
					<label for="groupe" class="col-xs-2 col-form-label">Visibilité</label>
					<div class="col-xs-10" id ="id_check">
					</div>
				</div>
				
								<div id='error_groupe'></div>
				







				<input type="hidden" name="nombre" id="nombre" value="0">



			</form>

			<input type="submit" class="btn btn-primary btn-block"
				onClick="javascript:createobject()">
		</div>


	</div>


	</div>


	<!-- 	<div class='layer-center'> -->

	<!-- 		<div class="form-group"> -->
	<!-- 			<div class="col-sm-offset-4 col-sm-4"> -->
	<!-- 				<input type="submit" class="btn btn-primary btn-block"></button> -->

	<!-- 			</div> -->
	<!-- 		</div> -->


	<!-- 	</div> -->


<div id="myModal" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <!-- dialog body -->
      <div class="modal-body">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        L'objet a bien été ajouté
      </div>
    </div>
  </div>
</div>



</body>

</html>
