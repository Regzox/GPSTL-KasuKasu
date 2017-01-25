<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title id='titre'>Lieux d'échange</title>

<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/bootstrap.min.css">
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script src="http://www.openlayers.org/api/OpenLayers.js"></script>
<script type="text/javascript" src="/KasuKasu/js/tether.min.js"></script>
<script type="text/javascript" src="/KasuKasu/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/KasuKasu/js/importpoint.js"></script>
<script type="text/javascript" src="/KasuKasu/js/traduction.js"></script>
<script type="text/javascript" src="/KasuKasu/js/cookies.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		trans('importpoint.jsp', 'titre');
		trans('importpoint.jsp', 'pts');
		trans('importpoint.jsp', 'pts_amis');
		trans('importpoint.jsp', 'comment');
		trans('importpoint.jsp', 'ModalAjout');
		trans('importpoint.jsp', 'noml');
		trans('importpoint.jsp', 'porteel');
		trans('importpoint.jsp', 'radius_input');
		trans('importpoint.jsp', 'close');
		trans('importpoint.jsp', 'save');
		trans('importpoint.jsp', 'comment2');
		trans('importpoint.jsp', 'comment3');
		trans('importpoint.jsp', 'ModalModif');
		trans('importpoint.jsp', 'nom');
		trans('importpoint.jsp', 'range');
		trans('importpoint.jsp', 'ferm');
		trans('importpoint.jsp', 'enreg');
	});
</script>

<link rel="stylesheet" type="text/css" href="/KasuKasu/css/sidebar.css" />



<style type="text/css">
html, body, #mapdiv {
	overflow: hidden; z-index : 0;
	width: 100%;
	height: 100%;
	margin: 0;
	z-index: 0;
}

.olImageLoadError {
	display: none;
}
</style>
</head>
<body onload="javascript:init();">

	<%@ include file="/fragments/interface/navbar.jspf"%>
	<%@ include file="/fragments/interface/sidebar.jspf"%>

	<!-- 	<div id="mapdiv"></div> -->
	<div id="page">

		<div class="menu captital">
			<div class="wrapper">
				<div class="tier"></div>
				<div class="tier">Carte interactive</div>
				<div class="tier"></div>
			</div>
		</div>

		<div class="wrapper">
			<div class="demi">
				<div id="mapdiv"></div>
			</div>
			<div class="demi">
				<div class="wrapper show" style="size">
					<div class="tier"></div>
					<div class="tier capitale">Légende</div>
					<div class="tier"></div>
				</div>
				<div class="layer-center wrapper show">
					<div class="demi">
						<div class="demi">
							<img alt="" src="/KasuKasu/data/marker-red.png" width="40"
								height="40">
						</div>
						<div class="demi">
							<div id='pts'>Vos lieux d'échange</div>
						</div>
					</div>
					<div class="demi">
						<div class="demi">
							<img alt="" src="/KasuKasu/data/marker.png" width="40"
								height="40">
						</div>
						<div class="demi">
							<div id='pts_amis'>Lieux d'échange de vos amis</div>
						</div>
					</div>
				</div>
				<div class="layer-center">
					<div id='comment'>Double-cliquez sur la carte pour ajouter un
						lieu</div>
				</div>
			</div>

			<%@ include file="/fragments/interface/footer.jspf"%>

<!-- <<<<<<< HEAD
		</div>
		Modal
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					Modal Header
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" id="ModalAjout">Ajouter le lieu</h4>
					</div>

					Modal Body
					<div class="modal-body">

						<form class="form-horizontal" role="form">
							<div class="form-group row">
								<label id='noml' for="old_email_input"
									class="col-xs-3 col-form-label">Nom</label>
								<div class="col-xs-8">
									<input class="form-control" value="" id="nom_input"
										name="nom_input" placeholder="Nom (obligatoire)"
										required="required">
								</div>
							</div>
							<div class="form-group row">
								<label id='porteel' for="old_password_input"
									class="col-xs-3 col-form-label">Portée</label>
								<div class="col-xs-8">
									<input class="form-control" value="" id="radius_input"
										type="number" name="radius_input"
										placeholder="Portée (obligatoire)" required="required">
								</div>
======= -->
					<form class="form-horizontal" role="form">
						<div class="form-group row">
							<label id='noml' for="old_email_input" class="col-xs-3 col-form-label">Nom</label>
							<div class="col-xs-8">
								<input class="form-control" value="" id="nom_input"
									name="nom_input" placeholder="Nom (obligatoire)"
									>
							</div>
						</div>
						<div class="form-group row">
							<label id='porteel' for="old_password_input" class="col-xs-3 col-form-label">Portée</label>
							<div class="col-xs-8">
								<input class="form-control" value="" id="radius_input"
									 name="radius_input"
									placeholder="Portée numérique (obligatoire)" >
<!-- >>>>>>> 2109ac524c37be233189e9a1de146531641065b6 -->
							</div>
						</form>






					</div>

					<!-- Modal Footer -->
					<div class="modal-footer">
						<button id='close' type="button" class="btn btn-default"
							data-dismiss="modal">Fermer</button>
						<button type="button" id="save" class="btn btn-primary">Ajouter</button>
					</div>
				</div>
			</div>
		</div>


		<div id="myModal2" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<!-- dialog body -->
					<div class="modal-body">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<div id='comment2'>Le lieu a bien été ajouté</div>
					</div>
				</div>
			</div>
		</div>

		<div id="myModal3" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<!-- dialog body -->
					<div class="modal-body">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<div id="comment3">Les changements ont bien été pris en
							compte</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Modal -->
		<div class="modal fade" id="myModal4" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<!-- Modal Header -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span> <span id="close"
								class="sr-only">Fermer</span>
						</button>
						<h4 class="modal-title" id="ModalModif">Modifier le lieu</h4>
					</div>

					<!-- Modal Body -->
					<div class="modal-body">

						<form class="form-horizontal" role="form">
							<div class="form-group row">
								<label id="nom" for="old_email_input"
									class="col-xs-3 col-form-label">Nom</label>
								<div class="col-xs-8">
									<input class="form-control" value="" id="nom_input"
										name="nom_input">
									<!-- 									placeholder="Nom" -->

								</div>
							</div>
<!-- <<<<<<< HEAD
							<div class="form-group row">
								<label id="range" for="old_password_input"
									class="col-xs-3 col-form-label">Portée</label>
								<div class="col-xs-8">
									<input class="form-control" value="" id="radius_input"
										type="number" name="radius_input">
																		placeholder="Portée"
								</div>
======= -->
						</div>
						<div class="form-group row">
							<label id="range" for="old_password_input" class="col-xs-3 col-form-label">Portée</label>
							<div class="col-xs-8">
								<input class="form-control" value="" id="radius_input"
									 name="radius_input"
									>
<!-- 									placeholder="Portée" -->
<!-- >>>>>>> 2109ac524c37be233189e9a1de146531641065b6 -->
							</div>
						</form>
					</div>

					<!-- Modal Footer -->
					<div class="modal-footer">
						<button id="ferm" type="button" class="btn btn-default"
							data-dismiss="modal">Fermer</button>
						<button id="enreg" type="button" class="btn btn-primary">Enregistrer</button>
					</div>
				</div>
			</div>
		</div>
</body>
</html>