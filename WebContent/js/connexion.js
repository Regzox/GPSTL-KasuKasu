function connexion(form) 
{
	var mail = form.mail.value;
	var pass = form.pass.value;

	var ok = checkInput(mail, pass);
	if (ok) 
	{
		printHTML("#notifier","");
		checkConnexion(mail, pass);
	}
}



function checkInput(mail, pass) 
{
	if(mail.length==0)
	{
		//func_error_login("Email missing");
		func_error_login("Email manquant");
		return false;
	}

	if(pass.length==0)
	{
		//func_error_login("Password missing");
		func_error_login("Mot de passe manquant");
		return false;
	}
	
	if(pass.length<8)
	{
		//func_error_login("Password too short");
		func_error_login("Mot de passe trop court");
		return false;
	}
	else 
	{
		return true;
	}
}


function checkConnexion(mailv, passv) 
{
	$.ajax({
		type : "POST",
		url : "/KasuKasu/connectuser",
		data : {mail : mailv, pass : passv},
		dataType : "json",
		success : ProcessLoginRequest,
		error : function(xhr,status,errorthrown){
			alert(xhr.responseText);
		}
	});
}




function ProcessLoginRequest(rep) 
{
	if (rep.error != undefined)  
	{
		func_error_login(rep.error);
	}
	else 
	{
		window.location.href = "/KasuKasu/searchitems.jsp";
	}
}


function func_error_login(msg)
{
		printHTML("#notifier",msg);
		$("#notifier").css({
			"color":"red",
			"font-size": "80%"
		})
}


function printHTML(dom,htm)
{ 
	$(dom).html(htm);
}





