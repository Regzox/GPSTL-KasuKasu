function Loan(loan_id,item,title,debut,fin){
	//alert("new Loan("+loan_id+","+item+","+title+","+debut+","+fin+")");
	this.loan_id=loan_id;
	this.item=item;
	this.title=title;
	this.debut=debut;
	this.fin=fin;
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
 {var jsob =JSON.parse(JSON.stringify(json),mirror);
	loans = jsob.loans;
	if(jsob.error==undefined){
		var fhtm="<br><div id=\"loansBox\">";	

		if(loans.length==0)
			fhtm+="<h3>Vous n'avez emprunté aucun objet.</h3>";

		for(var i in loans)
			fhtm+=loans[i].getHTML();
		
		fhtm+="</div>\n"; 
		printHTML("#found-loans",fhtm); 
	}else
		console.log("server error ! : " +jsob.error+"\n");
 }

Loan.prototype.getHTML=function(){  
	//alert("Loan ->getHtml ");
	var s;
	s="<div class=\"loanBox\" id=\"loanBox"+this.id+"\">";
	s+="<div class=\"item-title\" id=\"item-title"+this.id+"\">";
	s+="<a href=/KasuKasu/item.jsp?id="+this.item+"&title="+this.title+">";
	s+="<b>"+this.title+"</b>";
	s+="</a>";
	s+="</div>\n";	
	s+="<div class=\"item-infos\">";
	//s+="<span class=\"visible-item-info\" id=\"item-owner-info"+this.id+"\">"+this.owner+"</span>";//TODO access profil or contact
	s+="</div> ";
	s+="<div class=\"loan-more\">";
	//s+="<span class=\"loan-date\" id=\"loan-date"+this.id+"\">"+this.debut+"</span>\n";
	//s+="<span class=\"loan-date\" id=\"loan-date"+this.id+"\">"+this.fin+"</span>\n";
	s+="</div>";
	s+="</div><hr><br>\n";
	return s;
};


function refresh(result){
	if(result.error!=undefined)
		fillNOTIFIER(result.error);
	else
	window.location.reload();
}
