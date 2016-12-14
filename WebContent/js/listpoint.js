function userPoints(){
		$.ajax({
			type : "GET",
			url : "/KasuKasu/PointsUserList",
			data : "",
			dataType : "JSON",
			success : ProcessRetrievePoints,
			error : function(xhr,status,errorthrown){
				console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
			}
		});
	}


function ProcessRetrievePoints(json) 
{

	var json = JSON.parse(JSON.stringify(json));

	var bodymessage ="";

	
	if(json.expts == undefined || json.expts.length==0){
		bodymessage = bodymessage+
		"<tr style='text-align: left'>" +
		"<td> Aucun point d'échange</td>" +
		"<td></td>"+
		"<td></td>"+
		"<td></td>"+
		"</tr>";
	}else{
		var message = "<table class=\"table\">" +
		"<tr>" +
		"<th>Nom</th><th>Latitude</th><th>Longitude</th>" +
		"</tr>";
		var endmessage ="</table>";
		for (var i = 0; i < json.expts.length; i++) {
			var point = json.expts[i];

			bodymessage = bodymessage+
			"<tr>" +
			"<td>"+point.name+"</td>" +
			"<td>"+point.lat+"</td>"+
			"<td>"+point.lon+"</td>"+
			"<td><a href=\"/KasuKasu/addpoint.jsp"+"\"> Se désabonner de ce point </a></td>"+
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





