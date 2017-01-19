bool=0;
if(document.cookie.search("lang=en")!=-1)
	bool=1;
else
	if(document.cookie.search("lang=fr")!=-1)
		bool=0;
function Loan(loan_id,item,title,debut,fin){
	//alert("new Loan("+loan_id+","+item+","+title+","+debut+","+fin+")");
	this.loan_id=loan_id;
	this.item=item;
	this.title=title;
	this.debut=debut;
	this.fin=fin;
} 


function request_item_query(id){	
	$.ajax({
		type : "POST",
		url : RequestItemServlet,
		data : "id=" +id,
		dataType : "JSON",
		success : loaning_request_response,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}

//Request item process
//1) Show date/time dialog
//2) Send query
function request_item(id){	
// TODO: Uncomment for date begin end dialog
//	// Add DataPickerHTML
//	$( "#datePickerDialog" ).html(
//			"<div id='myModal' class='modal'>\n" +
//				"<div class='modal-content'>\n"+
//				 "<span class='close'>&times;</span>"+
//				 "<table>"+
//				 "<tr>"+
//				 	"<td>"+
//					"<div class='begin_section'>\n" +
//						"<div id='date_begin'></div>\n" +
//						"</div>\n" +
//						"</td>"+
//						"<td>"+
//						"<div class='end_section'>\n" +
//						"<div id='date_end'></div>\n" +
//						"</div>\n" +
//						"</td>"+
//					"</div>\n"+
//				"</tr>"+
//				"</table>"+
//			"</div>\n"
//	);
//
//	
//	 
//	// Show jquery data pickers 
//	$('#date_begin').datepicker();
//	$('#date_end').datepicker();
//	
//	// Show popUp
//	$("#myModal").css("display", "block");
//	$(".close").click( 
//			function() {$("#myModal").css("display", "none");}
//			
//	);

	request_item_query(id);

}


function loaning_request_response(rep){	
	//alert("afficher modal"); //afficher modal TODO
	refresh(rep);
}

function accept_item_request(id_applicant, id_item){	
	$.ajax({
		type : "POST",
		url : AcceptRequestsServlet,
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
		url : RefuseRequestsServlet,
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
		url : ApplicantLoaningServlet,
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

	if(loans.length==0 && bool==0)	
		fhtm+="<h3>Vous n'avez emprunté aucun objet.</h3>";
	if(loans.length==0 && bool==1)	
		fhtm+="<h3>You haven't borrowed any item.</h3>";

	for(var i in loans)
		fhtm+= loans[i].getHTML();

	fhtm+="</div>\n"; 
	printHTML("#found-loans",fhtm);

	for (var loan of loans) {
		console.log(loan);
		$("#item-title" + loan.loan_id).append(makeReturnItemButton(loan));
	}

}else
	console.log("server error ! : " +jsob.error+"\n");
}

Loan.prototype.getHTML=function(){  
	//alert("Loan ->getHtml ");
	var s;
	s="<div class=\"loanBox\" id=\"loanBox"+ this.loan_id +"\">";
	s+="<div class=\"item-title\" id=\"item-title"+ this.loan_id +"\">";
	s+="<a href=" + item_jsp + "?id="+ this.item +"&title="+ this.title +">";
	s+="<b>"+ this.title +"</b>";
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

function makeReturnItemButton(loan) {

	var button = window.document.createElement("button");
	if(bool==0)	
		$(button).html("Rendre");
	if(bool==1)	
		$(button).html("Return");
	$(button).click( function () {
		$.post(ReturnItemServlet, {
			loan_id : loan.loan_id
		})
		.done( function () {
			window.location.reload();
		});
	});

	return button;
}
