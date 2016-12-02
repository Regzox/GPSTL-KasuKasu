<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Ajouter un lieu de pr�t</title>


<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet"	href="/KasuKasu/css/bootstrap.min.css">
<script type="text/javascript" src="js/jquery-3.1.1.js"></script>
<script src="http://www.openlayers.org/api/OpenLayers.js"></script>
<script type="text/javascript" src="/KasuKasu/js/tether.min.js"></script>
<script type="text/javascript" src="/KasuKasu/js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/addpointpret.js" charset="utf-8"></script>

<link rel="stylesheet" type="text/css" href="/KasuKasu/css/sidebar.css" />

<style type="text/css">
html, body, #mapdiv {
	width: 100%;
	height: 100%;
	margin: 0;
}

.olImageLoadError {
	display: none;
}
</style>



</head>

<body onload="javascript:init()">


	<%@ include file="/fragments/sidebar.jspf"%>

	<div id="page">

		<div class='col-md-6'>
			
			<div class="form-group row">
				<div class="col-xs-10">Choisissez des points de pr�t en
					cliquant sur l'image</div>
			</div>

			<div id="mapdiv" style="width: 400px; height: 400px"></div>

		</div>

		<div class='modal-body row'>

			<div class='col-md-6'>
				<form id="formulaire" method="get">
					<input type="hidden" name="nombre" id="nombre" value="0">
				</form>
			</div>

			<div id='error_point'></div>

			<input type="submit" class="btn btn-primary btn-block"
				onClick="javascript:createobject()">
		</div>
	</div>

<div id="myModal" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <!-- dialog body -->
      <div class="modal-body">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        Les lieux ont bien �t� ajout�s
      </div>
    </div>
  </div>
</div>

</body>

</html>