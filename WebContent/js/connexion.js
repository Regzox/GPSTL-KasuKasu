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
		func_error_login("Mail missing");
		return false;
	}

	if(pass.length==0)
	{
		func_error_login("Password missing");
		return false;
	}
	
	if(pass.length<8)
	{
		func_error_login("Password too short");
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
		alert("TEST");
		window.location.href = kasukasu.private.dashboard;
	}
}


function func_error_login(msg)
{
		printHTML("#notifier",msg);
}


function printHTML(dom,htm)
{ 
	$(dom).html(htm);
}





