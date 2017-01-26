/**
 * Ouiza Bouyahiaoui
 * @returns
 */
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
var keys="";
var b=true;
var gen_lang;

//TODO à optimiser !!!

function trans(page,element){

	//get the file name
	var href = document.location.href;
	var fileName = href.substr(href.lastIndexOf('/') + 1);
	var xhr = getXMLHttpRequest();
	xhr.open("GET","/KasuKasu/traduction.json",false);
	xhr.send();
	var dico = JSON.parse(xhr.responseText);

	if(fileName == "portal.jsp" || fileName == "")
	{
		if(b)
		{
			keys = getLang(dico[0][page][element], element);
			b = false;
		}
		if(readCookie("lang") == null)
			createCookie();

		body = "<form> <select class='btn btn-info btn-block'>"

			for(var i=0; i< keys.length ; i++)
			{
				body = body + "<option id='"+keys[i]+"' onclick='createCookie(\""+keys[i]+"\"); location.reload();'>"+keys[i];
			}
		body = body + "</select></form>"
		printHTML("#lang", body);

	}
	for(var i in dico)
		if(dico[i].hasOwnProperty(page))
		{
			//TODO à vérifier
			if(document.getElementById(element)!=null){

				if(document.getElementById(element).type == "text" || document.getElementById(element).type == "password")
				{
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
		}
}

function getLang(json,element)
{
	var name;
	keys =[];
	for (name in json) {
		if (Object.prototype.hasOwnProperty.call(json, name)) {
			keys.push(name);
		}
	}
	return keys;
}
function walk_through(json,element,langue) {	
	if (json.hasOwnProperty(element))
		return json[element][langue];
}

function printHTML(dom,htm)
{ 
	$(dom).html(htm);
}

function genLang()
{
	var xhr = getXMLHttpRequest();
	xhr.open("GET","/KasuKasu/traduction.json",false);
	xhr.send();
	var dico = JSON.parse(xhr.responseText);

	keys = getLang(dico[0]["portal.jsp"]["titre"], "titre");
	body = "<form> <select class='btn btn-info'>"	
		for(var i=0; i< keys.length ; i++)
		{
			body = body + "<option id='"+keys[i]+"' onclick='editLang(\""+keys[i]+"\");'>"+keys[i];
		}
	body = body + "</select></form> <br> <br>"
	printHTML("#lang", body);
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
				bodymessage = bodymessage+
				"<tr style='text-align: left'>" +
				"<td> "+page+"</td>"+"<td> "+element+"</td>" ;
				bodymessage = bodymessage +"<td> <input id='"+page+"_"+element+"'type='text' value='"+dico[i][page][element][lang]+"'></input></td>"+"<td> "+"</tr>";
			}
		}	
	}
	var elt =message + bodymessage + endmessage;
	var btn = "<button id='save' class='btn btn-info btn-block' onclick='saveLang()'>Enregistrer</button>"

		printHTML(document.getElementById("elt"),elt + btn);
	language = lang;
}
/************************************* Ajout d'une nouvelle langue************************************/
var lang_added;
function addLang()
{
	input_lang = "<label>Langue : </label><input id='input_lang' ></input>"
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
				bodymessage = bodymessage +"<td> <input type='text' id='"+page+"_"+element+"'></input></td>"+"<td> "+"</tr>";
			}
		}	
	}

	var btn = "<button id='save' class='btn btn-info btn-block' onclick='addLanguage()'>Ajouter</button>"
		var elt = input_lang + message + bodymessage + endmessage + btn;
	printHTML(document.getElementById("elt"),elt);

}
/******************************* Add a new language and save it to a file*******************************/

function addLanguage()
{
	lang_added = document.getElementById('input_lang').value;
	var lang = lang_added;
	var xhr = getXMLHttpRequest();
	xhr.open("GET","/KasuKasu/traduction.json",false);
	xhr.send();
	var result = JSON.parse(xhr.responseText);
	for(var i in result)
	{
		for(var page in result[i])
		{
			for(var element in result[i][page])
			{	
				result[i][page][element][lang] = document.getElementById(page+"_"+element).value;
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
			console.log("OK");
		},
		error : function(XHR, testStatus, errorThrown) {
			//console.log(JSON.stringify(XHR + " " + testStatus + " "	+ errorThrown));
		}
	});
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
			console.log("OK");
		},
		error : function(XHR, testStatus, errorThrown) {
			//console.log(JSON.stringify(XHR + " " + testStatus + " "	+ errorThrown));
		}
	});
}