function request_item(id){	
	$.ajax({
		type : "POST",
		url : "requestitem",
		data : "id=" +id,
		dataType : "JSON",
		success : loaning_request_response,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}

function loaning_request_response(rep){	
	alert(rep); //afficher modal
}

function accept_item_request(id){	
	alert();
	$.ajax({
		type : "POST",
		url : "acceptrequests",
		data : "id=" +id,
		dataType : "JSON",
		success : loaning_request_response,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}


function find_user_loans(){	
	$.ajax({
		type : "GET",
		url : "applicantloaning",
		dataType : "JSON",
		success : list_loaning_response,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}
list_loaning_response = function(json)
 { var jsob =JSON.parse(JSON.stringify(json));
	loans = jsob.loans;
	if(jsob.error==undefined){
		var fhtm="<br><div id=\"loansBox\">";	

		if(items.length==0)
			fhtm+="<h3>Vous n'avez emprunté aucun objet.</h3>";

		for(var i in loans){
			fhtm+=loans[i];
		}		
		fhtm+="</div>\n"; 
		printHTML("#found-loans",fhtm); 
	}else
		console.log("server error ! : " +jsob.error+"\n");
 }
function printHTML(dom,htm)
{ 
	$(dom).html(htm);
}

