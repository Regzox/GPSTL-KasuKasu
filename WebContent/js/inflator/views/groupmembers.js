function finduseramongfriends(query) {
	if (query.length==0) return;
	$.ajax({
		type : "GET",
		url : "/KasuKasu/findamongfriends",
		data : {query : query},
		dataType : "JSON",
		success : ProcessFindUser,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}



function ProcessFindUser(rep) {
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
			nb++;
			$.each(profile, function(field, value) {
				//console.log(field); console.log(value);
				if(field=='name')
					x=value;
				if(field=='firstname')
					y=value;
				if(field=='id')
					z=value;
			});

			bodymessage+="<tr><td>"+x+"</td><td>"+y+"</td>"+
			"<td><a href=\"/KasuKasu/restricted/memberprofile.jsp?id="+z+"\"> Voir Profil </a></td>";
			//alert("z="+z+" "+MEM[z]);
			if(!(MEM[z]!= undefined && MEM[z]!= null))
				bodymessage+="<td><button class=\"joanlinkasbutton\" " +
				"id=\"add-"+z+"-to_group-"+GID+"\" " +
				"onClick=\"addMember('"+GID+"','"+z+"')\">" +
				"Ajouter au groupe</button>\n</td>";
			else
				bodymessage+="<td><button class=\"joanlinkasbutton\" " +
				"id=\"remove-"+z+"-to_group-"+GID+"\" " +
				"onClick=\"removeMember('"+GID+"','"+z+"')\">" +
				"Retirer du groupe</button>\n</td>";
			bodymessage+="</tr>";
		});

	if(nb==0){
		message="Aucun utilisateur ne correspond.";
		bodymessage="";
		endmessage="";
	}

	printHTML("#found-friends",message+bodymessage+endmessage);
}




/*****************************************************************************
 *									MEMBERS
 */

function Member(id,name,type){
	//alert("new Member("+id+","+name+")");
	this.id=id;
	this.name=name;
	this.type=type;
} 

Member.traiteReponseJSON=function(json){	
	//alert("Member.traiteReponseJSON raw json -> "+JSON.stringify(json));	
	var jsob =JSON.parse(JSON.stringify(json),/*Member.revival*/mirror);
	members = jsob.members;
	//alert("Member.traiteReponseJSON cooked jsob -> "+JSON.stringify(jsob));

	if(jsob.error==undefined){
		var fhtm="<br><div id=\"membersBox\">";	

		if(members.length==0)
			fhtm+="<h3>Il n'y a aucun membre pour le moment.</h3>";
		MEM=[];
		for(var i in members){
			//alert(JSON.stringify(members[i]));
			MEM[members[i].id]=members[i];
			fhtm+=(members[i]).getHTML();
			//alert("JSOB.htmling : "+members[i].getHTML());
		}		
		//alert("MEM="+JSON.stringify(MEM));
		fhtm+="</div>\n"; 
		//alert("Members.html = "+fhtm);  
		printHTML("#found-members",fhtm); 
	}else
		console.log("server error ! : " +jsob.error+"\n");
};



Member.prototype.getHTML=function(){  
	//alert("Member ->getHtml ");
	var s;
	s="<div class=\"MemberBox\" id=\"MemberBox"+this.id+"\">";
	s+="<div class=\"Member-name\" id=\"Member-name"+this.id+"\">"
	s+="<a href=\"/KasuKasu/restricted/memberprofile.jsp?id="+this.id+"\"><b>"+this.name+"</b></a></div>\n";	
	s+="<div class=\"Member-infos\">";
	s+="<span style=\"display:none;\" class=\"hiden-Member-info\" id=\"Member-Member-info"+this.id+"\">"+this.Member+"</span>";
	s+="</div> ";
	s+="</div><hr><br>\n";
	return s;
};

function groupMembers(gid){
	GID=gid; //Globalise gid
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
		success : refresh,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}

function removeMember(gid,member){
	$.ajax({
		type : "POST",
		url : "removemember",
		data :  "gid="+gid+"&member="+member,
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
