var result = new Array();
var result2 = new Array();


function init() 
{
	userGroups();
	userPoints();

}

/***************************** Récupérer les groupes sélectionnés *********************************/

function getSelectedGroups()
{
    var el = document.getElementById('id_check');
    var tops = el.getElementsByTagName('input');
    for (var i=0, len=tops.length; i<len; i++) 
    {
        if (tops[i].type == 'checkbox' ) 
        {
        	if (tops[i].checked)
        		{
        		   result.push(tops[i].name);
        		}
 
        }
    }
}

/***************************** Récupérer les points sélectionnés *********************************/

function getSelectedPoints()
{
    var el = document.getElementById('id_check2');
    var tops = el.getElementsByTagName('input');
    for (var i=0, len=tops.length; i<len; i++) 
    {
        if (tops[i].type == 'checkbox' ) 
        {
        	if (tops[i].checked)
        		{
        		   result2.push(tops[i].name);
        		}
 
        }
    }
}

function createobject() 
{
	printHTML("#error_nom","");
	printHTML("#error_description","");
	printHTML("#error_groupe","");
	printHTML("#error_point","");


	
	var nom = document.getElementById('nom').value;
	var description = document.getElementById('description').value;
	result.length=0;
	getSelectedGroups();
	result2.length=0;
	getSelectedPoints();


	var ok = verif(nom, description,result, result2);
	if (ok) 
	{
		printHTML("#error_nom","");
		printHTML("#error_description","");
		printHTML("#error_groupe","");
		printHTML("#error_point","");

		
		send (nom,description,result,result2);

		

	}
}

function verif(nom, description, result, result2) 
{
	var bool = true;


	if(nom.length==0)
	{
		printHTML("#error_nom","Nom manquant");
		$("#error_nom").css({
			"color":"red",
			"font-size": "80%"
		});
		
		document.getElementById('error_nom').scrollIntoView();
		bool = false;
	}

	if(description.length==0)
	{
		printHTML("#error_description","Description manquante");
		$("#error_description").css({
			"color":"red",
			"font-size": "80%"
		});

		document.getElementById('error_description').scrollIntoView();
		bool = false;

	}
	
	if(result2.length==0)
	{
		printHTML("#error_point","Vous devez selectionner au moins un point");
		$("#error_point").css({
			"color":"red",
			"font-size": "80%"
		});

		document.getElementById('error_point').scrollIntoView();
		bool = false;

	}
	
	if(result.length==0)
	{
		printHTML("#error_groupe","Vous devez selectionner au moins un groupe");
		$("#error_groupe").css({
			"color":"red",
			"font-size": "80%"
		});

		document.getElementById('error_groupe').scrollIntoView();
		bool = false;

	}
	

	return bool;
}

function send(nom, description, result, result2) 
{
	 var json = JSON.stringify(result);
	 console.log(json);
	 
	 var json2 = JSON.stringify(result2);
	 console.log(json2);
	 

	$.ajax({
	type : "POST",
	url : "createobject",

	data : "nom=" + nom + "&description=" + description + "&groupe=" + json + "&coordonnees=" + json2, 

	dataType : "json",
	success : function (data)
	{

        if (data.success=="Object added.")
        	{
    	       $("#myModal").modal({                    
    		      "backdrop"  : "static",
    		      "keyboard"  : true,
    		      "show"      : true                     
    		    });
    	       
    	       $("#myModal").on('hidden.bs.modal', function () {
    	           window.location.href = "/KasuKasu/useritems.jsp";
    	       });
        	}
	},
	error : function(XHR, testStatus, errorThrown) {
	console.log(JSON.stringify(XHR + " " + testStatus + " "	+ errorThrown));
	}
	});

}

function printHTML(dom,htm)
{ 
	$(dom).html(htm);
}

/*************************** Afficher les groupes d'un user ***************************************/


function userGroups(){
	$.ajax({
		type : "GET",
		url : "usergroups",
		data : "",
		dataType : "JSON",
		success : traiteReponse,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}



function traiteReponse(json) 
{

	var json = JSON.parse(JSON.stringify(json));
	groupeSelect = document.getElementById('groupe');

	
	for (var i=0; i< json.groups.length; i++)
		{
		   
		   var checkbox = document.createElement('input');
           checkbox.type = "checkbox";
           checkbox.name = json.groups[i].name;
           checkbox.value = "value";
           checkbox.id = "id" + i;
           
           var label = document.createElement('label')
           label.htmlFor = "id";
           label.appendChild(document.createTextNode(json.groups[i].name));
           
           document.getElementById('id_check').appendChild(checkbox);
           document.getElementById('id_check').appendChild(label);
           document.getElementById('id_check').appendChild(document.createElement("br"));

		}
	
	   var checkbox = document.createElement('input');
       checkbox.type = "checkbox";
       checkbox.name = "Tout le monde";
       checkbox.value = "value";
       checkbox.id = "id" + i;
       
       var label = document.createElement('label')
       label.htmlFor = "id";
       label.appendChild(document.createTextNode("Tout le monde"));
       
       document.getElementById('id_check').appendChild(checkbox);
       document.getElementById('id_check').appendChild(label);
       document.getElementById('id_check').appendChild(document.createElement("br"));
}

/*************************** Afficher les points d'un user ***************************************/


function userPoints(){
	$.ajax({
		type : "GET",
		url : "PointsUserList",
		data : "",
		dataType : "JSON",
		success : traiteReponse2,
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}



function traiteReponse2(json) 
{

/*	var json = JSON.parse(JSON.stringify(json));

	pointSelect = document.getElementById('point');
	
	if (json.points.length==0)  { 
		                            $('#ajout').show();
		                            $("#ajout").click(function(){
		                 	           window.location.href = "/KasuKasu/addpointpret.jsp";
		                 	          return false;
		                            }); 
	                            }

	else 
	{
		for (var i=0; i< json.points.length; i++)
		{
		 pointSelect.options[pointSelect.options.length] = new Option(json.points[i].nom);
		}
		
	}*/
	
	var json = JSON.parse(JSON.stringify(json));
	pointSelect = document.getElementById('point');
	
	if (json.expts.length==0)  
	{ 
        $('#ajout').show();
        $("#ajout").click(function(){
	           window.location.href = "/KasuKasu/addpoint.jsp";
	          return false;
        }); 
    }


	else 
		{
		
	for (var i=0; i< json.expts.length; i++)
		{
		   
		   var checkbox = document.createElement('input');
           checkbox.type = "checkbox";
           checkbox.name = json.expts[i].name;
           checkbox.value = "value";
           checkbox.id = "id" + i;
           
           var label = document.createElement('label')
           label.htmlFor = "id";
           label.appendChild(document.createTextNode(json.expts[i].name));
           
           document.getElementById('id_check2').appendChild(checkbox);
           document.getElementById('id_check2').appendChild(label);
           document.getElementById('id_check2').appendChild(document.createElement("br"));

		}
		}

	

}