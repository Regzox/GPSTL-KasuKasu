<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- <title>KasuKasu - Modify your account</title> -->
 <title>KasuKasu - Modifier votre compte</title> 


<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet"
	href="/KasuKasu/css/bootstrap.min.css">

<script type='text/javascript' src='/KasuKasu/js/jquery-3.1.1.js'></script>
<script type="text/javascript" src="/KasuKasu/js/md5.js"></script>
<script type="text/javascript" src="/KasuKasu/js/tether.min.js"></script>
<script type="text/javascript" src="/KasuKasu/js/bootstrap.min.js"></script>

<script type="text/javascript" src="/KasuKasu/js/modify-user-submit.js"></script>
</head>

<body>

	<%@ include file="/fragments/sidebar.jspf"%>

	<div id='page'>

		<div class="fit-width capital center show">Modifier votre compte</div>

		<div class="layer-center">

			<div id="notifier" class="notifier"></div>

			<form id="modify" class="layer-left" action="javascript:void(0)"
				method="post" onsubmit="validation(this)">
				<fieldset>

					<!-- <legend>Type your current</legend>
	
	                <div class="form-group row">
	                    <label for="old_email_input" class="col-xs-2 col-form-label">Email *</label>
	                    <div class="col-xs-10">
	                        <input 	class = "form-control" 
	                                type = "email" 
	                                value = ""
	                                id = "old_email_input"
	                                name = "old_email_input"
	                                required >
	                    </div>
	                </div>
	
	                <div class="form-group row">
	                    <label for="old_password_input" class="col-xs-2 col-form-label">Password *</label>
	                    <div class="col-xs-10">
	                        <input 	class = "form-control" 
	                                type = "password" 
	                                value = ""
	                                id = "old_password_input"
	                                name = "old_password_input"
	                                required >
	                    </div>
	                </div>
	
	                <hr /> -->

					<legend>Taper vos modifications</legend>

					<div class="form-group row">
						<label for="new_email_input" class="col-xs-3 col-form-label">Nouvel
							email</label>
						<div class="col-xs-9">
							<input class="form-control" type="email" value=""
								id="new_email_input" name="new_email_input"
								placeholder="Votre nouvel email (optionnel)" maxlength="255">
						</div>
					</div>

					<div class="form-group row">
						<label for="new_password_input" class="col-xs-3 col-form-label">Nouveau
							mot de passe</label>
						<div class="col-xs-9">
							<input class="form-control" type="password" value=""
								id="new_password_input" name="new_password_input"
								placeholder="Votre nouveau mot de passe (optionnel)"
								pattern=".{0}|.{8,64}">
						</div>
					</div>

					<div class="form-group row">
						<label for="new_name_input" class="col-xs-3 col-form-label">Nouveau
							nom</label>
						<div class="col-xs-9">
							<input class="form-control" type="text" value=""
								id="new_name_input" name="new_name_input"
								placeholder="Notre nouveau nom (optionnel)" pattern=".{0}|.{1,64}">
						</div>
					</div>

					<div class="form-group row">
						<label for="new_firstname_input" class="col-xs-3 col-form-label">Nouveau
							pr�nom</label>
						<div class="col-xs-9">
							<input class="form-control" type="text" value=""
								id="new_firstname_input" name="new_firstname_input"
								placeholder="Votre nouveau pr�nom (optionnel)"
								pattern=".{0}|.{1,64}">
						</div>
					</div>

					<div class="form-group row">
						<label for="new_phone_input" class="col-xs-3 col-form-label">Nouveau
							t�l�phone</label>
						<div class="col-xs-9">
							<input class="form-control" type="text" value=""
								id="new_phone_input" name="new_phone_input"
								placeholder="Votre nouveau t�l�phone (optionnel)" pattern="^((\+\d{1,3}(-| )?\(?\d\)?(-| )?\d{1,5})|(\(?\d{2,6}\)?))(-| )?(\d{3,4})(-| )?(\d{4})(( x| ext)\d{1,5}){0,1}$">
						</div>
					</div>

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
									<h4 class="modal-title" id="myModalLabel">Validation</h4>
								</div>
								<div class="modal-body">
<!-- 									<p>Please type your current information to apply your -->
<!-- 										changes</p> -->
									<p>Veuillez renseigner vos informations pour appliquer vos changements</p> 
									<!-- <div class="form-group row">
										<label for="old_email_input" class="col-xs-3 col-form-label">Email
											*</label>
										<div class="col-xs-8">
											<input class="form-control" type="email" value=""
												id="old_email_input" name="old_email_input"
												placeholder="Email actuel (obligatoire)" required>
										</div>
									</div> -->
									<div class="form-group row">
										<label for="old_password_input"
											class="col-xs-3 col-form-label">Mot de passe *</label>
										<div class="col-xs-8">
											<input class="form-control" type="password" value=""
												id="old_password_input" name="old_password_input"
												placeholder="Mot de passe actuel (obligatoire)" required>
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<input class="fit-width button" type="submit"
										value="Sauvegarder les changements" />
								</div>
							</div>
						</div>
					</div>

					<button type="button" class="fit-width button" data-toggle="modal"
						data-target="#myModal">Valider</button>

				</fieldset>
			</form>
		</div>
	</div>
</body>

</html>