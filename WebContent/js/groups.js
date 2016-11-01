function Group(id,name,type,owner,date){
	alert("new Group("+id+","+title+","+group+","+longitude+","+latitude+","+date+","+description+")");
	this.id=id;
	this.name=name;
	this.type=type;
	this.owner=owner;
	this.date=date;
} 

function Member(id,name){
	alert("new Member("+id+","+name);
	this.id=id;
	this.name=name;
	this.type=type;
} 

Group.revival=function(key,value){
	alert("revival begin");
	if(key=="groups"){ 
		var r;
		if((value.error==undefined) || (value.error==0)){
			r=value;  //whole tab
			alert("revival  -> Groups = "+JSON.stringify(r));
		}else{
			r =new Object();
			r.error=value.error;
			alert("revive -> error = "+r.error);
		}
		return (r);

	}else if(isNumber(key) && value.type=="group"){ //tab index
		var i = new Group(value.id,value.name,value.owner,value.type,value.date);
		return (i);

	}else if(isNumber(key) && value.type=="member"){ //tab index
		var i = new Member(value.id,value.name,value.type);
		return (i);
	}		else{
		alert("revival -> value = "+value);
		return (value);
	}
};


Group.traiteReponseJSON=function(json){	
	alert("Group.traiteReponseJSON raw json -> "+JSON.stringify(json));	
	var jsob =JSON.parse(JSON.stringify(json),Group.revival);
	groups = jsob.groups;
	//alert("Group.traiteReponseJSON cooked jsob -> "+JSON.stringify(jsob));

	if(jsob.error==undefined){
		var fhtm="<br><div id=\"groupsBox\">";	

		if(groups .length==0)
			fhtm+="<h3>Il n'y a aucun group pour le moment.</h3>";

		for(var i in groups){
			alert(JSON.stringify(Groups[i]));
			fhtm+=(groups[i]).getHTML();
			alert("JSOB.htmling : "+Groups[i].getHTML());
		}		
		fhtm+="</div>\n"; 
		alert("Groups.html = "+fhtm);  
		printHTML("#found-groups",fhtm); 
	}else
		console.log("server error ! : " +jsob.error+"\n");
};

Group.prototype.getHTML=function(){  
	alert("Group ->getHtml ");
	var s;
	s="<div class=\"GroupBox\" id=\"GroupBox"+this.id+"\">";
	s+="<div class=\"Group-name\" id=\"Group-name"+this.id+"\"><a href=\"/gotogroup\"><b>"+this.name+"</b></a></div>\n";	
	s+="<div class=\"Group-infos\">";
	s+="<span style=\"display:none;\" class=\"hiden-Group-info\" id=\"Group-group-info"+this.id+"\">"+this.group+"</span>";
	s+="<span class=\"Group-date\" id=\"Group-date"+this.id+"\">"+this.date+"</span>\n";
	s+="</div> ";
	s+="</div><hr><br>\n";
	return s;
};

this.name=name;
Member.prototype.getHTML=function(){  
	alert("Member ->getHtml ");
	var s;
	s="<div class=\"MemberBox\" id=\"MemberBox"+this.id+"\">";
	s+="<div class=\"Member-name\" id=\"Member-name"+this.id+"\"><a href=\"/gotoMember\"><b>"+this.name+"</b></a></div>\n";	
	s+="<div class=\"Member-infos\">";
	s+="<span style=\"display:none;\" class=\"hiden-Member-info\" id=\"Member-Member-info"+this.id+"\">"+this.Member+"</span>";
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
function groupMembers(gid){
	$.ajax({
		type : "GET",
		url : "groupmembers",
		data : "gid="+gid,
		dataType : "JSON",
		success : Group.traiteReponseJSON,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}
function addMember(gid,member){
	$.ajax({
		type : "POST",
		url : "addmember",
		data :  "gid="+gid+"&member="+member,
		dataType : "JSON",
		success : Group.traiteReponseJSON,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}
function createGroup(gid,member){
	$.ajax({
		type : "POST",
		url : "creategroup",
		data : "name="+name,
		dataType : "JSON",
		success : Group.traiteReponseJSON,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}
/**Only for first debugs*/ 
function printJSONGroups(rep){printHTML("#found-Groups", JSON.stringify(rep));}