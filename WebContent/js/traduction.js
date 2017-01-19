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
	
	xhr.open("GET","/KasuKasu/traduction_.json",false);
	xhr.send();
	//xhr.onreadystatechange = function() {

	/*if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) 
	{*/
		dico = JSON.parse(xhr.responseText);
		//console.log(JSON.stringify(dico));
		
		
		console.log(walk_through(dico,"portal.jsp","titre","fr","origin"));
		
			
	//}
	
}


function walk_through(json,topField,nextField,langue,origin) {	
	for (var key in json)
		if (json.hasOwnProperty(topField)) 
			return walk_through(json[topField],topField,nextField,langue,origin);
		else if (json.hasOwnProperty(nextField)){ 
			console.log(json[nextField][langue])
			return json[nextField][langue];
		}
		else 
			return origin;
}


