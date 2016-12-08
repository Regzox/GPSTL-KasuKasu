var map;
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
	
	userPoints();

}


function abonnement()
{
	alert ("coucou");
}

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
    var vectorLayer = new OpenLayers.Layer.Vector("Overlay");


	for (var i=0; i< json.expts.length; i++)
		{

	      var lon = json.expts[i].lon;
	       var lat = json.expts[i].lat;
	       
	        var feature = new OpenLayers.Feature.Vector
	        (
	                new OpenLayers.Geometry.Point( lon, lat ).transform
	                (
	            			new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
	            			map.getProjectionObject() // to Spherical Mercator Projection  
	                ),
	                {description: "marker number " + i} ,
	                {externalGraphic: '/KasuKasu/data/marker.png', graphicHeight: 25, graphicWidth: 21, graphicXOffset:-12, graphicYOffset:-25  }
	         );             
	        vectorLayer.addFeatures(feature);

		}
	
    map.addLayer(vectorLayer);
    
    var controls = {
    	      selector: new OpenLayers.Control.SelectFeature(vectorLayer, { onSelect: createPopup, onUnselect: destroyPopup })
    	    };

    	    function createPopup(feature) {
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