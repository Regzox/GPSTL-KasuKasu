function Group(id,name,type,owner,date){
	//alert("new Group("+id+","+name+","+type+","+owner+","+date+")");
	this.id=id;
	this.name=name;
	this.type=type;
	this.owner=owner;
	this.date=date;
} 

Group.revival=function(key,value){
	//alert("revival begin");
	if(key=="groups"){ 
		var r;
		if((value.error==undefined) || (value.error==0)){
			r=value;  //whole tab
			//alert("revival  -> Groups = "+JSON.stringify(r));
		}else{
			r =new Object();
			r.error=value.error;
			//alert("revive -> error = "+r.error);
		}
		return (r);
	}else if(isNumber(key) && value.type=="group"){ //tab index
		var i = new Group(value.id,value.name,value.owner,value.type,value.date);
		return (i);
	}
	else{
		//alert("revival -> value = "+value);
		return (value);
	}
};

Group.traiteReponseJSON=function(json){	
	//alert("Group.traiteReponseJSON raw json -> "+JSON.stringify(json));	
	var jsob =JSON.parse(JSON.stringify(json),Group.revival);
	groups = jsob.groups;
	//alert("Group.traiteReponseJSON cooked jsob -> "+JSON.stringify(jsob));

	if(jsob.error==undefined){
		var fhtm="<br><div id=\"groupsBox\">";	

		if(groups .length==0)
			fhtm+="<h3>Il n'y a aucun group pour le moment.</h3>";

		for(var i in groups){
			//alert(JSON.stringify(groups[i]));
			fhtm+=(groups[i]).getHTML();
			//alert("JSOB.htmling : "+groups[i].getHTML());
		}		
		fhtm+="</div>\n"; 
		//alert("Groups.html = "+fhtm);  
		printHTML("#found-groups",fhtm); 
	}else
		console.log("server error ! : " +jsob.error+"\n");
};

Group.prototype.getHTML=function(){  
	//alert("Group ->getHtml ");
	var s;
	s="<div class=\"groupBox\" id=\"groupBox"+this.id+"\">";
	s+="<div class=\"group-name\" id=\"group-name"+this.id+"\"><a href=\"/gotogroup\"><b>"+this.name+"</b></a></div>\n";	
	s+="<div class=\"group-infos\">";
	s+="<span style=\"display:none;\" class=\"hiden-group-info\" id=\"group-group-info"+this.id+"\">"+this.group+"</span>";
	s+="<span class=\"group-date\" id=\"group-date"+this.id+"\">"+this.date+"</span>\n";
	s+="</div> ";
	s+="</div><hr><br>\n";
	return s;
};


function userGroups(){
	$.ajax({
		type : "GET",
		url : "usergroups",
		data : "",
		dataType : "JSON",
		success : Group.traiteReponseJSON,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}


function createGroup(name){
	if(name.value.length==0)
		return;
	$.ajax({
		type : "POST",
		url : "creategroup",
		data : "name="+name.value,
		dataType : "JSON",
		success : refresh,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}

function refresh(result){
	//window.location.reload();//n'efface pas les inputs! why?
	window.location.href="/KasuKasu/groups.jsp";
}




/**Only for first debugs*/ 
function printJSONGroups(rep){printHTML("#found-Groups", JSON.stringify(rep));}