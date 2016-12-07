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

function refresh(result){
	if(result.error!=undefined)
		fillNOTIFIER(result.error);
	else
	window.location.reload();
}
