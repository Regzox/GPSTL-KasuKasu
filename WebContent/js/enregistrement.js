bool=0;
if(readCookie("lang") == "en")
	bool=1;
else
	if(readCookie("lang") == "fr")
		bool=0;

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
		if(bool==0)
			printHTML("#error_prenom","Prénom manquant");
		$("#error_prenom").css({
			"color":"red",
			"font-size": "80%"
		});
		if(bool==1)
			printHTML("#error_prenom","First name missing");
		$("#error_prenom").css({
			"color":"red",
			"font-size": "80%"
		});

		//window.scrollTo(0,0);
		document.getElementById('error_prenom').scrollIntoView();
		bool = false;
	}

	if(nom.length==0)
	{
		if(bool==0)
			printHTML("#error_nom","Nom manquant");
		$("#error_nom").css({
			"color":"red",
			"font-size": "80%"
		});
		if(bool==1)
			printHTML("#error_nom","Last name missing");
		$("#error_nom").css({
			"color":"red",
			"font-size": "80%"
		});
		//window.scrollTo(0,0);
		document.getElementById('error_nom').scrollIntoView();
		bool = false;
	}

	if(numero.length==0)
	{
		if(bool==0)
			printHTML("#error_telephone","Téléphone manquant");
		$("#error_telephone").css({
			"color":"red",
			"font-size": "80%"
		});
		if(bool==1)
			printHTML("#error_telephone","Phone number missing");
		$("#error_telephone").css({
			"color":"red",
			"font-size": "80%"
		});
		//window.scrollTo(0,0);
		document.getElementById('error_telephone').scrollIntoView();
		bool = false;
	}

	if(email.length==0)
	{
		if(bool==0)
			printHTML("#error_email","Email manquant");
		$("#error_email").css({
			"color":"red",
			"font-size": "80%"
		});
		if(bool==1)
			printHTML("#error_email","Email missing");
		$("#error_email").css({
			"color":"red",
			"font-size": "80%"
		});
		//window.scrollTo(0,0);
		document.getElementById('error_email').scrollIntoView();

		bool = false;
	}

	if(mdp.length==0)
	{
		if(bool==0)
			printHTML("#error_mdp","Mot de passe manquant");		
		$("#error_mdp").css({
			"color":"red",
			"font-size": "80%"
		});
		if(bool==1)
			printHTML("#error_mdp","Password missing");		
		$("#error_mdp").css({
			"color":"red",
			"font-size": "80%"
		});
		//window.scrollTo(0,0);
		document.getElementById('error_mdp').scrollIntoView();

		bool = false;
	}

	if(mdp.length<8)
	{
		if(bool==0)
			printHTML("#error_mdp","Veuillez saisir un mot de passe de taille > 7");
		$("#error_mdp").css({
			"color":"red",
			"font-size": "80%"
		});
		if(bool==1)
			printHTML("#error_mdp","Please enter a password > 7");
		$("#error_mdp").css({
			"color":"red",
			"font-size": "80%"
		});
		//window.scrollTo(0,0);
		document.getElementById('error_mdp').scrollIntoView();

		bool = false;
	}


	if(mdp != confirmation)	
	{
		if(bool==0)
			printHTML("#error_confirmation","Mots de passe incompatibles");
		$("#error_confirmation").css({
			"color":"red",
			"font-size": "80%"
		});
		if(bool==1)
			printHTML("#error_confirmation","Incompatible passwords");
		$("#error_confirmation").css({
			"color":"red",
			"font-size": "80%"
		});
		//window.scrollTo(0,0);
		document.getElementById('error_confirmation').scrollIntoView();

		bool = false;
	} 

	if (!ValidCaptcha())
	{
		if(bool==0)
			printHTML("#error_captcha","Ce n'est pas Einstein !");
		$("#error_captcha").css({
			"color":"red",
			"font-size": "80%"
		});
		if(bool==1)
			printHTML("#error_captcha","This is not Einstein !");
		$("#error_captcha").css({
			"color":"red",
			"font-size": "80%"
		});
		document.getElementById('error_captcha').scrollIntoView();

		return false;

	}

	return bool;
}


function enregistre(prenom, nom, numero, email, mdp, confirmation) 
{
	var userlang = navigator.language || navigator.userLanguage; 
	document.cookie = "language="+userlang+"; expires=Thu, 21 Dec 2021 12:00:00 UTC;";

	openWaiter();
	$.ajax({
		type : "POST",
		url : "createuser",
		async: false,
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
			openJModal(2000,"Une erreur s'est produite!");


		}
	});




}




function traiteReponseEnregistrement(rep) 
{
	if (rep.error != undefined)  
	{
		//func_erreur_inscription(rep.error);
		if(bool==0)
			openJModal(2000,"Une erreur s'est produite");
		if(bool==1)
			openJModal(2000,"An error has been occurred");

	}
	else if (rep.message!=undefined)
	{
		if (rep.message!="1") //func_erreur_inscription(rep.message);
			openJModal(2000,rep.message);

		else window.location.href = dashboard_jsp;
	}
	else 
	{
		window.location.href = dashboard_jsp;
	}

}



function func_erreur_inscription(msg)
{
	printHTML("#error_email",msg);
	$("#error_email").css({
		"color":"red",
		"font-size": "80%"
	});
	document.getElementById('error_email').scrollIntoView();

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

