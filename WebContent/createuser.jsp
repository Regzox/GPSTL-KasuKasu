<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link type="text/css" rel="stylesheet" href="/KasuKasu/css/style.css" />
<link type="text/css" rel="stylesheet" href="/KasuKasu/css/bootstrap.min.css">

<script type="text/javascript" src="/KasuKasu/js/enregistrement.js"></script>
<script	type="text/javascript" src="/KasuKasu/js/translation.js"></script>
<script type="text/javascript" src="/KasuKasu/js/jquery-3.1.1.js"></script>



<title id='titre'>Inscription</title>
</head>
    <body onload="trans()">
    
    	<div class='layer-center'>
    	
    	<p id="a" class="capital">Inscription</p>
    	
    
		 <!--  <div id='notifier' class='notifier'></div> -->
            
            <div class='layer-left'>
   
    
    
            <form action="javascript:(function(){return;})()" method="get"
				OnSubmit="javascript:enregistrement(this)">

                
           <div class="form-group row">
				<label id='prenoml' for="prenom" class="col-xs-2">Prénom</label>
				<div class="col-xs-10">
					<input class="form-control" type="text" value=""
						id="prenom" maxlength="255">
				</div>
			</div>


			
			<div id='error_prenom'></div> 
			</a>
			

           <div class="form-group row">
           
			   <label id='noml' for="nom" class="col-xs-2">Nom</label>
				<div class="col-xs-10">
					<input class="form-control" type="text" value=""
						id="nom" maxlength="255">
				</div>
			</div> 
			
			<div id='error_nom'></div> 
			             
              
			<div class="form-group row">
				<label id='numl' for="numero" class="col-xs-2" >Téléphone</label>
				<div class="col-xs-10">
					<input class="form-control" type="tel" value="" 
						id="numero"  pattern="^((\+\d{1,3}(-| )?\(?\d\)?(-| )?\d{1,5})|(\(?\d{2,6}\)?))(-| )?(\d{3,4})(-| )?(\d{4})(( x| ext)\d{1,5}){0,1}$">
				</div>
			</div>
			
			

			
			<div id='error_telephone'></div> 
			
                
 			<div class="form-group row">
				<label id='emaill' for="email" class="col-xs-2">Email</label>
				<div class="col-xs-10">
					<input class="form-control" type="email"
						value="" id="email" maxlength="255">
				</div>
			</div>
			
			<div id='error_email'></div> 
			


			<div class="form-group row">
				<label id='mdpl' for="mdp" class="col-xs-2" >Mot de passe</label>
				<div class="col-xs-10">
					<input class="form-control" type="password" value="" placeholder="Minimum 8 caractères"
						id="mdp" maxlength="255">
				</div>
			</div>
			
			<div id='error_mdp'></div> 
			

			<div class="form-group row">
				<label id='confl' for="confirmation" class="col-xs-2">Confirmer le mot de passe</label>
				<div class="col-xs-10">
					<input class="form-control" type="password" value=""
						id="confirmation" maxlength="255">
				</div>
			</div>
			
			<div id='error_confirmation'></div> 
			
			
<!-- 					<hr /> -->
			
			
			<div  class="form-group row">
				<p id='captchal'>Veuillez cocher la case correspondante à Albert Einstein</p>
				<br> 
				<br>
				<div class="col-xs-12">
				<input type="radio" id="einstein" name="image">
				<img alt="" src="data/AlbertEinstein.jpg" width="100" height="100">
				<input type="radio" id="elisabeth" name="image">
				<img alt="" src="data/ReineElisabeth.jpeg" width="100" height="100">
				<input type="radio" id="bill" name="image">
				<img alt="" src="data/BillGates.jpeg" width="100" height="100">
				<input type="radio" id="marie" name="image" >
				<img alt="" src="data/MarieCurie.png" width="100" height="100">
						
					
				</div>
			</div>
			
			<div id='error_captcha'></div> 
			

                
                <input id='submit' type="submit" class="btn btn-primary btn-block" >
                


        </form>
        
        
        		</div>
        		        		</div>
        		
        

    </body>

</html>

