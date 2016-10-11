<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="js/enregistrement.js"></script>
<script type="text/javascript" src="js/jquery-3.1.1.js"></script>

<title>Inscription</title>
</head>
    <body>
        <form action="javascript:(function(){return;})()" method="get"
				OnSubmit="javascript:enregistrement(this)">
				
            <fieldset>
                <legend>Inscription</legend>
                <p>Vous pouvez vous inscrire via ce formulaire.</p>
                
                <label for="prenom">Prénom </label>
                <input type="text" required id="prenom" name="prenom" value="" size="20" maxlength="20" />
                <br />
                
                <label for="nom">Nom </label>
                <input type="text" required id="nom" name="nom" value="" size="20" maxlength="20" />
                <br />               
              
                <label for="numero">Numéro de téléphone (ex: 0678451526) </label>
                <input type="text" required pattern="^((\+\d{1,3}(-| )?\(?\d\)?(-| )?\d{1,5})|(\(?\d{2,6}\)?))(-| )?(\d{3,4})(-| )?(\d{4})(( x| ext)\d{1,5}){0,1}$" id="numero" name="numero" value="" size="20" maxlength="20" />
                <br />
                
                <label for="email">Adresse email </label>
                <input type="email" required id="email" name="email" value="" size="20" maxlength="60" />
                <br />


                <label for="mdp">Mot de passe (minimum 6 caractères) </label>
                <input type="password" required pattern=".{6,}" id="mdp" name="mdp" value="" size="20" maxlength="20" />
                <br />


                <label for="confirmation">Confirmation du mot de passe </label>
                <input type="password" required pattern=".{6,}" id="confirmation" name="confirmation" value="" size="20" maxlength="20" />
                <br />

                <input type="submit" value="Inscription" />
                <br />

            </fieldset>

        </form>

    </body>

</html>




