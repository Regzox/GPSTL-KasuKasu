/**
 * ANAGBLA Joan */

function Item(id,owner,group,date,longitude,latitude,title,description){
	//alert("new Item("+id+","+title+","+group+","+longitude+","+latitude+","+date+","+description+")");
	this.id=id;
	this.owner=owner;
	this.group=group;
	this.date=date;
	this.longitude=longitude;
	this.latitude=latitude;
	this.title=title;
	this.description=description;
} 


Item.revival=function(key,value){
	//alert("revival begin");
	if(key=="items"){ 
		var r;
		if((value.error==undefined) || (value.error==0)){
			r=value;  //whole tab
			//alert("revival  -> items = "+JSON.stringify(r));
		}else{
			r =new Object();
			r.error=value.error;
			//alert("revive -> error = "+r.error);
		}
		return (r);

	}else if(isNumber(key) && value.type=="item"){ //tab index
		var i = new Item(value.id,value.owner,value.group,value.date,
				value.longitude,value.latitude,value.title,value.description);
		return (i);
	}else{
		//alert("revival -> value = "+value);
		return (value);
	}
};


Item.traiteReponseJSON=function(json){	
	//alert("Item.traiteReponseJSON raw json -> "+JSON.stringify(json));	
	var jsob =JSON.parse(JSON.stringify(json),Item.revival);
	items = jsob.items;
	//alert("Item.traiteReponseJSON cooked jsob -> "+JSON.stringify(jsob));

	if(jsob.error==undefined){
		var fhtm="<br><div id=\"itemsBox\">";	

		if(items.length==0)
			fhtm+="<h3>Il n'y a rien à afficher.</h3>";

		for(var i in items){
			//alert(JSON.stringify(items[i]));
			fhtm+=(items[i]).getHTML();
			//alert("JSOB.htmling : "+items[i].getHTML());
		}		
		fhtm+="</div>\n"; 
//		alert("items.html = "+fhtm);  
		printHTML("#found-items",fhtm); 
	}else
		console.log("server error ! : " +jsob.error+"\n");
};


Item.prototype.getHTML=function(){  
	//alert("Item ->getHtml ");
	var s;
	s="<div class=\"itemBox\" id=\"itemBox"+this.id+"\">";
	s+="<div class=\"item-title\" id=\"item-title"+this.id+"\"><a href=\"/borrowthis\"><b>"+this.title+"</b></a></div>\n";	
	s+="<div class=\"item-infos\">";
	s+="<span class=\"visible-item-info\" id=\"item-owner-info"+this.id+"\">"+this.owner+"</span>";
	s+="<span style=\"display:none;\" class=\"hiden-item-info\" id=\"item-group-info"+this.id+"\">"+this.group+"</span>";
	s+="<span style=\"display:none;\" class=\"hiden-item-info\" id=\"item-longitude-info"+this.id+"\">"+this.longitude+"</span>";
	s+="<span style=\"display:none;\" class=\"hiden-item-info\" id=\"item-latitude-info"+this.id+"\">"+this.latitude+"</span>";
	s+="</div> ";
	s+="<div class=\"item-desc\" id=\"item-desc"+this.id+"\">"+this.description+"</div><br>\n";
	s+="<div class=\"item-more\">";
	s+="<span class=\"item-date\" id=\"item-date"+this.id+"\">"+this.date+"</span>\n";
	s+=" <input style=\"margin-left:50%;\" type=\"button\" value=\"Je le veux\" class=\"iwantit_btn\" " +
	"id=\"iwantit_btn"+this.id+"\" OnClick=\"location.href='./demande?idbox="+this.id+"&owner="+this.owner+"';\"/>\n";
	s+="</div>";
	s+="</div><hr><br>\n";
	return s;
};


Item.traiteReponseJSON2=function(json){	
	//alert("Item.traiteReponseJSON raw json -> "+JSON.stringify(json));	
	var jsob =JSON.parse(JSON.stringify(json),Item.revival);
	items = jsob.items;
	//alert("Item.traiteReponseJSON cooked jsob -> "+JSON.stringify(jsob));

	if(jsob.error==undefined){
		var fhtm="<br><div id=\"itemsBox\">";	

		if(items.length==0)
			fhtm+="<h3>Vous n'avez aucun objet empruntable en ce moment.</h3>";

		for(var i in items){
			//alert(JSON.stringify(items[i]));
			fhtm+=(items[i]).getHTML2();
			//alert("JSOB.htmling : "+items[i].getHTML());
		}		
		fhtm+="</div>\n"; 
//		alert("items.html = "+fhtm);  
		printHTML("#found-items",fhtm); 
	}else
		console.log("server error ! : " +jsob.error+"\n");
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
		success : Item.traiteReponseJSON2,
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

/**
 * Solution temporaire a corriger
 */
function reset_applicants_shared_div(id){
	removeElt("#item-applicants");
	printHTMLSup ("#itemBox"+id,"<div id=\"item-applicants\"><br></div>");
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
				"<td><a href=\"/KasuKasu/vaiderpret?id_objet=3&id_emprunteur="+z+"\">Accorder</a></td>"+
				"</tr>";
			} 
		});
	if(nb==0){
		message="<br>Aucune demande sur cet Objet pour le moment.";
		bodymessage="";
		endmessage="";
	}
	endmessage+="<br>";
	
	var iahtm=(message+bodymessage+endmessage);
	printHTML("#item-applicants",iahtm);
}