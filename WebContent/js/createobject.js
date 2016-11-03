	T = new Array();
	i = 0;

OpenLayers.Control.Click = OpenLayers.Class(OpenLayers.Control, {
	defaultHandlerOptions : {
		'single' : true,
		'double' : false,
		'pixelTolerance' : 0,
		'stopSingle' : false,
		'stopDouble' : false
	},

	initialize : function(options) {
		this.handlerOptions = OpenLayers.Util.extend({},
				this.defaultHandlerOptions);
		OpenLayers.Control.prototype.initialize.apply(this, arguments);
		this.handler = new OpenLayers.Handler.Click(this, {
			'click' : this.trigger
		}, this.handlerOptions);
	},

	trigger : function(e) {
		var lonlat = map.getLonLatFromViewPortPx(e.xy)
		//alert("latitude : " + lonlat.lat + ", longitude : " + lonlat.lon);

		var markers = new OpenLayers.Layer.Markers("Markers");
		map.addLayer(markers);

		var marker = new OpenLayers.Marker(lonlat);
		markers.addMarker(marker);

		div = document.getElementById('formulaire');
		
		nombre_h = document.getElementById('nombre');

		
		var lon = document.createElement("input");
		lon.type = "text";
		lon.value = lonlat.lon;
		lon.id=i;
		div.appendChild(lon);
		
		i=i+1;

		var lat = document.createElement("input");
		lat.type = "text";
		lat.value = lonlat.lat;
		lat.id=i;
		div.appendChild(lat);
		
		nombre_h.value=i;

		
		i=i+1;
		


		var supp = document.createElement("input");
		supp.type = "button";
		supp.value = "Supprimer";
		supp.onclick = function() {

			markers.removeMarker(marker);
			div.removeChild(lon);
			div.removeChild(lat);
			div.removeChild(supp);
		}

		div.appendChild(supp);

	}

});

var map;
function init() {
	map = new OpenLayers.Map("mapdiv");
	map.addLayer(new OpenLayers.Layer.OSM());

	var lonLat = new OpenLayers.LonLat(2.3488000, 48.8534100).transform(
			new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
			map.getProjectionObject() // to Spherical Mercator Projection
	);

	var zoom = 16;

	map.setCenter(lonLat, zoom);
	var click = new OpenLayers.Control.Click();
	map.addControl(click);
	click.activate();
	
	userGroups();
	

}

function createobject() 
{
	printHTML("#error_nom","");
	printHTML("#error_description","");
	printHTML("#error_datedeb","");
	printHTML("#error_datefin","");
	
	
	var nombre=document.getElementById('nombre').value;
	var nom = document.getElementById('nom').value;
	var description = document.getElementById('description').value;
	var datedebut = document.getElementById('datedeb').value;
	var datefin = document.getElementById('datefin').value;
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
	
	
	var result2 = [];
	for (i=0; i<=nombre; i=i+2)
		{
		    if (document.getElementById(i) !== null)
		    	{
		    	   result2.push(document.getElementById(i).value+","+document.getElementById(i+1).value)

		    	}
		}
	

	var ok = verif(nom, description, datedebut, datefin, nombre);
	if (ok) 
	{
		printHTML("#error_nom","");
		printHTML("#error_description","");
		printHTML("#error_datedeb","");
		printHTML("#error_datefin","");
		printHTML("#error_point","");
		send(nom, description, datedebut, datefin, result, result2);

	}
}

function verif(nom, description, datedebut, datefin, nombre) 
{
	var bool = true;
	
	if(nombre==0)
	{
		printHTML("#error_point","Point de pret manquant");
		$("#error_point").css({
			"color":"red",
			"font-size": "80%"
		});

		//return false;
		bool = false;
	}

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

	if(datedebut.length==0)
	{
		printHTML("#error_datedeb","Date de debut manquante");
		$("#error_datedeb").css({
			"color":"red",
			"font-size": "80%"
		});

		//return false;
		bool = false;

	}

	else 
	{
		var dateReg = /[0-9]{2}[/][0-9]{2}[/][0-9]{4}$/;

		if (!datedebut.match(dateReg)) 
		{

			printHTML("#error_datedeb","Format incompatible");
			$("#error_datedeb").css({
				"color":"red",
				"font-size": "80%"
			});

			//return false;
			bool = false;
		}

		else 
		{
			var parts = datedebut.split("/");
			var datedeb = new Date(parts[2], parts[1] - 1, parts[0]);
			var dateact = new Date();

			if (dateact>datedeb)
			{
				printHTML("#error_datedeb","Date anterieure a la date d'aujourd'hui");
				$("#error_datedeb").css({
					"color":"red",
					"font-size": "80%"
				});

				//return false;
				bool = false;
			}

		}
	}



	if(datefin.length==0)
	{
		printHTML("#error_datefin","Date de fin manquante");
		$("#error_datefin").css({
			"color":"red",
			"font-size": "80%"
		});

		//return false;
		bool = false;

	}



	else 
	{
		var dateReg = /[0-9]{2}[/][0-9]{2}[/][0-9]{4}$/;

		if (!datefin.match(dateReg)) 
		{

			printHTML("#error_datefin","Format incompatible");
			$("#error_datefin").css({
				"color":"red",
				"font-size": "80%"
			});

			//return false;
			bool = false;
		}

		else 
		{
			var parts = datedebut.split("/");
			var datedeb = new Date(parts[2], parts[1] - 1, parts[0]);

			var parts2 = datefin.split("/");
			var datef = new Date(parts2[2], parts2[1] - 1, parts2[0]);


			if (datef<datedeb)
			{
				printHTML("#error_datefin","Date de fin < date de debut");
				$("#error_datedeb").css({
					"color":"red",
					"font-size": "80%"
				});

				//return false;
				bool = false;
			}

		}
	}


	return bool;
}

function send(nom, description, datedebut, datefin, result, result2) 
{
	//alert("coucou");
	 var json = JSON.stringify(result);
	 
	 var json2 = JSON.stringify(result2);


	$.ajax({
	type : "POST",
	url : "createobject",

	data : "nom=" + nom + "&description=" + description
	+ "&datedebut=" + datedebut + "&datefin=" + datefin + "&groupe=" + json
	+ "&coordonnees=" + json2, 

	
//	data : {nom : nom, 
//	          description : description, 
//	          datedebut : datedebut, 
//	          datefin : datefin, 
//	          groupe : groupe},


	dataType : "json",
	//success : alert('Good !'),
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