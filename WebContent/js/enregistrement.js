function enregistrement(formulaire) 
{
	var nom = formulaire.nom.value;
	var prenom = formulaire.prenom.value;
	var numero = formulaire.numero.value;
	var email = formulaire.email.value;
	var mdp = formulaire.mdp.value;
	var confirmation = formulaire.confirmation.value;


	var ok = verif(nom, prenom, numero, email, mdp, confirmation);
	if (ok) 
	{
		printHTML("#notifier","");
		enregistre(nom, prenom, numero, email, mdp, confirmation);
	}
}



function verif(nom, prenom, numero, email, mdp, confirmation) 
{
	if(prenom.length==0)
	{
		func_erreur_inscription("Prenom manquant");
		return false;
	}
	
	if(nom.length==0)
	{
		func_erreur_inscription("Nom manquant");
		return false;
	}
	
	if(numero.length==0)
	{
		func_erreur_inscription("Telephone manquant");
		return false;
	}
	
	if(email.length==0)
	{
		func_erreur_inscription("Email manquant");
		return false;
	}

	if(mdp.length==0)
	{
		func_erreur_inscription("Mot de passe manquant");
		return false;
	}
	
	if(mdp.length<8)
	{
		func_erreur_inscription("Mot de passe trop court");
		return false;
	}

	
	if(mdp != confirmation)	
	{
		func_erreur_inscription("Mots de passe incompatibles");
		return false;
	} 
	
	if (!ValidCaptcha())
	{
		func_erreur_inscription("Captcha incompatible");
		return false;
		
	}
	
	else 
	{
		return true;
	}
}


function enregistre(prenom, nom, numero, email, mdp, confirmation) 
{
	$.ajax({
		type : "POST",
		url : "createuser",
		data : "prenom=" + prenom + "&nom=" + nom + "&numero=" + numero + "&email="
				+ email+ "&mdp=" + mdp + "&confirmation=" + confirmation,
		dataType : "json",
		success : traiteReponseEnregistrement,
		error : function(XHR, testStatus, errorThrown) 
		{
			alert(XHR + "" + testStatus + "" + errorThrown);
		}
	});
}




function traiteReponseEnregistrement(rep) 
{
	if (rep.error != undefined)  
	{
		func_erreur_inscription(rep.error);
	}
	else if (rep.message!=undefined)
	{
		if (rep.message!="1") func_erreur_inscription(rep.message);
		else window.location.href = "connexion.jsp";
	}
	else 
	{
		window.location.href = "connexion.jsp";
	}
}


function func_erreur_inscription(msg)
{
		printHTML("#notifier",msg);
}


function printHTML(dom,htm)
{ 
	$(dom).html(htm);
}

function Captcha()
{
    var alpha = new Array('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z');
    var i;
    for (i=0;i<6;i++)
    {
      var a = alpha[Math.floor(Math.random() * alpha.length)];
      var b = alpha[Math.floor(Math.random() * alpha.length)];
      var c = alpha[Math.floor(Math.random() * alpha.length)];
      var d = alpha[Math.floor(Math.random() * alpha.length)];
      var e = alpha[Math.floor(Math.random() * alpha.length)];
      var f = alpha[Math.floor(Math.random() * alpha.length)];
      var g = alpha[Math.floor(Math.random() * alpha.length)];
     }
   var code = a +  b +  + c +  d + e + f +  g;

   document.getElementById('captcha').value=code;
 }
 function ValidCaptcha()
 {
     var string1 = document.getElementById('captcha').value;
     var string2 = document.getElementById('captcha_rep').value;
     if (string1 == string2)
     {
       return true;
     }
     else
     {        
       return false;
     }
 }



