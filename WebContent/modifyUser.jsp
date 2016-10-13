<%@ page language='java' contentType='text/html; charset=ISO-8859-1'
	pageEncoding='ISO-8859-1'%>
<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>
<title>KasuKasu - Modify your account</title>

<link type='text/css' rel='stylesheet' href='/KasuKasu/css/style.css' />
<link type='text/css' rel='stylesheet' href='/KasuKasu/css/bootstrap.min.css'>

<script type='text/javascript' src='/KasuKasu/js/md5.js'></script>
<script type='text/javascript' src='/KasuKasu/js/jquery-3.1.1.min.js'></script>
<script type='text/javascript' src='/KasuKasu/js/tether.min.js'></script>
<script	type='text/javascript' src='/KasuKasu/js/bootstrap.min.js'></script>

<script type='text/javascript' src='/KasuKasu/js/modify-user-submit.js'></script>

</head>
<body>


	<div class='layer-center'>
	
	<p class='capital'>Change your account information</p>
	
	<div>
		<div id='notifier' class='notifier'></div>
		
		<div class='layer-left'>
			<p class='sub-capital'>Your current information</p>
			<div class='form-group row'>
				<label for='old-email-input' class='col-xs-2 col-form-label'>Email *</label>
				<div class='col-xs-10'>
					<input 	class = 'form-control' 
							type = 'email' 
							value = ''
							id = 'old-email-input'
							name = 'old-email-input'
							required >
				</div>
			</div>

			<div class='form-group row'>
				<label for='old-password-input' class='col-xs-2 col-form-label'>Password *</label>
				<div class='col-xs-10'>
					<input 	class = 'form-control' 
							type = 'password' 
							value = ''
							id = 'old-password-input'
							name = 'old-password-input'
							required >
				</div>
			</div>
		</div>

		<hr />

		<div class='layer-left'>
			<p class='sub-capital'>Your new information</p>
			<div class='form-group row'>
				<label for='new-email-input' class='col-xs-2 col-form-label'>Email</label>
				<div class='col-xs-10'>
					<input 	class = 'form-control'
					 		type = 'email'
					 		value = ''
					 		id = 'new-email-input'
					 		name = 'new-email-input' >
				</div>
			</div>

			<div class='form-group row'>
				<label for='new-password-input' class='col-xs-2 col-form-label'>Password</label>
				<div class='col-xs-10'>
					<input 	class = 'form-control'
				 		type = 'password'
				 		value = ''
				 		id = 'new-password-input'
				 		name = 'new-password-input' >
				</div>
			</div>

			<div class='form-group row'>
				<label for='new-name-input' class='col-xs-2 col-form-label'>Name</label>
				<div class='col-xs-10'>
					<input 	class = 'form-control'
				 		type = 'text'
				 		value = ''
				 		id = 'new-name-input'
				 		name = 'new-name-input' >
				</div>
			</div>

			<div class='form-group row'>
				<label for='new-firstname-input' class='col-xs-2 col-form-label'>First Name</label>
				<div class='col-xs-10'>
					<input 	class = 'form-control'
				 		type = 'text'
				 		value = ''
				 		id = 'new-firstname-input'
				 		name = 'new-firstname-input' >
				</div>
			</div>

			<div class='form-group row'>
				<label for='new-phone-input' class='col-xs-2 col-form-label'>Telephone</label>
				<div class='col-xs-10'>
					<input 	class = 'form-control'
				 		type = 'text'
				 		value = ''
				 		id = 'new-phone-input'
				 		name = 'new-phone-input' >
				</div>
			</div>

			<button type='button' class='btn btn-primary btn-block' onclick='submit()'>Validate</button>

		</div>
 	</div>
	</div>
	
</body>

</html>