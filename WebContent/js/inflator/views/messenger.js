/**
 *Messenger  
 */

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
	//alert("User ->getHtml ");
	var s;
	s="<div class=\"msgBox\" id=\"msgBox-"+this.id+"\">\n";
	s+="<div class=\"msg-visible-infos\" id=\"msg-visible-infos-"+this.id+"\">\n";
	s+="<a class=\"msg-sendername\" id=\"msg-sendername-"+this.id+"\" href=\"\"><b>"+this.sendername+"</b></a>\n";
	s+="<span class=\"msg-date\" id=\"msg-date-"+this.id+"\">( "+this.date+" )</span>\n";
	s+="</div>\n";	
	s+="<div class=\"msg-content\">"+this.message+"</div>\n";
	s+="<div class=\"msg-control\">\n";
	s+="<span style=\"display:none;\" class=\"msg-hiden-control\" id=\"msg-hiden-id-"+this.id+"\">"+this.id+"</span>\n";
	s+="<span style=\"display:none;\" class=\"msg-hiden-control\" id=\"msg-hiden-sender-"+this.id+"\">"+this.sender+"</span>\n";
	s+="<span style=\"display:none;\" class=\"msg-hiden-control\" id=\"msg-hiden-recipient-"+this.id+"\">"+this.recipient+"</span>\n";
	s+="</div>\n";
	s+="</div><br>\n";
	return s;
};

function new_private_message(uther,msg){
	if(uther=='null' || uther==undefined || msg=='null' || msg==undefined)
		return;
	//alert("new_private_message to uther="+uther+"& msg="+msg);
	if(msg.length>0)
	send_ajax_request_("POST","newprivatemessage","uther="+uther+"&msg="+msg,"json",ajax_reply_processor_);
}

function conversation(uther){
	//alert("conversation with uther : "+uther);
	if(uther=='null' || uther==undefined)
		gotoURL("/Momento/messenger.jsp");
	else
	send_ajax_request_("GET","conversation","uther="+uther,"json",ajax_reply_processor_);
}


/************************************SPEAKERS(USER'S CONVERSATION)**/
function Speaker(id,type,username){
	//alert("new Speaker("+id+","+type+","+username+")");
	this.id=id;
	this.type=type;
	this.username=username;
}
Speaker.prototype.getHTML=function(){  
	//alert("User ->getHtml ");
	var s;
	s="<div class=\"speakerBox\" id=\"speakerBox-"+this.id+"\">\n";
	s+="<div class=\"speaker-visible-infos\" id=\"speaker-visible-infos-"+this.id+"\">\n";
	s+="<a class=\"speaker-username\" id=\"speaker-username-"+this.id+"\" href=\"conversation.jsp?uther="+this.id+"&username="+this.username+"\"><b>"+this.username+"</b></a>\n";
	s+="</div>\n";	
	s+="<div class=\"msg-control\">\n";
	s+="<span style=\"display:none;\" class=\"msg-hiden-control\" id=\"msg-hiden-id-"+this.id+"\">"+this.id+"</span>\n";
	s+="</div>\n";
	s+="</div><br>\n";
	return s;
};

function interlocutors(){
	//alert("interlocutors ");
	send_ajax_request_("GET","interlocutors","","json",ajax_reply_processor_);
}
