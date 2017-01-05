var bool;
if(document.cookie.search("lang=en")!=-1)
	bool=1;
else
	if(document.cookie.search("lang=fr")!=-1)
		bool=0;
function retrieveFriendsFunc() 
{
	printHTML("#notifier","");
	retrieveFriends(1);
}

function retrieveRequestsFunc() 
{
	printHTML("#notifier","");
	retrieveRequests(2);
}

function retrieveFriends(requestv) 
{
	$.ajax({
		type : "POST",
		url : "/KasuKasu/friendsandrequest",
		data : {typeOfRequest : requestv},
		dataType : "JSON",
		success : ProcessRetrieveFriends,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}

function retrieveRequests(requestv) 
{
	$.ajax({
		type : "POST",
		url : "/KasuKasu/friendsandrequest",
		data : {typeOfRequest : requestv},
		dataType : "JSON",
		success : ProcessRetrieveRequests,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}

function ProcessRetrieveFriends(rep) 
{
	
	var message; 
	var endmessage ="</table>";
	var bodymessage ="";
	
	
	if(bool==1)
	{
		message = "<table class=\"table\">" +
		"<tr>" +
		"<th>Last name</th><th>First name</th><th>Profile</th>" +
		"</tr>";
	}
	else
	{
		if(bool==0)
		{
			message = "<table class=\"table\">" +
			"<tr>" +
			"<th>Nom</th><th>Prenom</th><th>Profil</th>" +
			"</tr>";
		}
	}
	var jsonData =rep.result;//= JSON.parse(rep);
	if((jsonData.users == undefined || jsonData.users.length == 0)){
		if(bool==0)
		bodymessage = bodymessage+
		"<tr style='text-align: left'>" +
		"<td> Aucun</td>" +
		"<td> Amis</td>"+
		"<td></td>"+
		"<td></td>"+
		"<td></td>"+
		"</tr>";
		else
			bodymessage = bodymessage+
			"<tr style='text-align: left'>" +
			"<td> No</td>" +
			"<td> Friend </td>"+
			"<td></td>"+
			"<td></td>"+
			"<td></td>"+
			"</tr>";
			
	}else{
		if(bool=0)
		for (var i = 0; i < jsonData.users.length; i++) {
			var user = jsonData.users[i];

			bodymessage = bodymessage+
			"<tr>" +
			"<td>"+user.name+"</td>" +
			"<td>"+user.firstname+"</td>"+
			"<td><a href=\"/KasuKasu/restricted/memberprofile.jsp?id="+user.id+"\"> Afficher le profil </a></td>"+
			"<td><a href=\"/KasuKasu/friendsmanagement?typeOfRequest=2&id="+user.id+"\"> Supprimer </a></td>"
			"</tr>";
		}
		else
			for (var i = 0; i < jsonData.users.length; i++) {
				var user = jsonData.users[i];

				bodymessage = bodymessage+
				"<tr>" +
				"<td>"+user.name+"</td>" +
				"<td>"+user.firstname+"</td>"+
				"<td><a href=\"/KasuKasu/restricted/memberprofile.jsp?id="+user.id+"\"> Show profile </a></td>"+
				"<td><a href=\"/KasuKasu/friendsmanagement?typeOfRequest=2&id="+user.id+"\"> Delete </a></td>"
				"</tr>";
			}	
	}
	var div=(message+bodymessage+endmessage);
	func_message(div);
}

function ProcessRetrieveRequests(rep) 
{
	var message;
	
	if(bool==0)
	{
		message = "<table class=\"table\">" +
	"<tr>" +
	"<th>Nom</th><th>Prenom</th><th>Profil</th><th>Accepter</th><th>Refuser</th>" +
	"</tr>";
	}
	else
	{
		if(bool==1)
		{
			message = "<table class=\"table\">" +
			"<tr>" +
			"<th>Last name</th><th>First name</th><th>Profile</th><th>Accept</th><th>Decline</th>" +
			"</tr>";
		}
	}	
	var endmessage ="</table>";
	var bodymessage ="";

	var jsonData = rep.result;//JSON.parse(rep);
	if(jsonData.users == undefined || jsonData.users.length == 0){
		
		if(bool==0)
		bodymessage = bodymessage+
		"<tr style='text-align: left'>" +
		"<td> Aucune</td>" +
		"<td> Requête</td>"+
		"<td></td>"+
		"<td></td>"+
		"<td></td>"+
		"<td></td>"+
		"</tr>";
		else
			if(bool==1)
				bodymessage = bodymessage+
				"<tr style='text-align: left'>" +
				"<td> No</td>" +
				"<td> Request</td>"+
				"<td></td>"+
				"<td></td>"+
				"<td></td>"+
				"<td></td>"+
				"</tr>";
	}else{
		if(bool==0)
		for (var i = 0; i < jsonData.users.length; i++) {
			var user = jsonData.users[i];

			bodymessage = bodymessage+
			"<tr>" +
			"<td>"+user.name+"</td>" +
			"<td>"+user.firstname+"</td>"+
			"<td><a href=\"/KasuKasu/restricted/memberprofile.jsp?id="+user.id+"\"> Afficher le profil </a></td>"+
			"<td><a href=\"/KasuKasu/friendsmanagement?typeOfRequest=1&id="+user.id+"\"> Accepter la requête </a></td>"+
			"<td><a href=\"/KasuKasu/friendsmanagement?typeOfRequest=4&id="+user.id+"\"> Refuser le requête </a></td>"
			"</tr>";
		}
		else
			if(bool==1)
				for (var i = 0; i < jsonData.users.length; i++) {
					var user = jsonData.users[i];

					bodymessage = bodymessage+
					"<tr>" +
					"<td>"+user.name+"</td>" +
					"<td>"+user.firstname+"</td>"+
					"<td><a href=\"/KasuKasu/restricted/memberprofile.jsp?id="+user.id+"\"> Show profile </a></td>"+
					"<td><a href=\"/KasuKasu/friendsmanagement?typeOfRequest=1&id="+user.id+"\"> Accept the request </a></td>"+
					"<td><a href=\"/KasuKasu/friendsmanagement?typeOfRequest=4&id="+user.id+"\"> Decline the request </a></td>"
					"</tr>";
				}
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





