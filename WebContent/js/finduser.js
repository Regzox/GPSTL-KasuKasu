bool=0;
if(document.cookie.search("lang=en")!=-1)
		bool=1;
	else
		if(document.cookie.search("lang=fr")!=-1)
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
		url : finduser_jsp,
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
		"<th>Nom</th><th>Prenom</th><th>Profil</th>" +
		"</tr>";
	if(bool==1)
		message="<table class=\"table\">" +
		"<tr>" +
		"<th>Last name</th><th>First name</th><th>Profile</th>" +
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
				if(bool==0)
					bodymessage = bodymessage+
					"<tr>" +
					"<td>"+x+"</td>" +
					"<td>"+y+"</td>"+
					"<td><a href=\"" + memberprofile_jsp + "?id="+z+"\"> Voir Profil </a></td>"+
					"<td><a href=\"" + FriendsManagementServlet + "?typeOfRequest=3&id="+z+"\"> Ajouter amis </a></td>"+
					"</tr>";
				if(bool==1)
					bodymessage = bodymessage+
					"<tr>" +
					"<td>"+x+"</td>" +
					"<td>"+y+"</td>"+
					"<td><a href=\"" + memberprofile_jsp + "?id="+z+"\"> Show Profile </a></td>"+
					"<td><a href=\"" + FriendsManagementServlet + "?typeOfRequest=3&id="+z+"\"> Add friend </a></td>"+
					"</tr>";
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

