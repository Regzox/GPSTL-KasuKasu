var map;
bool=0;
if(document.cookie.search("lang=en")!=-1)
		bool=1;
	else
		if(document.cookie.search("lang=fr")!=-1)
			bool=0;
var id; 
var name; 
var radius;

//OpenLayers.Control.Click = OpenLayers.Class(OpenLayers.Control, {
//	defaultHandlerOptions : {
//		'single' : true,
//		'double' : false,
//		'pixelTolerance' : 0,
//		'stopSingle' : false,
//		'stopDouble' : false
//	},
//
//	initialize : function(options) {
//		this.handlerOptions = OpenLayers.Util.extend({},
//				this.defaultHandlerOptions);
//		OpenLayers.Control.prototype.initialize.apply(this, arguments);
//		this.handler = new OpenLayers.Handler.Click(this, {
//			'click' : this.trigger
//		}, this.handlerOptions);
//	},
//
//	trigger : function(e) {
//		var toProjection = new OpenLayers.Projection("EPSG:4326");
//		var lonlat = map.getLonLatFromViewPortPx(e.xy);
//		
//		var markers = new OpenLayers.Layer.Markers("Markers");
//		map.addLayer(markers);
//
//		var marker = new OpenLayers.Marker(lonlat);
//		markers.addMarker(marker);
//		
//	}
//});

function init() 
{
	map = new OpenLayers.Map("mapdiv");
	map.addLayer(new OpenLayers.Layer.OSM());

	var lonLat = new OpenLayers.LonLat(2.3488000, 48.8534100).transform(
			new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
			map.getProjectionObject() // to Spherical Mercator Projection
	);

	var zoom = 14;
	map.setCenter(lonLat, zoom);
	//Afficher les points d'échange des amis de l'utilisateur auxquels il peut s'abonner
	userFriendsPoints();
	//Afficher les points d'échange de l'utilisateur
	userPoints();

//	var click = new OpenLayers.Control.Click();
//	map.addControl(click);
//	click.activate();

}

/************************************* S'abonner à un point d'échange ************************************/
function abonnement()
{
    $("#myModal").modal({                    
	      "backdrop"  : "static",
	      "keyboard"  : true,
	      "show"      : true                     
	    });
    
	$('#save').on(
			'click',
			function(evt)
			{
			    nom = $("#myModal").find("#nom_input").val();
			    radius = $("#myModal").find("#radius_input").val();
			    lat = $("#lat").val();
			    lon = $("#lon").val();

			    
			    if (nom.length!=0 && radius.length!=0)
			    	{
				       addPoint(nom,radius,lat,lon); 
			    	}

			}
			);

 }

function addPoint(nom,radius,lat,lon)
{

	$.ajax({
		type : "POST",
		url : "addexchangepoint",
		xhrFields: 
		{
			withCredentials: true
		},
		data : "name=" + nom + "&radius=" + radius + "&lat=" + lat + "&lon=" + lon, 
		dataType : "JSON",
		success : function (data)
		{

	        if (data.message=="1")
	        	{
	    	       $("#myModal").hide(); 
	    	       
	    	       $("#myModal2").modal({                    
	     		      "backdrop"  : "static",
	     		      "keyboard"  : true,
	     		      "show"      : true                     
	     		    });
	     	       
	     	       $("#myModal2").on('hidden.bs.modal', function () {
	     	           window.location.href = "/KasuKasu/importpoint.jsp";
	     	       });
   	    
	        	}
		},		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
	
}

/********************************** Afficher les points des amis de l'utilisateur ************************************/


function userFriendsPoints(){
	$.ajax({
		type : "GET",
		url : "PointsUserFriendList",
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
    var vectorLayer = new OpenLayers.Layer.Vector("Overlay");


	for (var i=0; i< json.expts.length; i++)
		{

	      var lon = json.expts[i].lon;
	       var lat = json.expts[i].lat;
	      var feature;
	       if(bool==0)
	        feature = new OpenLayers.Feature.Vector
	        (

	                new OpenLayers.Geometry.Point( lon, lat ),
	                {description: "Mes amis sur ce lieu: "+json.expts[i].name} ,
	                {externalGraphic: '/KasuKasu/data/marker.png', graphicHeight: 25, graphicWidth: 21, graphicXOffset:-12, graphicYOffset:-25  }
	         ); 
	       if(bool==1)
		        feature = new OpenLayers.Feature.Vector
		        (

		                new OpenLayers.Geometry.Point( lon, lat ),
		                {description: "My friends in this place : "+json.expts[i].name} ,
		                {externalGraphic: '/KasuKasu/data/marker.png', graphicHeight: 25, graphicWidth: 21, graphicXOffset:-12, graphicYOffset:-25  }
		         ); 
	        vectorLayer.addFeatures(feature);

		}
	
    map.addLayer(vectorLayer);
    
    var controls = {
    	      selector: new OpenLayers.Control.SelectFeature(vectorLayer, { onSelect: createPopup, onUnselect: destroyPopup })
    	    };

    	    function createPopup(feature) {

    	    	var lonLat = feature.geometry.getBounds().getCenterLonLat();
    			document.getElementById('lat').value=lonLat.lat;
    			document.getElementById('lon').value=lonLat.lon;


    	         feature.popup = new OpenLayers.Popup.FramedCloud("pop",
    	          feature.geometry.getBounds().getCenterLonLat(),
    	          null, 	          
    	          '<div class="markerContent">'+feature.attributes.description+'</div>'+'<br></br>'+'<button onclick="abonnement()">S\'abonner</button>',
    	          null,
    	          true,
    	          function() { controls['selector'].unselectAll(); }
    	      );
    	      //feature.popup.closeOnMove = true;
    	      map.addPopup(feature.popup);
    	    }

    	    function destroyPopup(feature) {
    	      feature.popup.destroy();
    	      feature.popup = null;
    	    }
    	    
    	    map.addControl(controls['selector']);
    	    controls['selector'].activate();

}

/********************************** Afficher les points de l'utilisateur ************************************/
function userPoints(){
	$.ajax({
		type : "GET",
		url : "/KasuKasu/PointsUserList",
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
    var vectorLayer = new OpenLayers.Layer.Vector("Overlay");


	for (var i=0; i< json.expts.length; i++)
		{

	      var lon = json.expts[i].lon;
	      var lat = json.expts[i].lat;
	      id = json.expts[i].id;
	      var feature;
	       if(bool==0)
	        feature = new OpenLayers.Feature.Vector
	        (

	                new OpenLayers.Geometry.Point( lon, lat ),
	                {description: "Lieu : "+json.expts[i].name+", "+json.expts[i].radius+"m"},
	                {externalGraphic: '/KasuKasu/data/marker-red.png', graphicHeight: 25, graphicWidth: 21, graphicXOffset:-12, graphicYOffset:-25  }
	         ); 
	       if(bool==1)
		        feature = new OpenLayers.Feature.Vector
		        (

		                new OpenLayers.Geometry.Point( lon, lat ),
		                {description: "Place : "+json.expts[i].name} ,
		                {externalGraphic: '/KasuKasu/data/marker-red.png', graphicHeight: 25, graphicWidth: 21, graphicXOffset:-12, graphicYOffset:-25  }
		         ); 
	        vectorLayer.addFeatures(feature);

		}
	
    map.addLayer(vectorLayer);
    
    var controls = {
    	      selector: new OpenLayers.Control.SelectFeature(vectorLayer, { onSelect: createPopup, onUnselect: destroyPopup })
    	    };

    	    function createPopup(feature) 
    	    {

    	    	var lonLat = feature.geometry.getBounds().getCenterLonLat();
    			document.getElementById('lat').value=lonLat.lat;
    			document.getElementById('lon').value=lonLat.lon;
    			
    			id="";
    			name="";
    			radius="";
    			
    			getMarkerDetail(lonLat.lat,lonLat.lon);
//    			console.log(id);
//    			console.log(name);
//    			console.log(radius);

    	         feature.popup = new OpenLayers.Popup.FramedCloud("pop",
    	          feature.geometry.getBounds().getCenterLonLat(),
    	          null, 	          
    	          '<div class="markerContent">'+feature.attributes.description+'</div>'+'<br></br>'+'<button onclick="Modifier(\'' + id + '\',\'' + name + '\' ,\'' + radius+ '\')"> Modifier</button>'+'<button onclick="Delete(\''+id+'\');">Supprimer</button>',
    	          null,
    	          true,
    	          function() { controls['selector'].unselectAll(); }
    	      );
    	      //feature.popup.closeOnMove = true;
    	      map.addPopup(feature.popup);
    	    }

    	    function destroyPopup(feature) {
    	      feature.popup.destroy();
    	      feature.popup = null;
    	    }
    	    
    	    map.addControl(controls['selector']);
    	    controls['selector'].activate();

}

/*************************************** Supprimer un point d'échange ************************************/

function Delete(id)
{
	$.ajax({
		type : "POST",
		url : "/KasuKasu/DeleteExchangePointServlet",
		data : "id="+id,
		dataType : "JSON",
		success : function (data)
		{

	        if (data.message=="1")
	        	{
	    	       $("#myModal3").modal({                    
	    		      "backdrop"  : "static",
	    		      "keyboard"  : true,
	    		      "show"      : true                     
	    		    });
	    	       
	    	       $("#myModal3").on('hidden.bs.modal', function () {
	    	           window.location.href = "/KasuKasu/importpoint.jsp";
	    	       });
	        	}
		},		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});}


/*************************************** Modifier un point d'échange ************************************/


function Modifier(id_modif,old_name,old_radius)
{
	
//	console.log(id);
//	console.log(old_name);
//	console.log(old_radius);

    $("#myModal4").modal({                    
	      "backdrop"  : "static",
	      "keyboard"  : true,
	      "show"      : true                     
	    });
  
	$('#enreg').on(
			'click',
			function(evt)
			{
			    nom_modif = $("#myModal4").find("#nom_input").val();
			    radius_modif = $("#myModal4").find("#radius_input").val();

			    
			    if (nom_modif.length==0) nom_modif = old_name;
			    if (radius_modif.length==0) radius_modif = old_radius;
			    console.log(nom_modif);
			    console.log(radius_modif);
			    
				updatePoint(id_modif,radius_modif,nom_modif); 

			}
			);
}

function updatePoint(id_modif,radius_modif,name_modif)
{
	$.ajax({
		type : "POST",
		url : "/KasuKasu/updateexchangepoint",
		data : "id=" + id_modif + "&radius=" + radius_modif + "&name=" + name_modif, 
		dataType : "JSON",
		success : function (data)
		{

	        if (data.message=="1")
	        	{
	    	       $("#myModal4").hide(); 

	    	       $("#myModal3").modal({                    
	    		      "backdrop"  : "static",
	    		      "keyboard"  : true,
	    		      "show"      : true                     
	    		    });
	    	       
	    	       $("#myModal3").on('hidden.bs.modal', function () {
	    	           window.location.href = "/KasuKasu/importpoint.jsp";
	    	       });
	        	}
		},		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});}


/********************************** Récupérer les informations sur un marker ************************************/
function getMarkerDetail(lat,lon)
{
	$.ajax({
		type : "GET",
		url : "/KasuKasu/GetPointMarkerDetail",
	    async: false,
		data : "lat=" + lat + "&lon=" + lon,
		dataType : "JSON",
		success : function (data)
		{
	       id=data.id;
           name =data.name;
           radius=data.radius;
		},
		error : function(xhr,status,errorthrown){
			console.log(JSON.stringify(xhr + " " + status + " " + errorthrown));
		}
	});
}
