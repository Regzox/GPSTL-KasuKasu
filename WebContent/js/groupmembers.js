function Member(id,name){
	alert("new Member("+id+","+name);
	this.id=id;
	this.name=name;
	this.type=type;
} 

Member.revival=function(key,value){
	alert("revival begin");
	if(key=="members"){ 
		var r;
		if((value.error==undefined) || (value.error==0)){
			r=value;  //whole tab
			alert("revival  -> Members = "+JSON.stringify(r));
		}else{
			r =new Object();
			r.error=value.error;
			alert("revive -> error = "+r.error);
		}
		return (r);

	}else if(isNumber(key) && value.type=="member"){ //tab index
		var i = new Member(value.id,value.name,value.type);
		return (i);
	}		
	else{
		alert("revival -> value = "+value);
		return (value);
	}
};


Member.traiteReponseJSON=function(json){	
	alert("Member.traiteReponseJSON raw json -> "+JSON.stringify(json));	
	var jsob =JSON.parse(JSON.stringify(json),Member.revival);
	members = jsob.members;
	alert("Member.traiteReponseJSON cooked jsob -> "+JSON.stringify(jsob));

	if(jsob.error==undefined){
		var fhtm="<br><div id=\"membersBox\">";	

		if(members .length==0)
			fhtm+="<h3>Il n'y a aucun membre pour le moment.</h3>";

		for(var i in members){
			alert(JSON.stringify(Members[i]));
			fhtm+=(members[i]).getHTML();
			alert("JSOB.htmling : "+members[i].getHTML());
		}		
		fhtm+="</div>\n"; 
		alert("Members.html = "+fhtm);  
		printHTML("#found-members",fhtm); 
	}else
		console.log("server error ! : " +jsob.error+"\n");
};



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



function groupMembers(gid){
	$.ajax({
		type : "GET",
		url : "groupmembers",
		data : "gid="+gid,
		dataType : "JSON",
		success : Member.traiteReponseJSON,
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
		success : location.reload,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}
/**Only for first debugs*/ 
function printJSONMembers(rep){printHTML("#found-Members", JSON.stringify(rep));}