//récupération de l'objet xhr
function getXMLHttpRequest() {
	var xhr = null;
	if (window.XMLHttpRequest || window.ActiveXObject) {
		if (window.ActiveXObject) {
			try {
				xhr = new ActiveXObject("Msxml2.XMLHTTP");
			} catch(e) {
				xhr = new ActiveXObject("Microsoft.XMLHTTP");
			}
		} else {
			xhr = new XMLHttpRequest(); 
		}
	} else {
		alert("Votre navigateur ne supporte pas l'objet XMLHTTPRequest...");
		return null;
	}
	return xhr;
}

function trans()
{
	var xhr = getXMLHttpRequest();
	var dico;
	//get the file name
	var href = document.location.href;
	var fileName = href.substr(href.lastIndexOf('/') + 1);
	
	xhr.open("GET","/KasuKasu/traduction.json",false);
	xhr.send();
	//xhr.onreadystatechange = function() {

	if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) 
	{
		dico = JSON.parse(xhr.responseText);
		
		if(fileName == "portal.jsp" || fileName == "")
		{
			document.getElementById('btn_fr').onclick = function() {
				document.cookie = 'lang=fr';
				location.reload();
				}
			document.getElementById('btn_en').onclick = function() {
				document.cookie = 'lang=en';
				location.reload();
				}           	
		}
		var cook=document.cookie;
		
		if(cook.search("lang=en")!=-1)
		{
			translation(dico,"en",fileName);
			trans_(dico,"en");
		}
		if(cook.search("lang=fr")!=-1)
		{
			translation(dico,"fr",fileName);
			trans_(dico,"fr");
		}
	}
}

function trans_(dico, lang)
{
	if(lang=="fr")
		{
			k=0;
		}
	else
		if(lang=="en")
			{
				k=1;
			}
	for(i=0;i<dico.length;i++) 
	{
		var id = dico[i].id; 
		var traductions = dico[i].traductions;
		j=k;
		if(id=="profile.jspf" && k< traductions.length)
		{	
			printHTML("#prenom",traductions[k].expression);k=k+2;
			printHTML("#nom",traductions[k].expression);k=k+2;
			printHTML("#mail",traductions[k].expression);k=k+2;
			printHTML("#tel",traductions[k].expression);k=k+2;
		} 
		k=j;
		if(id=="sidebar.jspf" && k< traductions.length)
		{	
			printHTML("#search_item",traductions[k].expression);k=k+2;//
			printHTML("#search_user",traductions[k].expression);k=k+2;//
			printHTML("#my_loans",traductions[k].expression);k=k+2;//
			printHTML("#my_items",traductions[k].expression);k=k+2;//
			printHTML("#my_friend",traductions[k].expression);k=k+2;//
			printHTML("#my_places",traductions[k].expression);k=k+2;//
			printHTML("#messaging",traductions[k].expression);k=k+2;//
			printHTML("#help",traductions[k].expression);k=k+2;//
			
		} 
		
	}
}
function translation(dico,lang,fileName)
{
	var i=0;
	//for every web page 
	for(i=0;i<dico.length;i++) 
	{
		if(lang=="fr")
			k=0;	
		else
			if(lang=="en")
				k=1;
		var id = dico[i].id; 
		var traductions = dico[i].traductions;
		if((fileName == "" || fileName=="portal.jsp")&& id == "portal.jsp" && k< traductions.length)
		{
			printHTML("#bienvenue",traductions[k].expression);
			printHTML("#titre",traductions[k].expression);k=k+2;
			printHTML("#site",traductions[k].expression);k=k+2;
			printHTML("#btn_insc",traductions[k].expression);k=k+2;
			printHTML("#btn_connex",traductions[k].expression);
		}
		if(id == "connexion.jsp"&& id == fileName && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression);
			printHTML("#connex",traductions[k].expression);k=k+2;
			printHTML("#email",traductions[k].expression);k=k+2;
			printHTML("#mdp",traductions[k].expression);k=k+2;
			printHTML("#mdpo",traductions[k].expression); 	
		}
		if(id == "createuser.jsp" && id == fileName && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression);
			printHTML("#a",traductions[k].expression);k=k+2;
			printHTML("#prenoml",traductions[k].expression);k=k+2;
			printHTML("#noml",traductions[k].expression);k=k+2;
			printHTML("#numl",traductions[k].expression);k=k+2;
			printHTML("#emaill",traductions[k].expression);k=k+2;
			printHTML("#mdpl",traductions[k].expression);k=k+2;
			printHTML("#confl",traductions[k].expression);k=k+2;
			printHTML("#captchal",traductions[k].expression);k=k+2;
			//printHTML("#submit",traductions[k].expression);
			document.getElementById("submit").value = traductions[k].expression; 		
		}
		if(id == "retrievePassword.jsp" && id == fileName && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression);
			printHTML("#ret",traductions[k].expression);k=k+2;
			printHTML("#email",traductions[k].expression);k=k+2;
			//printHTML("#submit",traductions[k].expression);
			document.getElementById("submit").value = traductions[k].expression;  
			
		}
		if(id == "modifyUser.jsp" && id == fileName && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression);
			printHTML("#a",traductions[k].expression);k=k+2;
			printHTML("#modif",traductions[k].expression);k=k+2;
			printHTML("#emaill",traductions[k].expression);k=k+2;
			printHTML("#mdpl",traductions[k].expression);k=k+2;
			printHTML("#noml",traductions[k].expression);k=k+2;
			printHTML("#prenoml",traductions[k].expression);k=k+2;
			printHTML("#numl",traductions[k].expression);k=k+2;
			//printHTML("#submit",traductions[k].expression);
			document.getElementById("valider").value = traductions[k].expression;k=k+2;  
			printHTML("#verif",traductions[k].expression);k=k+2;
			printHTML("#psw",traductions[k].expression);k=k+2;
			document.getElementById("enreg").value = traductions[k].expression;  
		}
		if(id == "searchitems.jsp" && id == fileName && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression);k=k+2;
			printHTML("#p",traductions[k].expression);k=k+2;
			//printHTML("#submit".innerHTML,traductions[k].expression);
			document.getElementById("submit").value = traductions[k].expression;           				
		}
		if(id == "profile.jsp" && id == fileName && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression);k=k+2;
			printHTML("#modif",traductions[k].expression);k=k+2;
			printHTML("#photo",traductions[k].expression);
		}
		if(id == "upload.jsp" && id == fileName && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression);k=k+2;
			printHTML("#upload",traductions[k].expression);k=k+2;
			document.getElementById("submit").value = traductions[k].expression;           				
		}
		if(id == "finduser.jsp" && id == fileName && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression);
			printHTML("#rech",traductions[k].expression);k=k+2;
			//printHTML("#submit",traductions[k].expression);
			document.getElementById("submit").value = traductions[k].expression;          				
		}
		if(id == "myfriends.jsp" && id == fileName && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression); 	
		}
		if(id == "pendingrequests.jsp" && id == fileName && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression);			
		}
		if(id == "groups.jsp" && id == fileName && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression);k=k+2;
			printHTML("#create",traductions[k].expression);
			//printHTML("#submit",traductions[k].expression);
			document.getElementById("submit").value = traductions[k].expression;       				
		}
		if(id == "createobject.jsp" && id == fileName && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression);
			printHTML("#ajout",traductions[k].expression);k=k+2;
			printHTML("#noml",traductions[k].expression);k=k+2;
			printHTML("#descl",traductions[k].expression);k=k+2;
			printHTML("#pts",traductions[k].expression);k=k+2;
			printHTML("#ajout_btn",traductions[k].expression);k=k+2;
			printHTML("#vis",traductions[k].expression);k=k+2;
			//printHTML("#submit",traductions[k].expression);
			document.getElementById("submit").value = traductions[k].expression;k=k+2;   
			printHTML("#comment",traductions[k].expression);
		}
		if(id == "useritems.jsp" && id == fileName && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression);k=k+2;
			printHTML("#manage",traductions[k].expression);k=k+2;
			//printHTML("#submit",traductions[k].expression);k=k+2;
			document.getElementById("submit").value = traductions[k].expression; k=k+2;
			printHTML("#liste",traductions[k].expression);k=k+2;          				
		}
		if(id == "userLoans.jsp" && id == fileName && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression);
			printHTML("#liste",traductions[k].expression);         				
		}
		if(id == "addpoint.jsp" && id == fileName && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression);k=k+2;
			printHTML("#choix",traductions[k].expression);k=k+2;
			//printHTML("#submit",traductions[k].expression);k=k+2;
			document.getElementById("submit").value = traductions[k].expression; k=k+2; 
			printHTML("#comment",traductions[k].expression);			
		}
		//nn encore finalisé TODO
		if(id == "importpoint.jsp" && id == fileName && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression);k=k+2;
			printHTML("#comment",traductions[k].expression);				
		}
		if(id == "memberprofile.jsp" && id == fileName && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression);k=k+2;
			printHTML("#prenom",traductions[k].expression);k=k+2;	
			printHTML("#nom",traductions[k].expression);k=k+2;				
			printHTML("#mail",traductions[k].expression);k=k+2;				
			printHTML("#tel",traductions[k].expression);k=k+2;
			//printHTML("#contact",traductions[k].expression);
			document.getElementById("contact").value = traductions[k].expression;


		}
		if(id == "groupmembers.jsp" && fileName.search(id)!=-1 && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression);k=k+2;
			printHTML("#gest",traductions[k].expression);k=k+2;	
			document.getElementById("submit").value = traductions[k].expression; k=k+2;
			printHTML("#comment",traductions[k].expression);k=k+2;
			printHTML("#liste",traductions[k].expression);			
		}
		if(id == "item.jsp" && id == fileName && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression);
			printHTML("#obj",traductions[k].expression);			
		}
		if(id == "listpoint.jsp" && id == fileName && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression);k=k+2;
			printHTML("#comment",traductions[k].expression);k=k+2;	
			printHTML("#close",traductions[k].expression);k=k+2;			
			printHTML("#myModalLabel",traductions[k].expression);k=k+2;			
			printHTML("#nom",traductions[k].expression);k=k+2;			
			printHTML("#range",traductions[k].expression);k=k+2;			
			printHTML("#ferm",traductions[k].expression);k=k+2;			
			printHTML("#enreg",traductions[k].expression);k=k+2;						
		}
		if(id == "messenger.jsp" && id == fileName && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression);k=k+2;
		}
		if(id == "conversation.jsp" && fileName.search(id) != -1 && k< traductions.length)
		{ 
			printHTML("#titre",traductions[k].expression);k=k+2;
			document.getElementById("new_message-input").placeholder = traductions[k].expression;
		}
		if(id == "evaluation.jsp" && id == fileName && k< traductions.length)
		{ 
			
		}
	}
}
function printHTML(dom,htm)
{ 
	$(dom).html(htm);
}