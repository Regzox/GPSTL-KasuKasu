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
		var toProjection = new OpenLayers.Projection("EPSG:4326");
		var lonlat = map.getLonLatFromViewPortPx(e.xy);
		
		var lonlat1 = map.getLonLatFromViewPortPx(e.xy).
        transform(map.getProjectionObject(), toProjection);
		//alert("latitude : " + lonlat.lat + ", longitude : " + lonlat.lon);

		var markers = new OpenLayers.Layer.Markers("Markers");
		map.addLayer(markers);

		var marker = new OpenLayers.Marker(lonlat);
		markers.addMarker(marker);

		div = document.getElementById('formulaire');
		
		nombre_h = document.getElementById('nombre');

		
		var lon = document.createElement("input");
		lon.type = "text";
		lon.value = lonlat1.lon.toFixed(5);
		lon.id=i;
		div.appendChild(lon);
		
		i=i+1;
		var lat = document.createElement("input");
		lat.type = "text";
		lat.value = lonlat1.lat.toFixed(5);
		lat.id=i;
		div.appendChild(lat);
		
		i=i+1;		
		var nom = document.createElement("input");
		nom.type = "text";
		nom.setAttribute("placeholder", "Nom du lieu"); 	
		nom.id=i;
		div.appendChild(nom);
		
		i=i+1;
		var br = document.createElement("br");
		br.id=i;
		div.appendChild(br);
		
		i= i+1;		
		var radius = document.createElement("input");
		radius.type = "text";
		radius.setAttribute("size", 40);
		radius.setAttribute("placeholder", "Périmètre en mètres. Exemple : 250"); 	
		radius.id=i;
		div.appendChild(radius);
		
		i=i+1;
		var br3 = document.createElement("br");
		br3.id=i;
		div.appendChild(br3);
		

		

		var supp = document.createElement("input");
		supp.type = "button";
		supp.value = "Supprimer";
		supp.onclick = function() 
		{

			markers.removeMarker(marker);
			div.removeChild(lon);
			div.removeChild(lat);
			div.removeChild(nom);
			div.removeChild(br);
			div.removeChild(radius);
			div.removeChild(br3);
			div.removeChild(br1);
			div.removeChild(br2);
			div.removeChild(supp);
		}

		div.appendChild(supp);
		
		i=i+1;
		var br1 = document.createElement("br");
		br1.id=i;
		div.appendChild(br1);
		
		i=i+1;
		var br2 = document.createElement("br");
		br2.id=i;
		div.appendChild(br2);
		
		i=i+1;
		nombre_h.value=i;



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
	
	

}

function createobject() 
{

	printHTML("#error_point","");
	
	
	var nombre=document.getElementById('nombre').value;
	
	var result2 = {};
	var points = [];
	result2.points=points;
	for (i=0; i<=nombre; i=i+8)
		{
		    if (document.getElementById(i) !== null)
		    	{
		    	
		    	var point = 
		    	{
		    		    "lat": document.getElementById(i).value,
		    		    "lon": document.getElementById(i+1).value,
		    		    "nom": document.getElementById(i+2).value,
		    		    "radius": document.getElementById(i+4).value

		    	}
                     result2.points.push(point);
		    	}
		}
	

	var ok = verif(nombre);
	if (ok) 
	{
		//console.log(result2);
		printHTML("#error_point","");
		send(result2);
		

	}
}

function verif(nombre) 
{
	var bool = true;
	
	if(nombre==0)
	{
		printHTML("#error_point","Aucun point d'emprunt");
		$("#error_point").css({
			"color":"red",
			"font-size": "80%"
		});

		//return false;
		bool = false;
	}


	return bool;
}

function send(result2) 
{
	 
	 var json2 = JSON.stringify(result2);
	 console.log(json2);


	$.ajax({
	type : "POST",
	url : "AddPoint",
	data : "points=" + json2, 
	dataType : "json",
	success :  function(data)
	{
	    if (data.message=="1")
	   {
	    $("#myModal").modal({                    
	      "backdrop"  : "static",
	      "keyboard"  : true,
	      "show"      : true                     
	    });
	   }
	    //Ajouter redirection vers la liste des points
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
