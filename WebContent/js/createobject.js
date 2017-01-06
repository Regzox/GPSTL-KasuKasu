var result = new Array();
var result2 = new Array();

bool_trans=0;
if(document.cookie.search("lang=en")!=-1)
		bool_trans=1;
	else
		if(document.cookie.search("lang=fr")!=-1)
			bool_trans=0;

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
     		       if (tops[i].name!="Tout le monde")
     		       {
            		   
            			var groupe = 
            			{
            				    "id": tops[i].value,
            				    "nom": tops[i].name,
           		
            			}
            			
             		   result.push(groupe);


     		       }
        		}
 
        }
    }
}

function isSelected()
{
	var bool = false;
    var el = document.getElementById('id_check');
    var tops = el.getElementsByTagName('input');
    for (var i=0, len=tops.length; i<len; i++) 
    {
        if (tops[i].type == 'checkbox' ) 
        {
        	if (tops[i].checked)
        		{
        		   if (tops[i].value!="Tout le monde");
        		   bool = true;
        		}
 
        }
    }
    
    return bool;
	
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
        		   result2.push(tops[i].value);
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
		if(bool_trans==0)
			printHTML("#error_nom","Nom manquant");
			$("#error_nom").css({
				"color":"red",
				"font-size": "80%"
			});
		if(bool_trans==1)
			printHTML("#error_nom","Name missing");
			$("#error_nom").css({
				"color":"red",
				"font-size": "80%"
			});
		document.getElementById('error_nom').scrollIntoView();
		bool = false;
	}

	if(description.length==0)
	{
		if(bool_trans==0)
			printHTML("#error_description","Description manquante");
			$("#error_description").css({
				"color":"red",
				"font-size": "80%"
			});
		if(bool_trans==1)
			printHTML("#error_description","Description missing");
			$("#error_description").css({
				"color":"red",
				"font-size": "80%"
			});

		document.getElementById('error_description').scrollIntoView();
		bool = false;

	}
	
	if(result2.length==0)
	{
		if(bool_trans==0)
			printHTML("#error_point","Vous devez sélectionner au moins un point");
			$("#error_point").css({
				"color":"red",
				"font-size": "80%"
			});
		if(bool_trans==1)
			printHTML("#error_point","You must select at least one exchange point");
			$("#error_point").css({
				"color":"red",
				"font-size": "80%"
			});
	
				document.getElementById('error_point').scrollIntoView();
				bool = false;


	}
	
	if(result.length==0)
	{
		if (!isSelected())
			{
				if(bool_trans==0)
					printHTML("#error_groupe","Vous devez sélectionner au moins un groupe");
					$("#error_groupe").css({
						"color":"red",
						"font-size": "80%"
					});
				if(bool_trans==1)
					printHTML("#error_groupe","You must select at least one group");
					$("#error_groupe").css({
						"color":"red",
						"font-size": "80%"
					});
		
				document.getElementById('error_groupe').scrollIntoView();
				bool = false;
		     }

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
           checkbox.value = json.groups[i].id;
           checkbox.id = "id" + i;
           checkbox.onclick = function()
           {
        	    var el = document.getElementById('id_check');

        	    var tops = el.getElementsByTagName('input');

        	    for (var j=0, len=tops.length; j<len; j++) 
        	    {

        	        if (tops[j].type == 'checkbox' ) 
        	        {

        	        	if (tops[j].value=="Tout le monde") 
        	        		{
        	        		   if (tops[j].checked)
        	        			   {
        	        			      tops[i].checked=false;
        	        			   }
        	        		}
      
        	 
        	        }
        	    }   
        	    
           };
           
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
       checkbox.value = "Tout le monde";
       checkbox.id = "id" + i;
       checkbox.onclick = function()
       {
    	    var el = document.getElementById('id_check');
    	    var tops = el.getElementsByTagName('input');

    	    for (var j=0, len=tops.length; j<len; j++) 
    	    {

    	        if (tops[j].type == 'checkbox' ) 
    	        {

    	        	if (j!=i) (tops[j].checked=false);
  
    	 
    	        }
    	    }   
    	    
       };
       
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
           checkbox.value = json.expts[i].id;
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