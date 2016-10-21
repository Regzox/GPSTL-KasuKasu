function finduser(form) 
{
	var search = form.search.value;
	var val = form.value.value;

	var ok = checkInput(search, val);
	if (ok) 
	{
		printHTML("#notifier","");
		findUserJS(search, val);
	}
}



function checkInput(search, val) 
{
	if(val.length==0)
	{
		func_message("Champ Vide");
		return false;
	}
	else 
	{
		return true;
	}
}


function findUserJS(searchv, valuev) 
{
	$.ajax({
		type : "POST",
		url : "/KasuKasu/finduser",
		data : {search : searchv, value : valuev},
		dataType : "JSON",
		success : ProcessFindUser,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}

function ProcessFindUser(rep) 
{
		var message = "<table class=\"table\">" +
		"<tr>" +
		"<th>Nom</th><th>Prenom</th><th>Profil</th>" +
		"</tr>";
		var endmessage ="</table>";

		var bodymessage ="";
		if(rep.users != undefined)
		$.each(rep.users, function(user, profile) {
			var x,y,z;
			if(user !='warning'){
			$.each(profile, function(field, value) {
				//console.log(field); console.log(value);
				if(field=='name')
					x=value;
				if(field=='firstname')
					y=value;
				if(field=='id')
					z=value;
			});
			
			bodymessage = bodymessage+
				"<tr>" +
				"<td>"+x+"</td>" +
				"<td>"+y+"</td>"+
				"<td><a href=\"profil/?id="+z+"\"> Voir Profil </a></td>"+
				"</tr>";
			}else{
				message="Aucun utilisateur ne correspond.";
				bodymessage="";
				endmessage="";
			}
		});
	var div=(message+bodymessage+endmessage);
	func_message(div);
}




function func_message(msg)
{
	printHTML("#notifier",msg);
}


function printHTML(dom,htm)
{ 
	$(dom).html(htm);
}




