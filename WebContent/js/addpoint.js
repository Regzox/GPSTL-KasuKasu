	T = new Array();
	i = 0;
	bool=0;
	if(document.cookie.search("lang=en")!=-1)
			bool=1;
		else
			if(document.cookie.search("lang=fr")!=-1)
				bool=0;
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
		
		/*var lonlat1 = map.getLonLatFromViewPortPx(e.xy).
        transform(map.getProjectionObject(), toProjection);*/
		lonlat1=lonlat;

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
		var br0 = document.createElement("br");
		br0.id=i;
		div.appendChild(br0);
		
		i=i+1;		
		var nom = document.createElement("input");
		nom.type = "text";
		if(bool==0)
			nom.setAttribute("placeholder", "Nom du lieu"); 
		if(bool==1)
			nom.setAttribute("placeholder", "Place's name"); 

		nom.id=i;
		div.appendChild(nom);
		
		i=i+1;		
		var nom_error = document.createElement("div");
		nom_error.id=i;
		div.appendChild(nom_error);
		
		i=i+1;
		var br = document.createElement("br");
		br.id=i;
		div.appendChild(br);
		
		i= i+1;		
		var radius = document.createElement("input");
		radius.type = "text";
		radius.setAttribute("size", 40);
		if(bool==0)
			radius.setAttribute("placeholder", "Portée en mètres. Exemple : 250");
		if(bool==1)
			radius.setAttribute("placeholder", "Range in meters. Example : 250");
		radius.id=i;
		div.appendChild(radius);
		
		i=i+1;		
		var radius_error = document.createElement("div");
		radius_error.id=i;
		div.appendChild(radius_error);
		
		i=i+1;
		var br3 = document.createElement("br");
		br3.id=i;
		div.appendChild(br3);

		var supp = document.createElement("input");
		supp.type = "button";
		if(bool==0)
			supp.value = "Supprimer";
		if(bool==1)
			supp.value = "Delete";
		supp.onclick = function() 
		{

			markers.removeMarker(marker);
			div.removeChild(lon);
			div.removeChild(lat);
			div.removeChild(br0);
			div.removeChild(nom);
			div.removeChild(nom_error);
			div.removeChild(br);
			div.removeChild(radius);
			div.removeChild(radius_error);
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
	printHTML("#error_point","");
	//document.getElementById('nombre').innerHTML="0";

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
	var inc=0;

	//console.log("nombre="+nombre);
	

for (i=0; i<nombre; i=i+11)
{
	var bool = true;

	if (document.getElementById(i+4)!=null)	
	{
	  document.getElementById(i+4).innerHTML="";
	}	
	
	if (document.getElementById(i+7)!=null)	
	{
	  document.getElementById(i+7).innerHTML="";
	}
	

if (document.getElementById(i+3)!=null&&document.getElementById(i+3).value.length==0)
	{
		if (document.getElementById(i+4)!=null)	
		{
			if(bool==0)
				document.getElementById(i+4).innerHTML="<span style='color:red;font-size:80%'>Nom obligatoire</span>";
			if(bool==1)
				document.getElementById(i+4).innerHTML="<span style='color:red;font-size:80%'>Place's name required</span>";
		  
			document.getElementById(i+4).scrollIntoView();
		}
		
		bool = false;
	}

if (document.getElementById(i+6)!=null&&document.getElementById(i+6).value.length==0)
{
	if (document.getElementById(i+7)!=null)	
	{
		if(bool==0)
			document.getElementById(i+7).innerHTML="<span style='color:red;font-size:80%'>Portée obligatoire</span>";
		if(bool==1)
			document.getElementById(i+7).innerHTML="<span style='color:red;font-size:80%'>Range required</span>";

		document.getElementById(i+7).scrollIntoView();
	}

	bool = false;		

	

}

if (document.getElementById(i+6)!=null&&document.getElementById(i+6).value.length!=0)
{
	var reg= /^[0-9]*$/;	
    var string = document.getElementById(i+6).value;
	if (!reg.test(string))	
	{ 
		if (document.getElementById(i+7)!=null)	
		{
			if(bool==0)
				document.getElementById(i+7).innerHTML="<span style='color:red;font-size:80%'>Portée doit être un nombre positif</span>";
			if(bool==1)
				document.getElementById(i+7).innerHTML="<span style='color:red;font-size:80%'>The range must be a positive number</span>";

			document.getElementById(i+7).scrollIntoView();
		}
	  bool = false;

	}



}


if (bool==true)
	{
		var point = 
		{
			    "lon": document.getElementById(i).value,
			    "lat": document.getElementById(i+1).value,
			    "nom": document.getElementById(i+3).value,
			    "radius": document.getElementById(i+6).value
		}
	
		 result2.points.push(point);
		 //console.log("result2:"+result2);
	
	}
else inc=inc+1;


		      

}           

	
    if (inc==0)
    {
  	
    	if (result2.points.length!=0) 
    	{

    		send(result2); 		

    	}
    }

}

//function verif(nombre) 
//{
//	var bool = true;
//	printHTML("#error_point","");
//
//	
//	if(nombre==0)
//	{
//		console.log("lenght="+result2.points.length);
//		printHTML("#error_point","Aucun point d'emprunt");
//		$("#error_point").css({
//			"color":"red",
//			"font-size": "80%"
//		});
//
//		//return false;
//		bool = false;
//	}
//
//
//	return bool;
//}

function send(result2) 
{
	 
	 var json2 = JSON.stringify(result2);
	 //console.log(json2);	
	//console.log("Ok");

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
	       
	       $("#myModal").on('hidden.bs.modal', function () {
	           window.location.href = listpoint_jsp;
	       });	 
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
