function func_erreur(msg) 
{
	var msg_box = "<div id=\"msg-err-connexion\">" + msg + "</div>";
	var tab = $("#msg-err-connexion");
	if (tab.length == 0) 
	{
		$("form").prepend(msg_box);
		$("#msg-err-connexion").css({
			"color" : "red"
		});
	} 
	else 
	{
		tab.replaceWith(msg_box);
		$("#msg-err-connexion").css({
			"color" : "red"
		});
	}
}

function enregistrement(formulaire) 
{
	var nom = formulaire.nom.value;
	var prenom = formulaire.prenom.value;
	var numero = formulaire.numero.value;
	var email = formulaire.email.value;
	var mdp = formulaire.mdp.value;
	var confirmation = formulaire.confirmation.value;


	var ok = verif(mdp,confirmation);
	if (ok) 
	{
		enregistre(nom, prenom, numero, email, mdp);
	}
}

function verif(mdp, confirmation) 
{
	if(mdp != confirmation)	
	{
		func_erreur("Mots de passe incompatibles");
		return false;
	} 
	
	else 
	{
		return true;
	}
}


function enregistre(nom, prenom, numero, email, mdp) 
{
	$.ajax(
			
	{
		type : "GET",
		url : "createuser",
		data : "nom=" + nom + "&prenom=" + prenom + "&numero=" + numero+ "&email="
				+ email + "&mdp=" + mdp,
		success : traiteReponseEnregistrement,
		error : function(XHR, testStatus, errorThrown) 
		{
			alert(XHR + "" + testStatus + "" + errorThrown);
		}
	});

}

function traiteReponseEnregistrement(rep) 
{
	if (rep.error != undefined)  func_erreur(rep.error);
	else 
	{
		window.location.href = "connexion.jsp";
	}
}