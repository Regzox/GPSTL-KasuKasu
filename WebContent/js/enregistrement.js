function enregistrement(formulaire) 
{
	printHTML("#error_prenom","");
	printHTML("#error_nom","");
	printHTML("#error_telephone","");
	printHTML("#error_email","");
	printHTML("#error_mdp","");
	printHTML("#error_confirmation","");
	printHTML("#error_captcha","");



	var nom = formulaire.nom.value;
	var prenom = formulaire.prenom.value;
	var numero = formulaire.numero.value;
	var email = formulaire.email.value;
	var mdp = formulaire.mdp.value;
	var confirmation = formulaire.confirmation.value;


	var ok = verif(prenom, nom, numero, email, mdp, confirmation);
	if (ok) 
	{
		//printHTML("#notifier","");
		printHTML("#error_prenom","");
		printHTML("#error_nom","");
		printHTML("#error_telephone","");
		printHTML("#error_email","");
		printHTML("#error_mdp","");
		printHTML("#error_confirmation","");
		printHTML("#error_captcha","");
		enregistre(prenom, nom, numero, email, mdp, confirmation);
	}
}



function verif(prenom,nom, numero, email, mdp, confirmation) 
{
	var bool = true;
	if(prenom.length==0)
	{
		//func_erreur_inscription("Prenom manquant");
		//notify({ warning : "Prenom manquant" });
		printHTML("#error_prenom","Prenom manquant");
		$("#error_prenom").css({
			"color":"red",
			"font-size": "80%"
		});

		bool = false;
	}

	if(nom.length==0)
	{
		//func_erreur_inscription("Nom manquant");
		//notify({ warning : "Nom manquant" });
		printHTML("#error_nom","Nom manquant");
		$("#error_nom").css({
			"color":"red",
			"font-size": "80%"
		});

		bool = false;
	}

	if(numero.length==0)
	{
		//func_erreur_inscription("Telephone manquant");
		//notify({ warning : "Telephone manquant" });
		printHTML("#error_telephone","Telephone manquant");
		$("#error_telephone").css({
			"color":"red",
			"font-size": "80%"
		});

		bool = false;
	}

	if(email.length==0)
	{
		//func_erreur_inscription("Email manquant");
		//notify({ warning : "Email manquant" });
		printHTML("#error_email","Email manquant");
		$("#error_email").css({
			"color":"red",
			"font-size": "80%"
		});

		bool = false;
	}

	if(mdp.length==0)
	{
		//func_erreur_inscription("Mot de passe manquant");
		//notify({ warning : "Mot de passe manquant" });
		printHTML("#error_mdp","Mot de passe manquant");		
		$("#error_mdp").css({
			"color":"red",
			"font-size": "80%"
		});


		bool = false;
	}

	if(mdp.length<8)
	{
		//func_erreur_inscription("Mot de passe trop court");
		//notify({ warning : "Mot de passe trop court" });
		printHTML("#error_mdp","Veuillez inserer un mot de passe de taille > 7");
		$("#error_mdp").css({
			"color":"red",
			"font-size": "80%"
		});

		bool = false;
	}


	if(mdp != confirmation)	
	{
		//func_erreur_inscription("Mots de passe incompatibles");
		//notify({ warning : "Mots de passe incompatibles" });
		printHTML("#error_confirmation","Mots de passe incompatibles");
		$("#error_confirmation").css({
			"color":"red",
			"font-size": "80%"
		});

		bool = false;
	} 

	if (!ValidCaptcha())
	{
		//func_erreur_inscription("Captcha incompatible");
		//notify({ warning : "Captcha incompatible" });
		printHTML("#error_captcha","Ce n'est pas Einstein !");
		$("#error_captcha").css({
			"color":"red",
			"font-size": "80%"
		});

		return false;

	}

	return bool;
}


function enregistre(prenom, nom, numero, email, mdp, confirmation) 
{
	var userlang = navigator.language || navigator.userLanguage; 
	document.cookie = "language="+userlang+"; expires=Thu, 21 Dec 2021 12:00:00 UTC;";

	$.ajax({
		type : "POST",
		url : "createuser",
		xhrFields: 
		{
			withCredentials: true
		},
		data : "prenom=" + prenom + "&nom=" + nom + "&numero=" + numero + "&email="
		+ email+ "&mdp=" + mdp + "&confirmation=" + confirmation,
		dataType : "json",
		success : traiteReponseEnregistrement,
		error : function(XHR, testStatus, errorThrown) 
		{
			console.log(JSON.stringify(XHR + " " + testStatus + " " + errorThrown));

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
		//else window.location.href = kasukasu.private.dashboard;
	}
	else 
	{
		window.location.href = kasukasu.private.dashboard;
	}
}


function func_erreur_inscription(msg)
{
	printHTML("#error_email",msg);
	$("#error_email").css({
		"color":"red",
		"font-size": "80%"
	});
}




function printHTML(dom,htm)
{ 
	$(dom).html(htm);
}

function ValidCaptcha()
{
	
	if (document.getElementById("einstein").checked)
	{
		return true;
	}
	else
	{        
		return false;
	}
}

//function Captcha()
//{
//	var alpha = new Array('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z');
//	var i;
//	for (i=0;i<6;i++)
//	{
//		var a = alpha[Math.floor(Math.random() * alpha.length)];
//		var b = alpha[Math.floor(Math.random() * alpha.length)];
//		var c = alpha[Math.floor(Math.random() * alpha.length)];
//		var d = alpha[Math.floor(Math.random() * alpha.length)];
//		var e = alpha[Math.floor(Math.random() * alpha.length)];
//		var f = alpha[Math.floor(Math.random() * alpha.length)];
//		var g = alpha[Math.floor(Math.random() * alpha.length)];
//	}
//	var code = a +  b +  + c +  d + e + f +  g;
//
//	document.getElementById('captcha').value=code;
//}
//function ValidCaptcha()
//{
//	var string1 = document.getElementById('captcha').value;
//	var string2 = document.getElementById('captcha_rep').value;
//	if (string1 == string2)
//	{
//		return true;
//	}
//	else
//	{        
//		return false;
//	}
//}

