/**Only for first debugs*/ 
function printJSONMembers(rep){printHTML("#found-Members", JSON.stringify(rep));}

function finduser(form) 
{
	var search = form.search.value;
	var val = form.value.value;

	var ok = checkInput(search, val);
	if (ok) 
	{
		printHTML("#notifier","");
		findUserJS(search, val);
	}
}

function checkInput(search, val) 
{
	if(val.length==0)
	{
		func_message("Champ Vide");
		return false;
	}
	else 
	{
		return true;
	}
}


function findUserJS(searchv, valuev) 
{
	$.ajax({
		type : "POST",
		url : "/KasuKasu/finduser",
		data : {search : searchv, value : valuev},
		dataType : "JSON",
		success : ProcessFindUser,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}

function ProcessFindUser(rep) 
{
	var message = "<table class=\"table\">" +
	"<tr>" +
	"<th>Nom</th><th>Prenom</th><th>Profil</th>" +
	"</tr>";
	var endmessage ="</table>";

	var bodymessage ="";
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

				bodymessage+="<tr><td>"+x+"</td><td>"+y+"</td>"+
				"<td><a href=\"/KasuKasu/profil/?id="+z+"\"> Voir Profil </a></td>";
				//alert("z="+z+" "+MEM[z]);
				if(!(MEM[z]!= undefined && MEM[z]!= null))
					bodymessage+="<td><button class=\"joanlinkasbutton\" " +
					"id=\"add-"+GID+"-to_group-"+z+"\" " +
					"onClick=\"addMember('"+GID+"','"+z+"')\">" +
					"Ajouter au groupe</button>\n</td>";
				bodymessage+="</tr>";
			}else{
				message="Aucun utilisateur ne correspond a ce que vous recherchez.";
				bodymessage="";
				endmessage="";
			}
		});
	var div=(message+bodymessage+endmessage);
	func_message(div);
}

function func_message(msg)
{
	printHTML("#notifier",msg);
}


function printHTML(dom,htm)
{ 
	$(dom).html(htm);
}









/*****************************************************************************
 *
 */

function Member(id,name,type){
	//alert("new Member("+id+","+name+")");
	this.id=id;
	this.name=name;
	this.type=type;
} 

Member.revival=function(key,value){
	//alert("revival begin");
	if(key=="members"){ 
		var r;
		if((value.error==undefined) || (value.error==0)){
			r=value;  //whole tab
			//alert("revival  -> Members = "+JSON.stringify(r));
		}else{
			r =new Object();
			r.error=value.error;
			//alert("revive -> error = "+r.error);
		}
		return (r);

	}else if(isNumber(key) && value.type=="member"){ //tab index
		var i = new Member(value.id,value.name,value.type);
		return (i);
	}		
	else{
		//alert("revival -> value = "+value);
		return (value);
	}
};


Member.traiteReponseJSON=function(json){	
	//alert("Member.traiteReponseJSON raw json -> "+JSON.stringify(json));	
	var jsob =JSON.parse(JSON.stringify(json),Member.revival);
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
	s+="<div class=\"Member-name\" id=\"Member-name"+this.id+"\"><a href=\"/gotoMember\"><b>"+this.name+"</b></a></div>\n";	
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

function refresh(result){
	if(result.error!=undefined)
		fillNOTIFIER(result.error);
	else
	window.location.reload();
}
