function init() 
{
	userGroups();
	userPoints();

}

function createobject() 
{
	printHTML("#error_nom","");
	printHTML("#error_description","");
	//printHTML("#error_datedeb","");
	//printHTML("#error_datefin","");	
	
	//var nombre=document.getElementById('nombre').value;
	var nom = document.getElementById('nom').value;
	var description = document.getElementById('description').value;
	//var datedebut = document.getElementById('datedeb').value;
	//var datefin = document.getElementById('datefin').value;
	var groupe = document.getElementById('groupe');

	var result = [];
	var options = groupe.options;
	var opt;

	for (var i=0, iLen=options.length; i<iLen; i++) 
	{
		opt = options[i];

		if (opt.selected) 
		{
			result.push(opt.value || opt.text);
		}
	}
	
	
//	var result2 = [];
//	for (i=0; i<=nombre; i=i+2)
//		{
//		    if (document.getElementById(i) !== null)
//		    	{
//		    	   result2.push(document.getElementById(i).value+","+document.getElementById(i+1).value)
//
//		    	}
//		}
	

	var ok = verif(nom, description);
	if (ok) 
	{
		printHTML("#error_nom","");
		printHTML("#error_description","");
		//printHTML("#error_datedeb","");
		//printHTML("#error_datefin","");
		//printHTML("#error_point","");
		send(nom, description, result);

	}
}

function verif(nom, description) 
{
	var bool = true;
	
//	if(nombre==0)
//	{
//		printHTML("#error_point","Point de pret manquant");
//		$("#error_point").css({
//			"color":"red",
//			"font-size": "80%"
//		});
//
//		//return false;
//		bool = false;
//	}

	if(nom.length==0)
	{
		printHTML("#error_nom","Nom manquant");
		$("#error_nom").css({
			"color":"red",
			"font-size": "80%"
		});

		//return false;
		bool = false;
	}

	if(description.length==0)
	{
		printHTML("#error_description","Description manquante");
		$("#error_description").css({
			"color":"red",
			"font-size": "80%"
		});

		//return false;
		bool = false;

	}




	return bool;
}

function send(nom, description, result) 
{
	//alert("coucou");
	 var json = JSON.stringify(result);
	 //console.log(json);
	 
	 //var json2 = JSON.stringify(result2);


	$.ajax({
	type : "POST",
	url : "createobject",

	data : "nom=" + nom + "&description=" + description + "&groupe=" + json, 

	
//	data : {nom : nom, 
//	          description : description, 
//	          datedebut : datedebut, 
//	          datefin : datefin, 
//	          groupe : groupe},


	dataType : "json",
	success : function (data)
	{
		
		//var json = JSON.parse(JSON.stringify(json));
        //alert (data.success);
        if (data.success=="Object added.")
        	{
    	       $("#myModal").modal({                    
    		      "backdrop"  : "static",
    		      "keyboard"  : true,
    		      "show"      : true                     
    		    });
    	       
    	       $("#myModal").on('hidden.bs.modal', function () {
    	           window.location.href = kasukasu.private.dashboard;
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

/*****************************************************************************************/


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
		  // alert (json.groups[i].name);
		 groupeSelect.options[groupeSelect.options.length] = new Option(json.groups[i].name);


		}
}

/*****************************************************************************************/


function userPoints(){
	$.ajax({
		type : "GET",
		url : "GetPointsPretUser",
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

	var json = JSON.parse(JSON.stringify(json));

	pointSelect = document.getElementById('point');

	
	for (var i=0; i< json.points.length; i++)
		{
		 pointSelect.options[pointSelect.options.length] = new Option(json.points[i].nom);
		}
}