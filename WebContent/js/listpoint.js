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
		"<th>Nom</th><th>Latitude</th><th>Longitude</th><th>Portée</th>" +
		"</tr>";
		var endmessage ="</table>";
		for (var i = 0; i < json.expts.length; i++) {
			var point = json.expts[i];

			bodymessage = bodymessage+
			"<tr>" +
			"<td>"+point.name+"</td>" +
			"<td>"+point.lat+"</td>"+
			"<td>"+point.lon+"</td>"+
			"<td>"+point.radius+"m"+"</td>"+
			'<td><a href="#" onclick="Delete(\''+point.id+'\');"> Supprimer</a></td>'+
			'<td><a href=# onclick="Modifier(\'' + point.id + '\',\'' + point.name + '\' ,\'' + point.radius+ '\')"> Modifier</a></td>'+
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

function Delete(id)
{
	$.ajax({
		type : "POST",
		url : "/KasuKasu/DeleteExchangePointServlet",
		data : "id="+id,
		dataType : "JSON",
		success : function (data)
		{

	        if (data.message=="1")
	        	{
	    	       $("#myModal").modal({                    
	    		      "backdrop"  : "static",
	    		      "keyboard"  : true,
	    		      "show"      : true                     
	    		    });
	    	       
	    	       $("#myModal").on('hidden.bs.modal', function () {
	    	           window.location.href = "/KasuKasu/restricted/listpoint.jsp";
	    	       });
	        	}
		},		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});}


function printHTML(dom,htm)
{ 
	$(dom).html(htm);
}

function Modifier(id,old_name,old_radius)
{

    $("#myModal2").modal({                    
	      "backdrop"  : "static",
	      "keyboard"  : true,
	      "show"      : true                     
	    });
  
	$('#save').on(
			'click',
			function(evt)
			{
			    nom = $("#myModal2").find("#nom_input").val();
			    radius = $("#myModal2").find("#radius_input").val();
			    
			    if (nom.length==0) nom = old_name;
			    if (radius.length==0) radius = old_radius;

				updatePoint(id,radius,nom); 

			}
			);
}

function updatePoint(id,radius,name)
{
	$.ajax({
		type : "POST",
		url : "/KasuKasu/updateexchangepoint",
		data : "id=" + id + "&radius=" + radius + "&name=" + name, 
		dataType : "JSON",
		success : function (data)
		{

	        if (data.message=="1")
	        	{
	    	       $("#myModal2").hide(); 

	    	       $("#myModal").modal({                    
	    		      "backdrop"  : "static",
	    		      "keyboard"  : true,
	    		      "show"      : true                     
	    		    });
	    	       
	    	       $("#myModal").on('hidden.bs.modal', function () {
	    	           window.location.href = "/KasuKasu/restricted/listpoint.jsp";
	    	       });
	        	}
		},		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});}





