<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Importer un lieu d'échange</title>
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet"	href="/KasuKasu/css/bootstrap.min.css">
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>
<script src="http://www.openlayers.org/api/OpenLayers.js"></script>
<script type="text/javascript" src="/KasuKasu/js/tether.min.js"></script>
<script type="text/javascript" src="/KasuKasu/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/KasuKasu/js/importpoint.js" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="/KasuKasu/css/sidebar.css" />

</head>
<body onload="javascript:init()">

	<div id="mapdiv" style="width: 400px; height: 400px"></div>
	
						<!-- Modal -->
					<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="myModalLabel">Importer le lieu</h4>
								</div>
								<div class="modal-body">
<!-- 									<p>Please type your current information to apply your -->
<!-- 										changes</p> -->
									<p>Veuillez renseigner vos nouvelles informations</p> 
									 <div class="form-group row">
										<label for="old_email_input" class="col-xs-3 col-form-label">Nom
											*</label>
										<div class="col-xs-8">
											<input class="form-control"  value=""
												id="nom_input" name="nom_input"
												placeholder="Nom (obligatoire)" required>
										</div>
									</div> 
									<div class="form-group row">
										<label for="old_password_input"
											class="col-xs-3 col-form-label">Portée *</label>
										<div class="col-xs-8">
											<input class="form-control"  value=""
												id="radius_input" name="radius_input"
												placeholder="Portée (obligatoire)" required>
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<input class="fit-width button" type="submit" id="save"
										value="Ajouter" />
								</div>
							</div>
						</div>
					</div>
<input type="hidden" name="lat" id="lat" value="0">
<input type="hidden" name="lon" id="lon" value="0">

</body>
</html>