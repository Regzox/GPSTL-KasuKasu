bool=0;
if(readCookie("lang") == "en")
	bool=1;
else
	if(readCookie("lang") == "fr")
		bool=0;


function Loan(loan_id,item,title,debut,fin,owner,ownername){
	//alert("new Loan("+loan_id+","+item+","+title+","+debut+","+fin+")");
	this.loan_id=loan_id;
	this.item=item;
	this.title=title;
	this.debut=debut;
	this.fin=fin;
	this.owner=owner;
	this.ownername=ownername;

} 

/**
 * Query to request an object for loaning.
 * 
 * @param id - id of the object requested
 * @param begin - Begin date of the request
 * @param end - End date of the request
 * @returns
 */
function request_item_query(id, begin, end){	
	openWaiter();
	$.ajax({
		type : "POST",
		url : RequestItemServlet,
		data : "id=" +id +"&debut="+ begin + "&fin=" + end,
		dataType : "JSON",
		success : request_item_query_response,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
			openJModal(2000,"Une erreur s'est produite!");
		}
	});
}
function goToLoanRequests(){gotoURL(userLoans_jsp);}
function request_item_query_response(rep){
	if(bool==0)
		openJModal(2000,
				"Une demande sur cet objet a &eacute;t&eacute; envoy&eacute;e ! "+
				"<div class='center'>\n" + 
					"<div class='btn btn-primary btn-xs' id='validate_dates_button'>Ok</div>\n"+ 
				"</div>\n" 
				,goToLoanRequests
		);
	if(bool==1)
		openJModal(2000,
				"A request for this item has been sent ! "+
				"<div class='center'>\n" + 
					"<div class='btn btn-primary btn-xs' id='validate_dates_button'>Ok</div>\n" +
				"</div>\n"
				,goToLoanRequests
		);
}


//Request item process
//1) Show date/time dialog
//2) Call request_item_query
function request_item(id){	

	if(bool==0)
		// Add DataPickerHTML
		$( "#datePickerDialog" ).html(
				"<div id='myModal' class='modal'>\n" +
				"<div class='modal-content'>\n"+
				"<span class='close'>&times;</span>\n"+
				"<h1>Date de début et de fin du prêt souhaités</h1>\n"+
				"<strong id='date_error'></strong>\n" +
				"<table style='margin: auto;'>\n"+
				"<tr>\n"+
				"<td>\n"+
				"<div class='begin_section'>\n" +
				"<span>Date de début du prêt</span>\n" +
				"<div id='date_begin'></div>\n" +
				"</div>\n" +
				"</td>\n"+
				"<td>\n"+

				"<div class='end_section'>\n" +
				"<span>Date de fin du prêt</span>\n" +
				"<div id='date_end'></div>\n" +
				"</div>\n" +
				"</td>"+
				"</div>\n"+
				"</tr>"+
				"</table>"+
				"<div class='btn btn-primary btn-xs' id='validate_dates_button'>Ok</div>" +
				"</div>\n"

		);
	if(bool==1)
		$( "#datePickerDialog" ).html(
				"<div id='myModal' class='modal'>\n" +
				"<div class='modal-content'>\n"+
				"<span class='close'>&times;</span>\n"+
				"<h1>Loan start and end dates desired </h1>\n"+
				"<strong id='date_error'></strong>\n" +
				"<table style='margin: auto;'>\n"+
				"<tr>\n"+
				"<td>\n"+
				"<div class='begin_section'>\n" +
				"<span>Loan start date</span>\n" +
				"<div id='date_begin'></div>\n" +
				"</div>\n" +
				"</td>\n"+
				"<td>\n"+

				"<div class='end_section'>\n" +
				"<span>Loan end date</span>\n" +
				"<div id='date_end'></div>\n" +
				"</div>\n" +
				"</td>"+
				"</div>\n"+
				"</tr>"+
				"</table>"+
				"<div class='btn btn-primary btn-xs' id='validate_dates_button'>Ok</div>" +
				"</div>\n"

		);
	var str_date_begin = null;
	var str_date_end = null;

	// Define jquery data pickers
	$('#date_begin').datepicker({

		dateFormat:'mm/dd/yy', minDate: new Date(),	

		// Update brother's minDate
		onSelect: function(dateText, inst) {
			// Destroy old brother
			jQuery("#date_end").datepicker("destroy");

			str_date_end = null;

			// Restrict end date range
			$('#date_end').datepicker(
					{dateFormat:'mm/dd/yy', minDate: new Date(dateText),
						onSelect: function(dateText, inst) {
							str_date_end=new Date(dateText);

						}

					}

			);

			// Update end and begin dates
			if ( new Date(dateText) > str_date_end )
				str_date_end=  new Date(dateText);
			str_date_begin=new Date(dateText);  
		}
	});

	// Init date of end with today's date.
	$('#date_end').datepicker({
		dateFormat:'mm/dd/yy', minDate: new Date(),	
		onSelect: function(dateText, inst) {
			str_date_end=new Date(str_date_end); 

		}

	});

	// Show popUp
	$("#myModal").css("display", "block");
	$(".close").click( 
			function() {$("#myModal").css("display", "none");}

	);


	$("#validate_dates_button").click(function() {
		if( str_date_begin != null && str_date_end != null ){
			// Reformat begin
			var format_date_begin ="";
			format_date_begin += str_date_begin.getDate();
			format_date_begin += "-";
			var month_index =  str_date_begin.getMonth() +1;
			if ( month_index < 10 ) format_date_begin  += "0";
			format_date_begin += month_index;  
			format_date_begin += "-";
			format_date_begin += str_date_begin.getFullYear();

			// Reformat end
			var format_date_end ="";
			format_date_end  += str_date_end.getDate();    
			format_date_end  += "-";
			var month_index =  str_date_end.getMonth()+1;
			if ( (month_index) < 10 ) format_date_end  += "0";
			format_date_end  += month_index;       
			format_date_end  += "-";                      
			format_date_end  += str_date_end.getFullYear();     

			$("#myModal").css("display", "none");
			request_item_query(id, format_date_begin, format_date_end);
		}
		else{
			if(bool==0)
				// Show popUp
				$("#date_error").html("Veuillez sélectionner des dates de début et de fin du prêt.");
			if(bool==1)
				// Show popUp
				$("#date_error").html("Please, select the loan start and end dates.");
		}
	});
}



function accept_item_request(id_applicant, id_item){	
	alert("OO")
	openWaiter();
	$.ajax({
		type : "POST",
		url : AcceptRequestsServlet,
		data : "applicant="+id_applicant+"&item="+id_item,
		dataType : "JSON",
		success : accept_item_request_response,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
			if(bool==0)
				openJModal(2000,"Une erreur s'est produite");
			if(bool==1)
				openJModal(2000,"An error has occured");
		}
	});
}
function goToApplicants(){gotoURL(applicants_jsp);}
function accept_item_request_response(rep){
	if(bool==0)
		openJModal(2000,
				"Vous venez d'accepter cette requête. Votre ami(e) en sera inform&eacute;(e)."
				,goToApplicants
		);
	if(bool==1)
		openJModal(2000,
				"You have accepted this request. Your friend will be informed."
				,goToApplicants
		);
}


function refuse_item_request(id_applicant,id_item){	
	//alert("app:"+id_applicant+"itm:"+id_item)
	openWaiter();
	$.ajax({
		type : "POST",
		url : RefuseRequestsServlet,
		data : "applicant="+id_applicant+"&item="+id_item,
		dataType : "JSON",
		success : refuse_item_request_response,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
			if(bool==0)
				openJModal(2000,"Une erreur s'est produite");
			if(bool==1)
				openJModal(2000,"An error has occured");
		}
	});
}
function refuse_item_request_response(rep){
	if(bool==0)
		openJModal(2000,
				"Vous venez de refuser cette requête. Votre ami(e) en sera inform&eacute;(e)."
				,goToApplicants
		);
	if(bool==1)
		openJModal(2000,
				"You have refused this request. Your friend will be informed."
				,goToApplicants
		);
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
list_loaning_response = function(json){
	var jsob =JSON.parse(JSON.stringify(json),mirror);
	loans = jsob.loans;
	if(jsob.error==undefined){

		var fhtm="<div id=\"loansBox\" align=\"center\">\n<table>";

		if(loans.length==0 && bool==0)	
			fhtm+="<h3>Vous n'avez emprunté aucun objet.</h3>";
		if(loans.length==0 && bool==1)	
			fhtm+="<h3>You haven't borrowed any item.</h3>";

		for(var i in loans)
			fhtm+= loans[i].getHTML();

		fhtm+="</table></div>\n";
		printHTML("#found-loans",fhtm);

		for (var loan of loans) {
			//console.log(loan);
			$("#loanBox" + loan.loan_id).append(makeReturnItemButton(loan));
		}

	}else
		console.log("server error ! : " +jsob.error+"\n");
}



function find_user_loans_request(){	
	$.ajax({
		type : "GET",
		url : ApplicantRequestsSetvlet,
		dataType : "JSON",
		success : list_loaning_request_response,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}
list_loaning_request_response = function(json){
	var jsob =JSON.parse(JSON.stringify(json),mirror);
	loans_requests = jsob.requests;
	if(jsob.error==undefined){

		var fhtm="<div id=\"loansBox\" align=\"center\">\n<table>";

		if(loans_requests.length==0 && bool==0)	
			fhtm+="<h3>Vous n'avez demandé aucun objet.</h3>";
		if(loans_requests.length==0 && bool==1)	
			fhtm+="<h3>You haven't request any item.</h3>";

		for(var i in loans_requests)
			fhtm+= loans_requests[i].getHTML();

		fhtm+="</table></div>\n";
		printHTML("#found-loans-requests",fhtm);

	}else
		console.log("server error ! : " +jsob.error+"\n");
}


Loan.prototype.getHTML=function(){  
	//alert("Loan ->getHtml ");
	var s;
	s="<tr><td class=\"loanBox\" id=\"loanBox"+ this.loan_id +"\">";
	s+="<span class=\"item-title\" id=\"item-title"+ this.loan_id +"\">";
	s+="<a href=" + item_jsp + "?id="+ this.item +"&title="+ this.title +">";
	s+="<b>"+ this.title +"</b>";
	s+="</a>";
	s+="</span>\n";	
	s+="<span class=\"item-infos\">";
	s+="<span class=\"visible-item-info\" id=\"item-owner-info"+this.id+"\"> de <a href=/KasuKasu/restricted/memberprofile.jsp?id="+this.owner+">"+this.ownername+"</a></span>";
	s+="</span> ";
	s+="<span class=\"loan-more\">";
	s+="<span class=\"loan-date\" id=\"loan-date"+this.id+"\"> du "+this.debut+"</span>\n";
	s+="<span class=\"loan-date\" id=\"loan-date"+this.id+"\"> au "+this.fin+"</span>\n";
	s+="</span>";
	s+="</td></tr>\n";
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