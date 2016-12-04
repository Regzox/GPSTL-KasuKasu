function finduser(form) 
{
	printHTML("#notifier","");
	if (form.value.value.length==0)
		return; 
		findUserJS(form.value.value);
	
}

function findUserJS(valuev) 
{
	$.ajax({
		type : "GET",
		url : "/KasuKasu/finduser",
		data : {value : valuev},
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
	var nb=0;
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
				//Skip if the user is yourself
				if(z==rep.id)
					return;
				nb++;
				bodymessage = bodymessage+
				"<tr>" +
				"<td>"+x+"</td>" +
				"<td>"+y+"</td>"+
				"<td><a href=\"/KasuKasu/restricted/memberprofile.jsp?id="+z+"\"> Voir Profil </a></td>"+
				"<td><a href=\"/KasuKasu/friendsmanagement?typeOfRequest=3&id="+z+"\"> Ajouter amis </a></td>"+
				"</tr>";
			}else{
				message="Aucun utilisateur ne correspond.";
				bodymessage="";
				endmessage="";
			}
		});
	if(nb==0){
		message="Aucun utilisateur ne correspond.";
		bodymessage="";
		endmessage="";
	}
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





