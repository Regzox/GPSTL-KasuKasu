var map;
function init() 
{
	map = new OpenLayers.Map("mapdiv");
	map.addLayer(new OpenLayers.Layer.OSM());

	var lonLat = new OpenLayers.LonLat(-0.1279688 ,51.5077286).transform(
			new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
			map.getProjectionObject() // to Spherical Mercator Projection
	);

	var zoom = 14;

	map.setCenter(lonLat, zoom);	
	
	addMarkers();

}



function addMarkers() 
{
    var vectorLayer = new OpenLayers.Layer.Vector("Overlay");
    var markers = 
    [
       [ -0.1279688, 51.5077286 ],
       [ -0.1244324, 51.5006728 ],
       [ -0.119623, 51.503308 ]
    ];
    
    //Loop through the markers array
    for (var i=0; i<markers.length; i++) 
    {
      
       var lon = markers[i][0];
       var lat = markers[i][1];
       
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

function abonnement()
{
	alert ("coucou");
}