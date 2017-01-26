bool=0;
if(readCookie("lang") == "en")
	bool=1;
else
	if(readCookie("lang") == "fr")
		bool=0;

function finduser(query) 
{
	console.log("query="+query);
	printHTML("#notifier","");
	if (query.length==0)return; 
	findUserJS(query);	
}

function findUserJS(valuev) 
{
	$.ajax({
		type : "GET",
		url : FindUserServlet,
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
	var message = "";

	if(bool==0)
		message= "<table class=\"table\">" +
		"<tr>" +
		"<th>Nom</th><th>Prenom</th><th>Profil</th><th>Contact</th><th>Statut</th>" +
		"</tr>";
	if(bool==1)
		message="<table class=\"table\">" +
		"<tr>" +
		"<th>Last name</th><th>First name</th><th>Profile</th><th>Contact</th><th>Status</th>" +
		"</tr>";

	var endmessage ="</table>";

	var bodymessage ="";
	var nb=0;
	if(rep.users != undefined)
		$.each(rep.users, function(user, profile) {
			var x,y,z,f;
			if(user !='warning'){
				$.each(profile, function(field, value) {
					//console.log(field); console.log(value);
					if(field=='name')
						x=value;
					if(field=='firstname')
						y=value;
					if(field=='id')
						z=value;
					if(field=='friend')
						f=value;
				});
				//Skip if the user is yourself
				if(z==rep.id)
					return;
				nb++;
				if(bool==0){
					bodymessage = bodymessage+
					"<tr style='text-align: left'>" +
					"<td>"+x+"</td>" +
					"<td>"+y+"</td>"+
					"<td><a href=\"" + memberprofile_jsp + "?id="+z+"\"> Voir </a></td>"+
					"<td><a href=\"" + conversation_jsp + "?uther="+z+"&interlocutor="+x+" "+y+" \"> Contacter </a></td>";
					if(f!=undefined && f!=null){
						switch(f){
						case "stranger" :
							bodymessage+="<td><a href=\"" + FriendsManagementServlet + "?typeOfRequest=3&id="+z+"\"> Ajouter aux amis </a></td>";
							break;
						case "friend":
							bodymessage+="<td>Déjà Amis</td>";
							break;
						case "waiting" :
							bodymessage+="<td>Attente de r&eacute;ponse</td>";
							break;
						}
					}


					bodymessage+="</tr>";
				}
				if(bool==1){
					bodymessage = bodymessage+
					"<tr style='text-align: left'>" +
					"<td>"+x+"</td>" +
					"<td>"+y+"</td>"+
					"<td><a href=\"" + memberprofile_jsp + "?id="+z+"\"> Show </a></td>";
					if(f!=undefined && f!=null){
						switch(f){
						case "stranger" :
							bodymessage+="<td><a href=\"" + FriendsManagementServlet + "?typeOfRequest=3&id="+z+"\"> Add to friends </a></td>";
							break;
						case "friend":
							bodymessage+="<td>Already Friends</td>";
							break;
						case "waiting" :
							bodymessage+="<td>Waiting for an Answer</td>"
								break;
						}
					}
					bodymessage+="</tr>";
				}
			}else{
				if(bool==0)
					message="Aucun utilisateur ne correspond.";
				if(bool==1)
					message="No user matches.";
				bodymessage="";
				endmessage="";
			}
		});
	if(nb==0){
		if(bool==0)
			message="Aucun utilisateur ne correspond.";
		if(bool==1)
			message="No user matches.";
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

