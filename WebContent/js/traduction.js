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

function trans(page,element){
	var xhr = getXMLHttpRequest();
	
	//get the file name
	var href = document.location.href;
	var fileName = href.substr(href.lastIndexOf('/') + 1);

	xhr.open("GET","/KasuKasu/traduction.json",false);
	xhr.send();
	if(fileName == "portal.jsp" || fileName == "")
	{
		if(readCookie("lang") == null)
			createCookie();
		document.getElementById('btn_fr').onclick = function() {
			createCookie("fr");
			location.reload();
			}
		document.getElementById('btn_en').onclick = function() {
			createCookie("en");
			location.reload();
			}           	
	}
	//xhr.onreadystatechange = function() {

	/*if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) 
	{*/
	var dico = JSON.parse(xhr.responseText);
	
	for(var i in dico)
		if(dico[i].hasOwnProperty(page))
			{
				if(document.getElementById(element).type == "text" || document.getElementById(element).type == "password")
					{
					//TODO dosn't really work :/
						if(document.getElementById(element).placeholder !="")
						{
							document.getElementById(element).placeholder = walk_through(dico[i][page],element,readCookie("lang"));
						}
					}
				if(document.getElementById(element).type == "submit")
				{
					document.getElementById(element).value = walk_through(dico[i][page],element,readCookie("lang"));
				}
				else
					{
						printHTML("#"+element, walk_through(dico[i][page],element,readCookie("lang")));
					}
			}
	//}

}


function walk_through(json,element,langue) {	
	 if (json.hasOwnProperty(element))
			return json[element][langue];
}

function printHTML(dom,htm)
{ 
	$(dom).html(htm);
}
var result;
var language="";
/************************************* Affichage des langues ************************************/

function editLang(lang)
{
	var xhr = getXMLHttpRequest();
	xhr.open("GET","/KasuKasu/traduction.json",false);
	xhr.send();

		var dico = JSON.parse(xhr.responseText);
		var bodymessage="";
		var endmessage ="</table>";
		var message = "<table class=\"table\">" +
		"<tr>" +
		"<th>Page</th><th>Element</th><th>Expression</th>"+
		"</tr>";
		result = dico;
		for(var i in dico)
		{
			for(var page in dico[i])
				{
					for(var element in dico[i][page])
					{	
						//for(var l in dico)
						bodymessage = bodymessage+
						"<tr style='text-align: left'>" +
						"<td> "+page+"</td>"+"<td> "+element+"</td>" ;
						bodymessage = bodymessage +"<td> <input id='"+page+"_"+element+"'type='text' value='"+dico[i][page][element][lang]+"'></input></td>"+"<td> "+"</tr>";
					}
				}	
		}
		var elt =message + bodymessage + endmessage;
		alert("lang = "+lang)
		btn = "<button id='save' onclick='saveLang()'>Enregistrer</button>"
		
		printHTML(document.getElementById("elt"),elt + btn);
		language = lang;
}
/************************************* Ajout d'une nouvelle langue************************************/
var btn;
function addLang()
{
	input_lang = "<input id='input_lang' ></input>"
	var xhr = getXMLHttpRequest();
	xhr.open("GET","/KasuKasu/traduction.json",false);
	xhr.send();

		var dico = JSON.parse(xhr.responseText);
		var bodymessage="";
		var endmessage ="</table>";
		var message = "<table class=\"table\">" +
		"<tr>" +
		"<th>Page</th><th>Element</th><th>Expression</th>"+
		"</tr>";
		for(var i in dico)
		{
			for(var page in dico[i])
				{
					for(var element in dico[i][page])
					{	
						//for(var l in dico)
						bodymessage = bodymessage+
						"<tr style='text-align: left'>" +
						"<td> "+page+"</td>"+"<td> "+element+"</td>" ;
						bodymessage = bodymessage +"<td> <input type='text' value='"+"'></input></td>"+"<td> "+"</tr>";
					}
				}	
		}
		var elt =input_lang + message + bodymessage + endmessage;
		printHTML(document.getElementById("elt"),elt);
		btn = "<button id='save' onclick='addLanguage()'>Ajouter</button>"
}
/********************************************* Sauvegarde des modifications ***********************************/

function saveLang()
{
	lang = language;
	for(var i in result)
	{
		for(var page in result[i])
			{
				for(var element in result[i][page])
				{	
					result[i][page][element][lang] = document.getElementById(page+"_"+element).value;
					//console.log("result["+i+"]["+page+"]["+element+"]["+lang+"] = " +result[i][page][element][lang]);
				}
			}	
	}

	$.ajax({
		type : "POST",
		url : SaveLanguageServlet,
		data : JSON.stringify(result), 
		dataType : 'json',
		success :  function()
		{
			//TODO
		},
		error : function(XHR, testStatus, errorThrown) {
		console.log(JSON.stringify(XHR + " " + testStatus + " "	+ errorThrown));
		}
		});
}