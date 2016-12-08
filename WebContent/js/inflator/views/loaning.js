function Loan(loan_id,applicant,item,when,debut,fin){
	alert("new Loan("+loan_id+","+applicant+","+item+","+when+","+debut+","+fin+")");
	this.id=id;
	this.name=name;
	this.type=type;
	this.owner=owner;
	this.date=date;
} 


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
	//alert("afficher modal"); //afficher modal TODO
	refresh(rep);
}

function accept_item_request(id_applicant, id_item){	
	$.ajax({
		type : "POST",
		url : "acceptrequests",
		data : "applicant="+id_applicant+"&item="+id_item,
		dataType : "JSON",
		success : loaning_request_response,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}

function refuse_item_request(id){	
	$.ajax({
		type : "POST",
		url : "refuserequests",
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
 {alert(JSON.stringify(json))
	var jsob =JSON.parse(JSON.stringify(json));
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



function refresh(result){
	if(result.error!=undefined)
		fillNOTIFIER(result.error);
	else
	window.location.reload();
}
