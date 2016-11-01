<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/bootstrap.min.css">

<script type="text/javascript" src="/KasuKasu/js/enregistrement.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>



<title>Inscription</title>
</head>
    <body onload="javascript:Captcha()">
    
    	<div class='layer-center'>
    	
    	<p class='capital'>Inscription</p>
    	
    
		 <!--  <div id='notifier' class='notifier'></div> -->
            
            <div class='layer-left'>
   
    
    
            <form action="javascript:(function(){return;})()" method="get"
				OnSubmit="javascript:enregistrement(this)">

                
           <div class="form-group row">
				<label for="prenom" class="col-xs-2 col-form-label">Prénom</label>
				<div class="col-xs-10">
					<input class="form-control" type="text" value=""
						id="prenom" maxlength="255">
				</div>
			</div>

			
			<div id='error_prenom'></div> 
			

           <div class="form-group row">
			   <label for="nom" class="col-xs-2 col-form-label">Nom</label>
				<div class="col-xs-10">
					<input class="form-control" type="text" value=""
						id="nom" maxlength="255">
				</div>
			</div> 
			
			<div id='error_nom'></div> 
			             
              
			<div class="form-group row">
				<label for="numero" class="col-xs-2 col-form-label" >Téléphone</label>
				<div class="col-xs-10">
					<input class="form-control" type="tel" value="" 
						id="numero"  pattern="^((\+\d{1,3}(-| )?\(?\d\)?(-| )?\d{1,5})|(\(?\d{2,6}\)?))(-| )?(\d{3,4})(-| )?(\d{4})(( x| ext)\d{1,5}){0,1}$">
				</div>
			</div>
			
			

			
			<div id='error_telephone'></div> 
			
                
 			<div class="form-group row">
				<label for="email" class="col-xs-2 col-form-label">Email</label>
				<div class="col-xs-10">
					<input class="form-control" type="email"
						value="" id="email" maxlength="255">
				</div>
			</div>
			
			<div id='error_email'></div> 
			


			<div class="form-group row">
				<label for="mdp" class="col-xs-2 col-form-label" >Mot de passe</label>
				<div class="col-xs-10">
					<input class="form-control" type="password" value="" placeholder="Minimum 8 caractères"
						id="mdp" maxlength="255">
				</div>
			</div>
			
			<div id='error_mdp'></div> 
			

			<div class="form-group row">
				<label for="confirmation" class="col-xs-2 col-form-label">Confirmer le mot de passe</label>
				<div class="col-xs-10">
					<input class="form-control" type="password" value=""
						id="confirmation" maxlength="255">
				</div>
			</div>
			
			<div id='error_confirmation'></div> 
			
			
<!-- 					<hr /> -->
			
			
<!-- 			<div class="form-group row"> -->
<!-- 				<div class="col-xs-10"> -->
				
<!-- 					Entrez le code suivant		 -->
						
				   
<!-- 					<input class="form-control" type="text" value="" id="captcha" disabled="disabled"> -->
<!-- 					<input type="button" id="refresh" value ="Nouveau code" onClick="javascript:Captcha()"/> -->
<!-- 					<input class="form-control" type="text" value="" id="captcha_rep" maxlength="255"> -->
					
					
<!-- 				</div> -->
<!-- 			</div> -->
			
<!-- 			<div id='error_captcha'></div>  -->
			

                
                <input type="submit" class="btn btn-primary btn-block"></button>
                


        </form>
        
        
        		</div>
        		        		</div>
        		
        

    </body>

</html>

