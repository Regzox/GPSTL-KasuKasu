/**
 * ANAGBLA Joan*/
bool=0;
applicants_shared_div_state_opened=false;
if(readCookie("lang") == "en")	
	bool=1;
else
	if(readCookie("lang") == "fr")
		bool=0;
function Item(id,owner,group,date,longitude,latitude,title,description,permission){
	//alert("new Item("+id+","+title+","+group+","+longitude+","+latitude+","+date+","+description+","+permission+")");
	this.id=id;
	this.owner=owner;
	this.group=group;
	this.date=date;
	this.longitude=longitude;
	this.latitude=latitude;
	this.title=title;
	this.description=description;
	this.permission=permission;
} 


Item.getItemTraiteReponseJSON=function(json){	
	//alert("Item.getItemTraiteReponseJSON raw json -> "+JSON.stringify(json));	
	var jsob =JSON.parse(JSON.stringify(json),/*Item.revival*/mirror);
	items = jsob.items;
//	alert("Item.getItemTraiteReponseJSON cooked jsob -> "+JSON.stringify(jsob));

	if(jsob.error==undefined){
		var fhtm="<br><div id=\"itemsBox\">";	

		if(items.length==0 && bool==0)
			fhtm+="<h3>Il n'y a rien à afficher.</h3>";
		if(items.length==0 && bool==1)
			fhtm+="<h3>Nothing to display.</h3>";
		for(var i in items){
			//alert(JSON.stringify(items[i]));
			if(items[i].permission==1){
				fhtm+=(items[i]).getHTML2();
				//alert("JSOB.htmling : "+items[i].getHTML2());
			}
			else if(items[i].permission === 0){
				fhtm+=(items[i]).getHTML();
				//alert("JSOB.htmling : "+items[i].getHTML());
			}
		}		

		// DatePickerDialog
		fhtm+="<div id='datePickerDialog'></div>"; 
		fhtm+="</div>\n"; 
		//alert("items.html = "+fhtm);  
		printHTML("#found-items",fhtm); 
	}else
		console.log("server error ! : " +jsob.error+"\n");
};


Item.listItemTraiteReponseJSON=function(json){	
	//alert("Item.getItemTraiteReponseJSON raw json -> "+JSON.stringify(json));	
	var jsob =JSON.parse(JSON.stringify(json),/*Item.revival*/mirror);
	items = jsob.items;
//	alert("Item.getItemTraiteReponseJSON cooked jsob -> "+JSON.stringify(jsob));

	if(jsob.error==undefined){
		var fhtm="<br><div id=\"itemsBox\">";	

		if(items.length==0 && bool==0)
			fhtm+="<h3>Il n'y a rien à afficher.</h3>";
		if(items.length==0 && bool==1)
			fhtm+="<h3>Nothing to display.</h3>";
		for(var i in items){
			//alert(JSON.stringify(items[i]));
			if(items[i].permission==1){
				fhtm+=(items[i]).getHTML0();
				//alert("JSOB.htmling : "+items[i].getHTML2());
			}
			else if(items[i].permission === 0){
				fhtm+=(items[i]).getHTML();
				//alert("JSOB.htmling : "+items[i].getHTML());
			}
		}		

		// DatePickerDialog
		fhtm+="<div id='datePickerDialog'></div>"; 
		fhtm+="</div>\n"; 
		//alert("items.html = "+fhtm);  
		printHTML("#found-items",fhtm); 

	}else
		console.log("server error ! : " +jsob.error+"\n");
};

Item.prototype.getHTML0=function(){  
	//alert("Item ->getHtml ");
	var s;
	s="<div class=\"itemBox\" id=\"itemBox"+this.id+"\">";
	s+="<div class=\"item-title\" id=\"item-title"+this.id+"\">";
	s+="<a href=" + item_jsp + "?id="+this.id+"&title="+this.title+">"; 
	s+="<b>"+this.title+"</b>";
	s+="</a>";
	s+="</div>\n";	
	s+="<div class=\"item-infos\">";
	s+="<span style=\"display:none;\" class=\"hiden-item-info\" id=\"item-owner-info"+this.id+"\">"+this.owner+"</span>";
	s+="<span style=\"display:none;\" class=\"hiden-item-info\" id=\"item-group-info"+this.id+"\">"+this.group+"</span>";
	s+="</div> ";
	s+="<div class=\"item-desc\" id=\"item-desc"+this.id+"\">"+this.description+"</div><br>\n";
	s+="<div class=\"item-more\">";
	s+="<span class=\"item-date\" id=\"item-date"+this.id+"\">"+this.date+"</span>\n";
	s+="</div>";
	s+="</div><hr><br>\n";
	return s;
};


Item.prototype.getHTML=function(){  
	//alert("Item ->getHtml ");
	var s;
	s="<div class=\"itemBox\" id=\"itemBox"+this.id+"\">";
	s+="<div class=\"item-title\" id=\"item-title"+this.id+"\">";
	s+="<a href=" + item_jsp + "?id="+this.id+"&title="+this.title+">"; 
	s+="<b>"+this.title+"</b>";
	s+="</a>";
	s+="</div>\n";	
	s+="<div class=\"item-infos\">";
	s+="<span style=\"display:none;\" class=\"hiden-item-info\" id=\"item-owner-info"+this.id+"\">"+this.owner+"</span>";
	s+="<span style=\"display:none;\" class=\"hiden-item-info\" id=\"item-group-info"+this.id+"\">"+this.group+"</span>";
	s+="</div> ";
	s+="<div class=\"item-desc\" id=\"item-desc"+this.id+"\">"+this.description+"</div><br>\n";
	s+="<div class=\"item-more\">";
	s+="<span class=\"item-date\" id=\"item-date"+this.id+"\">"+this.date+"</span>\n";
	if(bool==0)
		s+=" <input style=\"margin-left:50%;\" type=\"button\" value=\"Je le veux\" class=\"btn btn-primary btn-xs\" " +
		"id=\"iwantit_btn"+this.id+"\" OnClick=\"request_item('"+this.id+"');\"/>\n";
	if(bool==1)
		s+=" <input style=\"margin-left:50%;\" type=\"button\" value=\"I want it\" class=\"btn btn-primary btn-xs\" " +
		"id=\"iwantit_btn"+this.id+"\" OnClick=\"request_item('"+this.id+"');\"/>\n";
	s+="</div>";
	s+="</div><hr><br>\n";
	return s;
};


Item.prototype.getHTML2=function(){  
	//alert("Item ->getHtml ");
	var s;
	s="<div class=\"itemBox\" id=\"itemBox"+this.id+"\">";
	s+="<div class=\"item-title\" id=\"item-title"+this.id+"\">";
	s+="<a href=" + item_jsp + "?id="+this.id+"&title="+this.title+">";
	s+="<b>"+this.title+"</b>";
	s+="</a>";
	s+="</div>\n";		
	s+="<div class=\"item-infos\">";
	s+="<span style=\"display:none;\" class=\"hiden-item-info\" id=\"item-group-info"+this.id+"\">"+this.group+"</span>";
	s+="</div> ";
	s+="<div class=\"item-desc\" id=\"item-desc"+this.id+"\">"+this.description+"</div><br>\n";
	s+="<div class=\"item-more\">";
	s+=" <input style=\"float:left;margin-right:5%;\" type=\"image\" " +
	" alt=\"Submit\" width=\"30\" type=\"image\" height=\"30\" " +
	" src=\"icons/Feedback_Filled_50.png\" class=\"iwantit_btn\" " +
	"id=\"unwrap_applicants_btn"+this.id+"\" OnClick=\"javascript:item_applicants('"+this.id+"')\"/>\n";
	s+=" <input style=\"float:left;margin-right:75%;\" type=\"image\" " +
	" alt=\"Submit\" width=\"30\" type=\"image\" height=\"30\" " +
	" src=\"icons/Empty_Trash_Filled-50.png\" class=\"iwantit_btn\" " +
	"id=\"remove_item_btn"+this.id+"\" OnClick=\"javascript:removeItem('"+this.id+"')\"/>\n";
	if(bool==0)
		s+="<input style=\"float:left;\" " +
		"onclick=\"window.location.href='" + ObjectManagementServlet + "?objectId="+this.id+"&data=null'\"" +
		" type=\"button\" class=\"btn btn-primary btn-xs\" name=\"modify\" value=\"modifier\">";
	if(bool==1)
		s+="<input style=\"float:left;\"" +
		" onclick=\"window.location.href='" + ObjectManagementServlet + "?objectId="+this.id+"&data=null'\"" +
		" type=\"button\" class=\"btn btn-primary btn-xs\" name=\"modify\" value=\"modify\">";

	s+="<span  class=\"item-date\" id=\"item-date"+this.id+"\">"+this.date+"</span>\n";
	s+="</div>";
	s+="</div><hr><br>\n";
	return s;
};


function searchMRItems(query){
	$.ajax({
		type : "GET",
		url : SearchItemsServlet,
		data : "query=" +query,
		dataType : "JSON",
		success : Item.listItemTraiteReponseJSON,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}
function searchItems(query){
	console.log("query="+query);
	searchMRItems(query);
}


function filterUserItems(query){
	console.log("query="+query);	
	userItems(query);
}

function userItems(query){
	$.ajax({
		type : "GET",
		url : UserItemsServlet,
		data : "query=" +query,
		dataType : "JSON",
		success : Item.listItemTraiteReponseJSON,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}

function item_applicants(id) {
	reset_applicants_shared_div(id);

	$.ajax({
		type : "GET",
		url : ItemApplicantsListServlet,
		data : "id=" +id,
		dataType : "JSON",
		success : ProcessFindApplicants,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}

function user_items_applicants() {
	$.ajax({
		type : "GET",
		url : UserItemsApplicantsServlet,
		data : "",
		dataType : "JSON",
		success : ProcessFindApplicants,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}


function removeItem(id) {
	reset_applicants_shared_div(id);

	$.ajax({
		type : "POST",
		url : RemoveItemServlet,
		data : "id=" +id,
		dataType : "JSON",
		success : refresh,
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


function reset_applicants_shared_div(id){
	if(applicants_shared_div_state_opened)
		removeElt("#item-applicants");
	else{
		removeElt("#item-applicants");
		printHTMLSup ("#itemBox"+id,"<div id=\"item-applicants\">" +
				"<div id=\"current_item\" style=\"display:none;\">"+id+"<div>" +
		"<br></div>");
	}
	applicants_shared_div_state_opened=!applicants_shared_div_state_opened;
}



function getItem(id){
	$.ajax({
		type : "GET",
		url : GetItemServlet,
		data : "id=" +id,
		dataType : "JSON",
		success : Item.getItemTraiteReponseJSON,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}


function ProcessFindApplicants(rep) {
	var message;
	message = "<table class=\"table\"><tr>";
	if(bool==0){
		message +="<th>Date de demande</th>";
		if(rep.printobject==true)
			message +="<th>Objet</th>";
		message +="<th>De</th>"+"<th>&agrave;</th>"+"<th>Nom</th>"+"<th>Prenom</th>"+"<th>Profil</th>";
	}
	if(bool==1){
		message +="<th>Date of request</th>";
		if(rep.printobject==true)
			message +="<th>Item</th>";
		message +="<th>From</th>"+"<th>To</th>"+"<th>Last name</th>"+"<th>First name</th>"+"<th>Profile</th>";		
	}
	message+="</tr>";

	var endmessage ="</table>";
	var bodymessage ="";
	var nb=0;

	if(rep.users != undefined)
		$.each(rep.users, function(user, profile) {
			var x,y,z,iid,ititle,debut,fin,when;
			if(user !='warning'){
				$.each(profile, function(field, value) {
					//console.log(field); console.log(value);
					if(field=='name') x=value;
					if(field=='firstname') y=value;
					if(field=='id')	z=value;
					if(field=='itemid')	iid=value;
					if(field=='itemtitle')	ititle=value;
					if(field=='debut') debut=value;
					if(field=='fin') fin=value;
					if(field=='when') when=value;
				});
				nb++;

				bodymessage +="<tr>";
				bodymessage +="<td>"+when+"</td>";
				
				if(rep.printobject==true)
					bodymessage +="<td><b><a href="+item_jsp+"?id="+iid+"&title="+ititle+">"+ititle+"</a></b></td>" ;
				
				bodymessage +="<td>"+debut+"</td>"+"<td>"+fin+"</td>"+"<td>"+x+"</td>"+"<td>"+y+"</td>";

				if(bool==0)
					bodymessage +=
						"<td><a href=\"" + memberprofile_jsp + "?id="+z+"\">Afficher</a></td>"+
						"<td>" +
						"<input style=\"margin-left:5%;\" type=\"button\" value=\"Refuser\" class=\"btn btn-primary btn-xs\" " +
						"id=\"refuse_item_request_btn"+this.id+"\" OnClick=\"refuse_item_request('"+z+"','"+iid+"');\"/>\n"+
						"<input style=\"float:right;\" type=\"button\" value=\"Valider\" class=\"btn btn-primary btn-xs\" " +
						"id=\"accept_item_request_btn"+this.id+"\" OnClick=\"accept_item_request('"+z+"','"+iid+"');\"/></td>\n";
				if(bool==1)
					bodymessage +=
						"<td><a href=\"" + memberprofile_jsp + "?id="+z+"\">Show</a></td>"+
						"<td>" +
						"<input style=\"margin-left:5%;\" type=\"button\" value=\"Refuse\" class=\"accept_request_btn\" " +
						"id=\"refuse_item_request_btn"+this.id+"\" OnClick=\"refuse_item_request('"+z+"','"+iid+"');\"/>\n"+
						"<input style=\"float:right;\" type=\"button\" value=\"Validate\" class=\"accept_request_btn\" " +
						"id=\"accept_item_request_btn"+this.id+"\" OnClick=\"accept_item_request('"+z+"','"+iid+"');\"/></td>\n";
				bodymessage +="</tr>";
			} 
		});
	if(nb==0){
		if(bool==0)
			message="<br>Aucune demande pour le moment.";
		if(bool==1)
			message="<br>No request for the moment.";
		bodymessage="";
		endmessage="";
	}

	var iahtm=(message+bodymessage+endmessage);
	printHTML("#item-applicants",iahtm);
}