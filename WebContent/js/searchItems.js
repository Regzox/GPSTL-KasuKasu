function searchItems(form){
	printHTML("#notifier","");
	if (checkQuery(form.iquery.value))		
		searchMRItems(form.iquery.value);

}


function checkQuery(query){
	if(query.length==0)
		return false; //do nothing
	return true;
}


function searchMRItems(query){
	$.ajax({
		type : "GET",
		url : "searchitems",
		data : "query=" +query,
		dataType : "JSON",
		success : printFoundItems,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}

function printFoundItems(rep) {
	printHTML("#found-items", JSON.stringify(rep))
	/*var message = "<table class=\"table\">" +
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
	func_message(div);*/
}


function func_message(msg){
	printHTML("#notifier",msg);
}


function printHTML(dom,htm){ 
	$(dom).html(htm);
}





