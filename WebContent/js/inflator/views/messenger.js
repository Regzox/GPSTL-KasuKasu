/**
 *Messenger  
 */
bool=0;
if(readCookie("lang") == "en")
	bool=1;
	else
		if(readCookie("lang") == "fr")
			bool=0;


MESSAGESMAP = new Map();
SPEAKERSMAP = new Map();

//messsage Constructor 
function Message(id,type,sender,sendername,recipient,recipientname,message,date){
	//alert("new message ("+id+","+sender+","+sendername+","
	//	+recipient+","+recipientname+","+message+","+date+")");
	this.id=id;
	this.type=type;
	this.sender=sender;
	this.sendername=sendername;
	this.recipient=recipient;
	this.recipientname=recipientname;
	this.message=message;
	this.date=date;
}
Message.prototype.getHTML=function(){  
	var s;
	s="<tr><td class=\"msgBox\" id=\"msgBox-"+this.id+"\">\n";
	s+="<span class=\"msg-visible-infos\" id=\"msg-visible-infos-"+this.id+"\">\n";
	s+="<span class=\"msg-sendername\" id=\"msg-sendername-"+this.id+"\"><b>"+this.sendername+"</b></span>\n";
	s+="<span style=\"margin-left:5px;\" class=\"msg-date\" id=\"msg-date-"+this.id+"\">"+this.date+" </span>\n";
	s+="</span>\n<br>";	
	s+="<span style=\"margin-left:10px;\" class=\"msg-content\">"+this.message+"</span>\n";
	s+="<span class=\"msg-control\">\n";
	s+="<span style=\"display:none;\" class=\"msg-hiden-control\" id=\"msg-hiden-id-"+this.id+"\">"+this.id+"</span>\n";
	s+="<span style=\"display:none;\" class=\"msg-hiden-control\" id=\"msg-hiden-sender-"+this.id+"\">"+this.sender+"</span>\n";
	s+="<span style=\"display:none;\" class=\"msg-hiden-control\" id=\"msg-hiden-recipient-"+this.id+"\">"+this.recipient+"</span>\n";
	s+="</span>\n";
	s+="</td></tr>\n";
	return s;
};

function new_private_message(uther,msg){
	if(uther=='null' || uther==undefined || msg=='null' || msg==undefined)
		return;
	//alert("new_private_message to uther="+uther+"& msg="+msg);
	if(msg.length>0)
		$.ajax({
			type : "POST",
			url : NewPrivateMessageServlet,
			data : "uther="+uther+"&msg="+msg,
			dataType : "JSON",
			success : refreshMSG,
			error : function(xhr,status,errorthrown){
				console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
			}
		});
}

function refreshMSG(result){
	document.getElementById("new_message-input").value="";
	conversation(UTHER);
}


function goToMessenger(){gotoURL(messenger_jsp);}
function conversation(uther){
	//alert("conversation with uther : "+uther);
	UTHER=uther;//Globalize uther
	if(uther==null || uther==undefined || uther=="")
		openJModal(2000,"Une erreur s'est produite",goToMessenger);
	else
		$.ajax({
			type : "GET",
			url : ConversationServlet,
			data : "uther="+uther,
			dataType : "JSON",
			success : Message.TraiteReponse,
			error : function(xhr,status,errorthrown){
				console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
			}
		});
}

Message.TraiteReponse = function(result){
	//alert("#raw conversation_mirror : "+JSON.stringify(result));
	var messages=JSON.parse(JSON.stringify(result),mirror).messages; //si format json
	//var users=JSON.parse(result,kamisama).messages; //si format text ou json deja stringifie	
	//alert("#cooked conversation_mirror : "+JSON.stringify(messages));

	for(var message in messages)
		MESSAGESMAP.set(message.id,message);

	var fhtm="<div id=\"universalBox\" align=\"center\">\n<table>";		
	if(messages.length==0)
	{
		if(bool == 0)
			fhtm+="<h2 class=\"nothing\">Commencez par écrire un premier message</h2>";
		if(bool == 1)
			fhtm+="<h2 class=\"nothing\">Start by writing a first message</h2>";
	}

	for(var i=0;i<messages.length;i++)
		//alert("messages[i] : "+JSON.stringify(messages[i]));
		fhtm+=(messages[i]).getHTML();

	fhtm+="</table></div>\n"; //universalBox end
	//alert("#html conversation_mirror: "+fhtm);
	printHTML("#found-messages",fhtm); 
	
	setTimeout(function(){ conversation(UTHER); }, 10000); //refresh automatically each 10s
}


/************************************SPEAKERS(USER'S CONVERSATION)**/
function Speaker(id,type,interlocutor){
	//alert("new Speaker("+id+","+type+","+interlocutor+")");
	this.id=id;
	this.type=type;
	this.interlocutor=interlocutor;
}

Speaker.prototype.getHTML=function(){  
	var s="";
	s+="<tr><td class=\"speakerBox\" id=\"speakerBox-"+this.id+"\">\n";
	s+="<span style=\"margin-left:45%\" class=\"speaker-visible-infos\" id=\"speaker-visible-infos-"+this.id+"\">\n";
	s+="<a class=\"speaker-username\" id=\"speaker-username-"+this.id+"\" href=\"conversation.jsp?uther="+this.id+"&interlocutor="+this.interlocutor+"\"><b>"+this.interlocutor+"</b></a>\n";
	s+="</span>\n";	
	s+="<span class=\"msg-control\">\n";
	s+="<span style=\"display:none;\" class=\"msg-hiden-control\" id=\"msg-hiden-id-"+this.id+"\">"+this.id+"</span>\n";
	s+="</span>\n";
	s+="</td></tr>\n";
	return s;
};

function interlocutors(){
	$.ajax({
		type : "GET",
		url : InterlocutorsServlet,
		data : "",
		dataType : "JSON",
		success : Speaker.traiteReponse,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}

Speaker.traiteReponse = function (result){
	//alert("#raw conversation_mirror : "+JSON.stringify(result));
	var speakers=JSON.parse(JSON.stringify(result),mirror).speakers; //si format json
	//var users=JSON.parse(result,kamisama).messages; //si format text ou json deja stringifie	
	//alert("#cooked conversation_mirror : "+JSON.stringify(speakers));

	for(var speaker in speakers)
		SPEAKERSMAP.set(speaker.id,speaker);

	var fhtm="<div id=\"universalBox\" align=\"center\">\n<table>";
	fhtm+="<tr><th class=\"th\"><span style=\"margin-left:45%\">Conversations</span></th></tr>\n";

	if(speakers.length==0)
	{
		if(bool==0)
			fhtm+="<h2 class=\"nothing\">D&eacute;marrer une conversation</h2>";

		if(bool==1)
			fhtm+="<h2 class=\"nothing\">Start a conversation</h2>";
	}

	for(var i=0;i<speakers.length;i++)
		//alert("messages[i] : "+JSON.stringify(speakers[i]));
		fhtm+=(speakers[i]).getHTML()+"<br>";

	fhtm+="</table></div>\n"; //universalBox end
	//alert("#html conversation_mirror: "+fhtm);
	printHTML("#found-speakers",fhtm);  
}

