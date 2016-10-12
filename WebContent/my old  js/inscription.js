/**
 * *TRAITEMENT DU FORMULAIRE D INSCRIPTION
 * */
//Check formulaire Inscription
function enregistrement(formulaire){
	var login=formulaire.log.value;
	var nom=formulaire.nom.value;
	var prenom=formulaire.pnom.value;
	var pwd=formulaire.psw.value;
	var vpwd=formulaire.vpsw.value;
	//alert("Connefion.js/formE,msg: login="+login+" et pass="+pwd+" et nom="+nom+" et prenom="+prenom+" et verification="+vpwd);
	var ok=verif_formE(nom,prenom,login,pwd,vpwd);
	if(ok){
		printHTML("#msg_err_inscription",wait_ci());
		registration(login,nom,prenom,pwd);
	}
}	

//Inscription au niveau serveur
function registration(login,nom,prenom,pwd){
	//alert("registration -> AJAX");

	$.ajax({
		type:"GET",
		url:"user/create",
		data:"pnom="+prenom+"&nom="+nom+"&login="+login+"&pass="+pwd,
		dataType:"json",
		success:traiteReponseInscription,
		error:function(jqXHR,textStatus,errorThrown) {
			alert("jqXHR:"+JSON.stringify(jqXHR)+"\n textStatus:"+textStatus+"\n errorThrown:"+errorThrown);
		}
	});

	//alert("Enregistrement reussi \n login="+login+" \n password="+pwd+" \n nom="+nom+" \n prenom="+prenom);
}


function traiteReponseInscription(rep){
	//alert("yata");
	if (rep.erreur!=undefined){
		//alert("yata erreur : "+rep.erreur);
		func_erreur_inscription(rep.erreur);
	}else if (rep.message!=undefined){
		//alert("yata message: "+rep.message);
		func_erreur_inscription(rep.message);
	}else{ //CODE INACCESSIBLE ON RENVOIE SOIT UN MESSAGE SOIT UNE ERREUR DONC ON NE RENTRE JAMAIS ICI / laisse pour debug futur
		alert("yata redirect : "+JSON.stringify(rep)); 
		windows.location.href="main.jsp";
	}
}


//Verifications 
function verif_formE(nom,prenom,login,pwd,vpwd){
	//Verification login
	if(login.length==0){
		//alert("login manquant");
		func_erreur_inscription("login manquant");
		$("#log2").css({
			"border-color":"yellow",
			"border-width":"2px"
		});
		//alert("login rouge");
		return false;
	}
	//Verification password
	if(pwd.length==0){
		//alert("mot de passe manquant");
		func_erreur_inscription("mot de passe manquant");
		$("#psw").css({
			"border-color":"yellow",
			"border-width":"2px"
		});
		//alert("pass rouge");
		return false;
	}
	/** //Verification nom
	if(nom.length==0){
		alert("nom manquant");
		return false;
	}
	//Verification prenom
	if(prenom.length==0){
		alert("prenom manquant");
		return false;
	}*/
	//Verification taille password
	if(pwd.length<8){
		//alert("mot de passe trop court");
		func_erreur_inscription("mot de passe trop court");
		$("#psw").css({
			"border-color":"yellow",
			"border-width":"2px"
		});
		//alert("pass length rouge");
		return false;
	}
	//Verification correspondance password
	if(pwd!=vpwd){  //verifier si doit use equals
		//alert("mots de passe non correspondants");
		func_erreur_inscription("mots de passe non correspondants");
		$("#vpd").css({
			"border-color":"yellow",
			"border-width":"2px"
		});
		$("#psw").css({
			"border-color":"yellow",
			"border-width":"2px"
		});
		$("#msg_err_inscription").css({
			"margin-left":"10px",
			"font-size":"medium"
		});
		//alert("verif rouge");
		return false;
	}
	//alert("Connexion.js/formE,msg: Verrification all right");
	return true;
}

function func_erreur_inscription(msg){
	//alert("debut inscr err");
	printHTML("#msg_err_inscription",msg);
}