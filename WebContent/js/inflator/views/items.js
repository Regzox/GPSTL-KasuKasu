/**
 * ANAGBLA Joan */

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


Item.traiteReponseJSON=function(json){	
	//alert("Item.traiteReponseJSON raw json -> "+JSON.stringify(json));	
	var jsob =JSON.parse(JSON.stringify(json),/*Item.revival*/mirror);
	items = jsob.items;
//	alert("Item.traiteReponseJSON cooked jsob -> "+JSON.stringify(jsob));

	if(jsob.error==undefined){
		var fhtm="<br><div id=\"itemsBox\">";	

		if(items.length==0)
			fhtm+="<h3>Il n'y a rien à afficher.</h3>";

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
		fhtm+="</div>\n"; 
		//alert("items.html = "+fhtm);  
		printHTML("#found-items",fhtm); 
	}else
		console.log("server error ! : " +jsob.error+"\n");
};


Item.prototype.getHTML=function(){  
	//alert("Item ->getHtml ");
	var s;
	s="<div class=\"itemBox\" id=\"itemBox"+this.id+"\">";
	s+="<div class=\"item-title\" id=\"item-title"+this.id+"\">";
	s+="<a href=/KasuKasu/item.jsp?id="+this.id+"&title="+this.title+">";//TODO PREG REPLACE TITLE IF IS SENT BY URL 
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
	s+=" <input style=\"margin-left:50%;\" type=\"button\" value=\"Je le veux\" class=\"iwantit_btn\" " +
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
	s+="<a href=/KasuKasu/item.jsp?id="+this.id+"&title="+this.title+">";
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
	s+="<button <input style=\"float:left;margin-right:75%;\" onclick=\"window.location.href='/KasuKasu/objectmanagement?objectId="+this.id+"&data=null'\" type=\"button\"class=\"btn btn-primary btn-xs\" name=\"modify\" value=\"modify\">Modifier</button>";
	s+="<span  class=\"item-date\" id=\"item-date"+this.id+"\">"+this.date+"</span>\n";
	s+="</div>";
	s+="</div><hr><br>\n";
	return s;
};


function searchMRItems(query){
	$.ajax({
		type : "GET",
		url : "searchitems",
		data : "query=" +query,
		dataType : "JSON",
		success : Item.traiteReponseJSON,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}
function searchItems(form){
	if (checkQuery(form.iquery.value))		
		searchMRItems(form.iquery.value);
}
function checkQuery(query){
	if(query.length==0)
		return false; //do nothing
	return true;
}

function filterUserItems(form){
	if (checkQuery(form.iquery.value))		
		userItems(form.iquery.value);
}

function userItems(query){
	$.ajax({
		type : "GET",
		url : "useritems",
		data : "query=" +query,
		dataType : "JSON",
		success : Item.traiteReponseJSON,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}

function item_applicants(id) {
	reset_applicants_shared_div(id);

	$.ajax({
		type : "GET",
		url : "itemapplicantslist",
		data : "id=" +id,
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
		url : "removeitem",
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

/**
 * Solution temporaire a corriger TODO
 */
function reset_applicants_shared_div(id){
	removeElt("#item-applicants");
	printHTMLSup ("#itemBox"+id,"<div id=\"item-applicants\">" +
			"<div id=\"current_item\" style=\"display:none;\">"+id+"<div>" +
	"<br></div>");
}



function getItem(id){
	$.ajax({
		type : "GET",
		url : "getitem",
		data : "id=" +id,
		dataType : "JSON",
		success : Item.traiteReponseJSON,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}



/**
 * GESTION TRES SALE DES APPLICANTS POUR CE PROTOTYPE (oN NE GERE PAS LE NOMBRE DAPPEL SERVER , PAS RE CACHE DES USER RETROUVES , A CORRIGER ...)
 * @param rep
 */
function ProcessFindApplicants(rep) {
	var message = "<table class=\"table\">" +
	"<tr>" +
	"<th>Nom</th><th>Prenom</th><th>Profil</th>" +
	"</tr>";
	var endmessage ="</table>";

	var bodymessage ="";
	var nb=0;
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

				if(z==rep.id)return;//Skip if the user is yourself
				nb++;
				bodymessage +=
					"<tr>" +
					"<td>"+x+"</td>" +
					"<td>"+y+"</td>"+
					"<td><a href=\"/KasuKasu/restricted/memberprofile.jsp?id="+z+"\"> Voir Profil </a></td>"+
					"<td>" +
					"<input style=\"margin-left:5%;\" type=\"button\" value=\"Ignorer\" class=\"accept_request_btn\" " +
					"id=\"refuse_item_request_btn"+this.id+"\" OnClick=\"refuse_item_request('"+z+"','"+$("#current_item").text()+"');\"/>\n"+
					"<input style=\"float:right;\" type=\"button\" value=\"Valider\" class=\"accept_request_btn\" " +
					"id=\"accept_item_request_btn"+this.id+"\" OnClick=\"accept_item_request('"+z+"','"+$("#current_item").text()+"');\"/>\n";
				"</tr>";
			} 
		});
	if(nb==0){
		message="<br>Aucune demande sur cet objet pour le moment.";
		bodymessage="";
		endmessage="";
	}
	endmessage+="<br>";

	var iahtm=(message+bodymessage+endmessage);
	printHTML("#item-applicants",iahtm);
}